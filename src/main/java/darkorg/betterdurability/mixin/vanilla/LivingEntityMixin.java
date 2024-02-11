package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.util.ItemUtil;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


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
    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/ai/attributes/Attribute;)D", cancellable = true,
            at = @At(value = "TAIL"))
    private void modifyArmorCheck_discardToughness(Attribute pAttribute, CallbackInfoReturnable<Double> cir) {
        if (pAttribute == Attributes.ARMOR_TOUGHNESS) {
            double result = cir.getReturnValue();
            float invalidToughness = 0;
            for (EquipmentSlotType eqSlot: StackUtil.ARMOR_SLOTS) {
                ItemStack armorStack = this.getItemBySlot(eqSlot);
                if (armorStack.getItem() instanceof ArmorItem armorItem) {
                    if (!StackUtil.canArmorProtect(armorStack)) {
                        invalidToughness += armorItem.getToughness();
                    }
                }
            }
            result -= invalidToughness;
            cir.setReturnValue(result);
        }
    }
    @Inject(method = "getArmorValue()I", cancellable = true,
            at = @At(value = "TAIL"))
    private void modifyArmorCheck_discardDefense(CallbackInfoReturnable<Integer> cir) {
        int result = cir.getReturnValue();
        int invalidDefense = 0;
        for (EquipmentSlotType eqSlot: StackUtil.ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(eqSlot);
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                if (!StackUtil.canArmorProtect(armorStack)) {
                    invalidDefense += armorItem.getDefense();
                }
            }
        }
        result -= invalidDefense;
        cir.setReturnValue(result);
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

