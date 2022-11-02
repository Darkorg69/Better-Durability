package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.util.StackUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvents {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        List<ITextComponent> tooltip = event.getToolTip();

        if (StackUtil.isBroken(event.getItemStack())) {
            tooltip.add(0, new TranslationTextComponent("tooltip.betterdurability.broken").withStyle(TextFormatting.RED));
        }
    }
}
