package darkorg.betterdurability.util;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackUtil {
    public static final EquipmentSlotType[] ARMOR_SLOTS = {
            EquipmentSlotType.HEAD,
            EquipmentSlotType.CHEST,
            EquipmentSlotType.LEGS,
            EquipmentSlotType.FEET
    };
    public static final int ARMOR_BROKEN_THRESHOLD = 2; // armor with durability not more than this will not work
    public static final int ARMOR_VANILLA_THRESHOLD = 1; // armor durability should never drop below this
    public static final int SHIELD_BROKEN_THRESHOLD = 4; // shield with durability not more than this will not work
    public static final int SHIELD_VANILLA_THRESHOLD = 2; // shield durability should never drop below this

    public static int calculateArmorReducedDamage(ItemStack stack, int pAmount) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        return Math.min(pAmount, durabilityLeft - ARMOR_VANILLA_THRESHOLD);
    }

    public static boolean canArmorProtect(ItemStack stack) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        return durabilityLeft > ARMOR_BROKEN_THRESHOLD;
    }

    public static int calculateShieldReducedDamage(ItemStack stack, int pAmount) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        return Math.min(pAmount, durabilityLeft - SHIELD_VANILLA_THRESHOLD);
    }

    public static boolean canShieldProtect(ItemStack stack) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        return durabilityLeft > SHIELD_BROKEN_THRESHOLD;
    }

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

    public static boolean isInvalidArmor(ItemStack stack) {
        if (stack.isDamageableItem() && ItemUtil.isArmor(stack.getItem())) {
            return !canArmorProtect(stack);
        }
        return false;
    }

    public static boolean isInvalidShield(ItemStack stack) {
        if (stack.isDamageableItem() && ItemUtil.isShield(stack.getItem())) {
            return !canShieldProtect(stack);
        }
        return false;
    }

    public static boolean isBroken(ItemStack stack) {
        return isInvalidLeftClickBlock(stack)
            || isInvalidLeftClickEntity(stack)
            || isInvalidRightClickBlock(stack)
            || isInvalidRightClickEntity(stack)
            || isInvalidRightClickItem(stack)
            || isInvalidArmor(stack)
            || isInvalidShield(stack);
    }
}
