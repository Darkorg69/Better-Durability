package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import net.minecraft.item.ItemStack;
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
            ItemStack targetStack = event.getPlayer().getMainHandItem();
            if (!targetStack.isDamageableItem()) return;
            boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                    ItemDurabilityEvent.ItemUsage.Type.TOOL_LEFT_CLICK_BLOCK);
            if (!canFunction) { event.setCanceled(true); }
        }
    }

    @SubscribeEvent
    public static void onLeftClickEntity(AttackEntityEvent event) {
        ItemStack targetStack = event.getPlayer().getMainHandItem();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_LEFT_CLICK_ENTITY);
        if (!canFunction) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ITEM);
        if (!canFunction) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_BLOCK);
        if (!canFunction) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ENTITY);
        if (!canFunction) { event.setCanceled(true); }
    }

    @SubscribeEvent
    public static void onRightClickEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        ItemStack targetStack = event.getItemStack();
        if (!targetStack.isDamageableItem()) return;
        boolean canFunction = ItemDurabilityEvent.ItemUsage.check(targetStack,
                ItemDurabilityEvent.ItemUsage.Type.TOOL_RIGHT_CLICK_ENTITY);
        if (!canFunction) { event.setCanceled(true); }
    }
}
