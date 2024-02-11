package darkorg.betterdurability.event;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

/**
 * These events are triggered in various points where durability is involved.
 * Core code only need to handle them, so we can keep it stable.
 */
public class ItemDurabilityEvent extends Event {
    /**
     * This field holds the "victim" of this event. It is guaranteed to be damageable.
     * <p>
     * Note: Java don't have "const" like C++, "final" just means that you cannot assign other objects to this field,
     * but the internal data of the object is always mutable. For example, you can change durability of this itemStack
     * directly by calling {@link ItemStack#setDamageValue(int)} and this WILL affect following process. You are free to
     * do so as long as you don't shoot your own feet. ;)
     */
    public final ItemStack targetStack;
    public ItemDurabilityEvent(ItemStack targetStack) {
        this.targetStack = targetStack;
    }

    /**
     * This event is triggered when item is going to be broken by a deadly hit.
     * Look at {@link ItemBreaking#reserveDurability} on how to use.
     */
    public static class ItemBreaking extends ItemDurabilityEvent {
        /**
         * This field holds the damage that is originally to be dealt.
         */
        public final int damageValue;
        /**
         * This field is 0 by default. If it is set to a value greater than 0, then tool will reserve durability
         * equal to this value no matter how much damage it takes and will not break. Otherwise, the hurt process will
         * stay same as if we never injected.
         */
        public int reserveDurability = 0;
        public ItemBreaking(ItemStack targetStack, int damageValue) {
            super(targetStack);
            this.damageValue = damageValue;
        }
    }

    /**
     * This event is triggered when a damageable item is going to function and consume durability.
     * <p>
     * Cancelling this means item will not function this time.
     * <p>
     * Note: most enchantments will not trigger this event, except for Soul Speed and Thorns, for they cost durability.
     * <p>
     * Another note: this event is not guaranteed to be triggered before or after {@link ItemBreaking}.
     */
    @Cancelable
    public static class ItemUsage extends ItemDurabilityEvent {
        /**
         * Various types of effects item can make. Note that these types are decided by implementation, every injection
         * point maps to a type here.
         */
        public enum Type {
            TOOL_LEFT_CLICK_BLOCK,
            TOOL_LEFT_CLICK_ENTITY,
            TOOL_RIGHT_CLICK_ITEM,
            TOOL_RIGHT_CLICK_BLOCK,
            TOOL_RIGHT_CLICK_ENTITY,
            ARMOR_ENEMY_ATTACK,
            ARMOR_THORNS,
            HELMET_HEAD_STRUCK,
            BOOTS_SOULSPEED,
            SHIELD_DEFEND
        }

        /**
         * The source of this event, look at {@link Type} for detail.
         */
        public final Type type;
        public ItemUsage(ItemStack targetStack, Type type) {
            super(targetStack);
            this.type = type;
        }
        /**
         * Shorthand for checking if an item can function.
         */
        public static boolean check(ItemStack targetStack, Type type) {
            return !MinecraftForge.EVENT_BUS.post(new ItemUsage(targetStack, type));
        }
    }
}
