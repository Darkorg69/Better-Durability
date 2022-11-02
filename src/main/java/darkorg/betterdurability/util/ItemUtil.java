package darkorg.betterdurability.util;

import net.minecraft.item.*;

public class ItemUtil {
    public static boolean isTool(Item item) {
        return item instanceof AxeItem || item instanceof PickaxeItem || item instanceof ShovelItem;
    }

    public static boolean isHoe(Item item) {
        return item instanceof HoeItem;
    }

    public static boolean isShears(Item item) {
        return item instanceof ShearsItem;
    }

    public static boolean isSword(Item item) {
        return item instanceof SwordItem;
    }

    public static boolean isFishingRod(Item item) {
        return item instanceof FishingRodItem;
    }

    public static boolean isFlintAndSteel(Item item) {
        return item instanceof FlintAndSteelItem;
    }

    public static boolean isBow(Item item) {
        return item instanceof BowItem;
    }

    public static boolean isTrident(Item item) {
        return item instanceof TridentItem;
    }

    public static boolean isCrossbow(Item item) {
        return item instanceof CrossbowItem;
    }
}
