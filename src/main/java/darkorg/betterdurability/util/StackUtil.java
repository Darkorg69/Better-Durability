package darkorg.betterdurability.util;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static darkorg.betterdurability.util.ItemUtil.*;

public class StackUtil {
    public static boolean canLeftClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isSword(item) || isTrident(item)) return durabilityLeft > 2;
            if (isTool(item) || isHoe(item) || isShears(item)) return durabilityLeft > 1;
        }
        return true;
    }

    public static boolean canLeftClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isTool(item)) return durabilityLeft > 2;
            if (isHoe(item) || isSword(item) || isTrident(item)) return durabilityLeft > 1;
        }
        return true;
    }

    public static boolean canRightClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isTool(item) || isHoe(item) || isFlintAndSteel(item)) return durabilityLeft > 1;
        }
        return true;
    }

    public static boolean canRightClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isShears(item)) return durabilityLeft > 1;
        }
        return true;
    }

    public static boolean canRightClickItem(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isCrossbow(item)) return durabilityLeft > 9;
            if (isFishingRod(item)) return durabilityLeft > 5;
            if (isBow(item) || isTrident(item)) return durabilityLeft > 1;
        }
        return true;
    }

    public static boolean isBroken(ItemStack stack) {
        return !canLeftClickBlock(stack) || !canLeftClickEntity(stack) || !canRightClickBlock(stack) || !canRightClickEntity(stack) || !canRightClickItem(stack);
    }
}
