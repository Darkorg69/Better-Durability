package darkorg.betterdurability;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";
    public static final Logger LOGGER = LogManager.getLogger();

    public BetterDurability() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}