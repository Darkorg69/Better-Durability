package darkorg.betterdurability;

import darkorg.betterdurability.setup.ConfigurationHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final Logger LOGGER = LogManager.getLogger();

    public BetterDurability() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }
}