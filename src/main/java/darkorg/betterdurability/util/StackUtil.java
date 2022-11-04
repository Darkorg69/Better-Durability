package darkorg.betterdurability.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackUtil {
    public static boolean isInvalidLeftClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (ItemUtil.isSword(item) || ItemUtil.isTrident(item)) return durabilityLeft <= 2;
            if (ItemUtil.isTool(item) || ItemUtil.isHoe(item) || ItemUtil.isShears(item)) return durabilityLeft <= 1;
        }
        return false;
    }

    public static boolean isInvalidLeftClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (ItemUtil.isTool(item)) return durabilityLeft <= 2;
            if (ItemUtil.isHoe(item) || ItemUtil.isSword(item) || ItemUtil.isTrident(item)) return durabilityLeft <= 1;
        }
        return false;
    }

    public static boolean isInvalidRightClickItem(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (ItemUtil.isCrossbow(item)) return durabilityLeft <= 9;
            if (ItemUtil.isFishingRod(item)) return durabilityLeft <= 5;
            if (ItemUtil.isBow(item) || ItemUtil.isTrident(item)) return durabilityLeft <= 1;
        }
        return false;
    }

    public static boolean isInvalidRightClickBlock(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (ItemUtil.isTool(item) || ItemUtil.isHoe(item) || ItemUtil.isFlintAndSteel(item))
                return durabilityLeft <= 1;
        }
        return false;
    }

    public static boolean isInvalidRightClickEntity(ItemStack stack) {
        if (stack.isDamageableItem()) {
            int durability = stack.getMaxDamage();
            int durabilityLost = stack.getDamageValue();
            int durabilityLeft = durability - durabilityLost;

            Item item = stack.getItem();

            if (ItemUtil.isShears(item)) return durabilityLeft <= 1;
        }
        return false;
    }

    public static boolean isBroken(ItemStack stack) {
        return isInvalidLeftClickBlock(stack) || isInvalidLeftClickEntity(stack) || isInvalidRightClickBlock(stack) || isInvalidRightClickEntity(stack) || isInvalidRightClickItem(stack);
    }
}
