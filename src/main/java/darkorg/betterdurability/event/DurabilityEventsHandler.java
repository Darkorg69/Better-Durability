package darkorg.betterdurability.event;


import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.setup.ConfigurationHandler;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Core logic is here.
 */
@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID)
public class DurabilityEventsHandler {
    public static boolean isBlacklisted(Item targetItem, VanillaDamageableType itemType) {
        return ConfigurationHandler.DISABLED_CATEGORIES.contains(itemType.category)
            || ConfigurationHandler.DISABLED_TYPES.contains(itemType)
            || ConfigurationHandler.BLACKLISTED_ITEMS.contains(targetItem);
    }

    @SubscribeEvent
    public static void onItemBreaking(ItemDurabilityEvent.ItemBreaking event) {
        Item targetItem = event.targetStack.getItem();
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && !isBlacklisted(targetItem, itemType)) {
            event.reserveDurability = itemType.protectValue;
        }
    }

    @SubscribeEvent
    public static void onItemUsage(ItemDurabilityEvent.ItemUsage event) {
        Item targetItem = event.targetStack.getItem();
        VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(targetItem);
        if (itemType != null && itemType.isItemBroken(event.targetStack) && !isBlacklisted(targetItem, itemType)) {
            event.setCanceled(true);
        }
    }
}
