package darkorg.betterdurability.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import static darkorg.betterdurability.util.ItemUtil.*;

public class StackUtil {
    public static boolean isBroken(ItemStack stack) {
        return isInvalidLeftClickBlock(stack) || isInvalidLeftClickEntity(stack) || isInvalidRightClickBlock(stack) || isInvalidRightClickEntity(stack) || isInvalidRightClickItem(stack);
    }

    public static boolean isInvalidLeftClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isSword(item) || isTrident(item)) {
                return durabilityLeft <= 2;
            }
            if (isTool(item) || isHoe(item) || isShears(item)) {
                return durabilityLeft <= 1;
            }
        }
        return false;
    }

    public static boolean isInvalidLeftClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isTool(item)) {
                return durabilityLeft <= 2;
            }
            if (isHoe(item) || isSword(item) || isTrident(item)) {
                return durabilityLeft <= 1;
            }
        }
        return false;
    }

    public static boolean isInvalidRightClickItem(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isCrossbow(item)) {
                return durabilityLeft <= 9;
            }
            if (isFishingRod(item)) {
                return durabilityLeft <= 5;
            }
            if (isBow(item) || isTrident(item)) {
                return durabilityLeft <= 1;
            }
        }
        return false;
    }

    public static boolean isInvalidRightClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (isTool(item) || isHoe(item) || isFlintAndSteel(item)) {
                return durabilityLeft <= 1;
            }
        }
        return false;
    }

    public static boolean isInvalidRightClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            if (isShears(stack.getItem())) {
                return durabilityLeft <= 1;
            }
        }
        return false;
    }
}