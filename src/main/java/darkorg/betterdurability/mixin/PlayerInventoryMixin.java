package darkorg.betterdurability.mixin;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {
    // Injected instance things
    @Shadow @Final public NonNullList<ItemStack> armor;

    // inject the Armor Checking
    // This looks ugly and fragile, but it seems that there's no other way.
    @Unique private int modifyArmorCheck_preventBreak$armorIterIdx;
    @Inject(method = "hurtArmor(Lnet/minecraft/util/DamageSource;F)V", locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private void modifyArmorCheck_preventBreak_1(DamageSource pDamageSource, float pDamageAmount, CallbackInfo ci, int i, ItemStack itemstack, int j) {
        modifyArmorCheck_preventBreak$armorIterIdx = i;
    }
    @ModifyArg(method = "hurtArmor(Lnet/minecraft/util/DamageSource;F)V", index = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private int modifyArmorCheck_preventBreak_2(int pAmount) {
        ItemStack stack = this.armor.get(modifyArmorCheck_preventBreak$armorIterIdx);
        return StackUtil.calculateArmorReducedDamage(stack, pAmount);
    }
}
