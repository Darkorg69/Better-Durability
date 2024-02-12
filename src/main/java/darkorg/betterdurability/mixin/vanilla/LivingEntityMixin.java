package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique private static final EquipmentSlotType[] ARMOR_SLOTS = {
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET
    };

    // Injected instance things
    @Shadow protected ItemStack useItem;
    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlotType pSlot);

    // inject the Helmet Checking
    @ModifyVariable(method = "hurt(Lnet/minecraft/util/DamageSource;F)Z", argsOnly = true,
            at = @At(value = "CONSTANT", args = "floatValue=0.75"))
    private float modifyHelmetCheck$discardDefense(float value) {
        // increase damage if helmet should be broken
        ItemStack helmetStack = this.getItemBySlot(EquipmentSlotType.HEAD);
        return ItemUsage.check(helmetStack, ItemUsage.Type.HELMET_HEAD_STRUCK) ? value : value * 1.33F;
    }

    // inject the Armor Checking
    // Fun fact: helmet can be hurt by ANVIL and FALLING_BLOCK damage, which does not bypass armor,
    //           so helmet will be double-damaged in such occasions
    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/ai/attributes/Attribute;)D", cancellable = true,
            at = @At(value = "TAIL"))
    private void modifyArmorCheck$discardToughness(Attribute pAttribute, CallbackInfoReturnable<Double> cir) {
        if (pAttribute == Attributes.ARMOR_TOUGHNESS) {
            double result = cir.getReturnValue();
            float invalidToughness = 0;
            for (EquipmentSlotType eqSlot: ARMOR_SLOTS) {
                ItemStack armorStack = this.getItemBySlot(eqSlot);
                if (armorStack.getItem() instanceof ArmorItem armorItem) {
                    if (!ItemUsage.check(armorStack, ItemUsage.Type.ARMOR_ENEMY_ATTACK)) {
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
    private void modifyArmorCheck$discardDefense(CallbackInfoReturnable<Integer> cir) {
        int result = cir.getReturnValue();
        int invalidDefense = 0;
        for (EquipmentSlotType eqSlot: ARMOR_SLOTS) {
            ItemStack armorStack = this.getItemBySlot(eqSlot);
            if (armorStack.getItem() instanceof ArmorItem armorItem) {
                if (!ItemUsage.check(armorStack, ItemUsage.Type.ARMOR_ENEMY_ATTACK)) {
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
    private void modifyShieldCheck$discardDefense(CallbackInfoReturnable<Boolean> cir) {
        if (VanillaDamageableType.SHIELD.isItemThisType(this.useItem.getItem())) {
            if (!ItemUsage.check(this.useItem, ItemUsage.Type.SHIELD_DEFEND)) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

    // inject the Soul Speed Checking
    @Inject(method = "tryAddSoulSpeed()V", cancellable = true,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/ModifiableAttributeInstance;addTransientModifier(Lnet/minecraft/entity/ai/attributes/AttributeModifier;)V"))
    private void modifySoulSpeedCheck$discardEffect(CallbackInfo ci) {
        ItemStack bootStack = this.getItemBySlot(EquipmentSlotType.FEET);
        if (!ItemUsage.check(bootStack, ItemUsage.Type.BOOTS_SOULSPEED)) {
            ci.cancel();
        }
    }
}

