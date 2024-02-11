package darkorg.betterdurability.mixin.vanilla;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> p_i48577_1_, World p_i48577_2_) {
        super(p_i48577_1_, p_i48577_2_);
    }

    // inject the Shield Checking
    @ModifyArg(method = "hurtCurrentlyUsedShield(F)V", index = 0,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hurtAndBreak(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V"))
    private int modifyHelmetCheck_preventBreak(int pAmount) {
        return StackUtil.calculateShieldReducedDamage(this.useItem, pAmount);
    }
}
