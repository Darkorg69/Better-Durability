package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.event.ItemDurabilityEvent.ItemBreaking;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Random;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract int getDamageValue();
    @Shadow public abstract void setDamageValue(int pDamage);
    @Shadow public abstract int getMaxDamage();

    @Inject(method = "hurt(ILjava/util/Random;Lnet/minecraft/entity/player/ServerPlayerEntity;)Z",
            cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getDamageValue()I", ordinal = 1))
    private void injectItemStackHurt(int pAmount, Random pRandom, ServerPlayerEntity pUser, CallbackInfoReturnable<Boolean> cir) {
        int newDamageValue = this.getDamageValue() + pAmount;
        int maxDamageValue = this.getMaxDamage();
        if (newDamageValue >= maxDamageValue) {
            ItemBreaking event = new ItemBreaking((ItemStack)(Object)this, pAmount);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.reserveDurability > 0) {
                this.setDamageValue(maxDamageValue - event.reserveDurability);
                cir.setReturnValue(false);
            } else {
                this.setDamageValue(newDamageValue);
                cir.setReturnValue(true);
            }
        } else {
            this.setDamageValue(newDamageValue);
            cir.setReturnValue(false);
        }
        cir.cancel();
    }
}
