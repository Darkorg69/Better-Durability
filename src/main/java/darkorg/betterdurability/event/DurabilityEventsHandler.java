package darkorg.betterdurability.event;


import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Core logic is here.
 */
@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class DurabilityEventsHandler {
    @SubscribeEvent
    public static void onItemBreaking(ItemDurabilityEvent.ItemBreaking event) {
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(event.targetStack.getItem());
        if (itemType != null) { event.reserveDurability = itemType.protectValue; }
    }

    @SubscribeEvent
    public static void onItemUsage(ItemDurabilityEvent.ItemUsage event) {
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(event.targetStack.getItem());
        if (itemType != null && itemType.isItemBroken(event.targetStack)) { event.setCanceled(true); }
    }
}
