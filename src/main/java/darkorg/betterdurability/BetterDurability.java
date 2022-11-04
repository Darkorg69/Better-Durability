package darkorg.betterdurability;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;

@Mod(BetterDurability.MOD_ID)
public class BetterDurability {
    public static final String MOD_ID = "betterdurability";

    public IEventBus forgeBus = MinecraftForge.EVENT_BUS;

    public BetterDurability() {
        forgeBus.register(this);
    }
}