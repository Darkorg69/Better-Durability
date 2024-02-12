package darkorg.betterdurability;

import darkorg.betterdurability.setup.ConfigurationHandler;
import darkorg.betterdurability.util.NaiveLoggerWrapper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final NaiveLoggerWrapper LOGGER = new NaiveLoggerWrapper(LogManager.getLogger())
            .withPrefix("[Better Durability] ");

    public BetterDurability() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ConfigurationHandler.SERVER_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }
}