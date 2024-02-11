package darkorg.betterdurability.util;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum VanillaDamageableType {
    // brokenThreshold and protectValue here is somewhat arbitrary
    AXE(Category.TOOL, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof AxeItem;
        }
    },
    PICKAXE(Category.TOOL, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof PickaxeItem;
        }
    },
    SHOVEL(Category.TOOL, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ShovelItem;
        }
    },
    HOE(Category.TOOL, 1, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof HoeItem;
        }
    },
    SHEARS(Category.TOOL, 1, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ShearsItem;
        }
    },
    SWORD(Category.TOOL, 2,1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof SwordItem;
        }
    },
    FISHING_ROD(Category.TOOL, 5, 2) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof FishingRodItem;
        }
    },
    FLINT_AND_STEEL(Category.TOOL, 1, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof FlintAndSteelItem;
        }
    },
    BOW(Category.TOOL, 1,1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof BowItem;
        }
    },
    TRIDENT(Category.TOOL, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof TridentItem;
        }
    },
    CROSSBOW(Category.TOOL, 9, 4) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof CrossbowItem;
        }
    },
    HELMET(Category.ARMOR, 2, 1) {
        public boolean isItemThisType(Item item) {
            return item instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlotType.HEAD;
        }
    },
    CHESTPLATE(Category.ARMOR, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlotType.CHEST;
        }
    },
    LEGGINGS(Category.ARMOR, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlotType.LEGS;
        }
    },
    BOOTS(Category.ARMOR, 2, 1) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlotType.FEET;
        }
    },
    SHIELD(Category.SHIELD, 4, 2) {
        @Override
        public boolean isItemThisType(Item item) {
            return item instanceof ShieldItem;
        }
    };

    public enum Category { TOOL, ARMOR, SHIELD }

    public final Category category;
    public final int brokenThreshold; // damageable things will lose functionality if durability not more than this
    public final int protectValue; // durability will never drop below this, should be not more than brokenThreshold

    VanillaDamageableType(Category category, int brokenThreshold, int protectValue) {
        assert protectValue > 0 && brokenThreshold >= protectValue;
        this.category = category;
        this.brokenThreshold = brokenThreshold;
        this.protectValue = protectValue;
    }

    public boolean isItemBroken(ItemStack stack) {
        int durabilityLeft = stack.getMaxDamage() - stack.getDamageValue();
        return durabilityLeft <= this.brokenThreshold;
    }

    public abstract boolean isItemThisType(Item item);

    @Nullable
    public static VanillaDamageableType getTypeByItem(Item item) {
        return Arrays.stream(VanillaDamageableType.values())
                .filter(t -> t.isItemThisType(item))
                .findAny().orElse(null);
    }

    public static boolean isItemKnownBroken(ItemStack stack) {
        if (stack.isDamageableItem()) {
            VanillaDamageableType itemType = VanillaDamageableType.getTypeByItem(stack.getItem());
            return itemType != null && itemType.isItemBroken(stack);
        }
        return false;
    }
}
