package darkorg.betterdurability.event;

import darkorg.betterdurability.BetterDurability;
import darkorg.betterdurability.data.ModLanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterDurability.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(true, new ModLanguageProvider(generator, BetterDurability.MOD_ID, "en_us"));
    }
}
