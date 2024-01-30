package darkorg.betterdurability.mixin;

import darkorg.betterdurability.util.ItemUtil;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    // Injected instance things
    @Shadow protected ItemStack useItem;
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlotType pSlot);

    // inject the Helmet Checking
    @ModifyArg(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", index = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private int modifyHelmetCheck_preventBreak(int pAmount) {
        ItemStack helmetStack = this.getItemBySlot(EquipmentSlotType.HEAD);
        return StackUtil.calculateArmorReducedDamage(helmetStack, pAmount);
    }
    @ModifyVariable(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", argsOnly = true,
            at = @At(value = "CONSTANT", args = "floatValue=0.75"))
    private float modifyHelmetCheck_discardDefense(float value) {
        // increase damage if helmet should be broken
        ItemStack helmetStack = this.getItemBySlot(EquipmentSlotType.HEAD);
        return StackUtil.canArmorProtect(helmetStack) ? value : value * 1.33F;
    }

    // inject the Armor Checking
    // Fun fact: helmet can be hurt by ANVIL and FALLING_BLOCK damage, which does not bypass armor,
    //           so helmet will be double-damaged in such occasions
    @ModifyArgs(method = "getDamageAfterArmorAbsorb(Lnet/minecraft/util/DamageSource;F)F",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/CombatRules;getDamageAfterAbsorb(FFF)F"))
    private void modifyArmorCheck_discardDefense(Args args) {
        float pTotalArmor = args.get(1);
        float pToughnessAttribute = args.get(2);

        int invalidDefense = 0;
        float invalidToughness = 0;
        for (EquipmentSlotType eqSlot: StackUtil.ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(eqSlot);
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                if (!StackUtil.canArmorProtect(armorStack)) {
                    invalidDefense += armorItem.getDefense();
                    invalidToughness += armorItem.getToughness();
                }
            }
        }
        pTotalArmor -= (float)invalidDefense;
        pToughnessAttribute -= invalidToughness;

        args.set(1, pTotalArmor);
        args.set(2, pToughnessAttribute);
    }

    // inject the Shield Checking
    @Inject(method = "isBlocking()Z", cancellable = true,
            at = @At(value = "HEAD"))
    private void modifyShieldCheck_discardDefense(CallbackInfoReturnable<Boolean> cir) {
        if (ItemUtil.isShield(this.useItem.getItem())) {
            if (!StackUtil.canShieldProtect(this.useItem)) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    // inject the Soul Speed Checking
    @ModifyArg(method = "tryAddSoulSpeed()V", index = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private int modifySoulSpeedCheck_preventBreak(int pAmount) {
        ItemStack bootStack = this.getItemBySlot(EquipmentSlotType.FEET);
        return StackUtil.calculateArmorReducedDamage(bootStack, pAmount);
    }
    @Inject(method = "tryAddSoulSpeed()V", cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/ModifiableAttributeInstance;addTransientModifier(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V"))
    private void modifySoulSpeedCheck_discardEffect(CallbackInfo ci) {
        ItemStack bootStack = this.getItemBySlot(EquipmentSlotType.FEET);
        if (!StackUtil.canArmorProtect(bootStack)) {
            ci.cancel();
        }
    }
}

