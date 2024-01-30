package darkorg.betterdurability.mixin;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Random;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {
    // inject the Thorns checking
    @Unique ItemStack modifyThornsCheck$thornHurtStack = null;
    @Redirect(method = "doPostHurt(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)Ljava/util/Map$Entry;"))
    private Map.Entry<EquipmentSlotType, ItemStack> modifyThornsCheck_doNotPickBroken(Enchantment pTargetEnchantment, LivingEntity pEntity) {
        return EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, pEntity, StackUtil::canArmorProtect);
    }
    @Inject(method = "doPostHurt(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;I)V",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;hurt(Lnet/minecraft/util/DamageSource;F)Z"))
    private void modifyThornsCheck_discardEffect(LivingEntity pUser, Entity pAttacker, int pLevel, CallbackInfo ci, Random random, Map.Entry<EquipmentSlotType, ItemStack> entry) {
        if (entry == null) {
            ci.cancel();
        } else {
            modifyThornsCheck$thornHurtStack = entry.getValue();
        }
    }
    @ModifyArg(method = "doPostHurt(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;I)V", index = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private int modifyThornsCheck_preventBreak(int pAmount) {
        return StackUtil.calculateArmorReducedDamage(modifyThornsCheck$thornHurtStack, pAmount);
    }
}
