package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.StackUtil;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class ForgeEventsHandler {
    @SubscribeEvent
    public static void onLeftClickBlock(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock().getSpeedFactor() != 0.0F) {
            if (StackUtil.isInvalidLeftClickBlock(event.getPlayer().getMainHandItem())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickEntity(AttackEntityEvent event) {
        if (StackUtil.isInvalidLeftClickEntity(event.getPlayer().getMainHandItem())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        if (StackUtil.isInvalidRightClickItem(event.getItemStack())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (StackUtil.isInvalidRightClickBlock(event.getItemStack())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (StackUtil.isInvalidRightClickEntity(event.getItemStack())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onRightClickEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        if (StackUtil.isInvalidRightClickEntity(event.getItemStack())) {
            event.setCanceled(true);
        }
    }
}
