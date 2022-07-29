package darkorg.betterdurability.event;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RightClick {
    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();

        if (!StackUtil.canRightClickBlock(stack)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        ItemStack stack = event.getItemStack();

        if (!StackUtil.canRightClickEntity(stack)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack stack = event.getItemStack();

        if (!StackUtil.canRightClickItem(stack)) {
            event.setCanceled(true);
        }
    }
}
