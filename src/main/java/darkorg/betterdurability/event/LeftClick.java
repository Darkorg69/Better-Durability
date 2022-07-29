package darkorg.betterdurability.event;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LeftClick {
    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getPlayer();
        BlockState state = event.getState();
        ItemStack stack = player.getMainHandItem();

        if (state.getBlock().getSpeedFactor() != 0.0F) {
            if (!StackUtil.canLeftClickBlock(stack)) {
                event.setNewSpeed(0.0F);
            }
        }
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getMainHandItem();

        if (!StackUtil.canLeftClickEntity(stack)) {
            event.setCanceled(true);
        }
    }
}
