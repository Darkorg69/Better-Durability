package darkorg.betterdurability.event;

import darkorg.betterdurability.util.StackUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Tooltip {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        List<Component> tooltip = event.getToolTip();
        ItemStack stack = event.getItemStack();

        if (StackUtil.isBroken(stack)) {
            tooltip.add(0, new TranslatableComponent("tooltip.betterdurability.broken"));
        }
    }
}
