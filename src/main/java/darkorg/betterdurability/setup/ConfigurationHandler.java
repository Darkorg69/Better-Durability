package darkorg.betterdurability.setup;

import com.google.common.collect.ImmutableList;
import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.VanillaDamageableType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mod.EventBusSubscriber
public class ConfigurationHandler {
    public static ForgeConfigSpec COMMON_CONFIG;

    public static final String CATEGORY_BLACKLISTS = "blacklists";
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_CATEGORY_NAMES;
    public static final Set<VanillaDamageableType.Category> DISABLED_CATEGORIES = new HashSet<>();
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_TYPE_NAMES;
    public static final Set<VanillaDamageableType> DISABLED_TYPES = new HashSet<>();
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> BLACKLISTED_ITEM_IDS;
    public static final Set<Item> BLACKLISTED_ITEMS = new HashSet<>();

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Blacklist things, loved by server owners ;)").push(CATEGORY_BLACKLISTS);
        DISABLED_CATEGORY_NAMES = COMMON_BUILDER.comment("List of disabled damage protection categories. Available: TOOL, ARMOR, SHIELD.")
                .defineList("disabledCategoryNames", ImmutableList.of(), obj -> true);
        DISABLED_TYPE_NAMES = COMMON_BUILDER.comment("List of disabled damage protection types. Available: AXE, PICKAXE, SHOVEL, HOE, SHEARS, SWORD, FISHING_ROD, FLINT_AND_STEEL, BOW, TRIDENT, CROSSBOW, HELMET, CHESTPLATE, LEGGINGS, BOOTS, SHIELD.")
                .defineList("disabledTypeNames", ImmutableList.of(), obj -> true);
        BLACKLISTED_ITEM_IDS = COMMON_BUILDER.comment("List of blacklisted items. Format is modId:itemId, modId can be omitted for vanilla.")
                .defineList("blacklistedItemIds", ImmutableList.of(), obj -> true);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        loadEnumList(VanillaDamageableType.Category.class, DISABLED_CATEGORY_NAMES.get(), DISABLED_CATEGORIES);
        loadEnumList(VanillaDamageableType.class, DISABLED_TYPE_NAMES.get(), DISABLED_TYPES);
        loadItemIdList(BLACKLISTED_ITEM_IDS.get(), BLACKLISTED_ITEMS);
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        reloadEnumList(VanillaDamageableType.Category.class, DISABLED_CATEGORY_NAMES.get(), DISABLED_CATEGORIES);
        reloadEnumList(VanillaDamageableType.class, DISABLED_TYPE_NAMES.get(), DISABLED_TYPES);
        reloadItemIdList(BLACKLISTED_ITEM_IDS.get(), BLACKLISTED_ITEMS);
    }

    @SuppressWarnings("deprecation")
    private static void loadItemIdList(final List<? extends String> src, final Set<Item> dst) {
        for (String itemId : src) {
            Item item = Registry.ITEM.getOptional(ResourceLocation.of(itemId, ':')).orElse(null);
            if (item == null) {
                BetterDurability.LOGGER.error("Trying to blacklist item {} but it does not exist ...", itemId);
            } else {
                dst.add(item);
            }
        }
    }
    private static void reloadItemIdList(final List<? extends String> src, final Set<Item> dst) {
        dst.clear();
        loadItemIdList(src, dst);
    }

    private static <T extends Enum<T>> void loadEnumList(Class<T> clazz, final List<? extends String> src, final Set<T> dst) {
        for (String name : src) {
            try {
                dst.add(Enum.valueOf(clazz, name));
            } catch (IllegalArgumentException e) {
                BetterDurability.LOGGER.error("Trying to blacklist enum {} in {} but it does not exist ...", name, clazz);
            }
        }
    }

    private static <T extends Enum<T>> void reloadEnumList(Class<T> clazz, final List<? extends String> src, final Set<T> dst) {
        dst.clear();
        loadEnumList(clazz, src, dst);
    }
}
