package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.event.ItemDurabilityEvent.ItemUsage;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;
import java.util.Random;

@Mixin(ThornsEnchantment.class)
public class ThornsEnchantmentMixin {
    // inject the Thorns checking
    @Redirect(method = "doPostHurt(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRandomItemWith(Lnet/minecraft/enchantment/Enchantment;Lnet/minecraft/entity/LivingEntity;)Ljava/util/Map$Entry;"))
    private Map.Entry<EquipmentSlotType, ItemStack> modifyThornsCheck$doNotPickBroken(Enchantment pTargetEnchantment, LivingEntity pEntity) {
        // if not cancelled, we can use it for Thorns checking
        return EnchantmentHelper.getRandomItemWith(Enchantments.THORNS, pEntity, stack -> ItemUsage.check(stack, ItemUsage.Type.ARMOR_THORNS));
    }
    @Inject(method = "doPostHurt(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;I)V",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;hurt(Lnet/minecraft/util/DamageSource;F)Z"))
    private void modifyThornsCheck$discardEffect(LivingEntity pUser, Entity pAttacker, int pLevel, CallbackInfo ci, Random random, Map.Entry<EquipmentSlotType, ItemStack> entry) {
        // entry is nullable with our injection, so add a check here
        if (entry == null) { ci.cancel(); }
    }
}
