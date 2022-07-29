package darkorg.betterdurability;

import darkorg.betterdurability.setup.EventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {

    public static final String MOD_ID = "betterdurability";
    IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

    public BetterDurability() {
        EventHandler.init();
        bus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }
}
