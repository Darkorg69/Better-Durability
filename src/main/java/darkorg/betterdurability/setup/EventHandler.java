package darkorg.betterdurability.setup;

import darkorg.betterdurability.event.LeftClick;
import darkorg.betterdurability.event.RightClick;
import darkorg.betterdurability.event.Tooltip;
import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
    public static void init() {
        MinecraftForge.EVENT_BUS.register(new Tooltip());
        MinecraftForge.EVENT_BUS.register(new LeftClick());
        MinecraftForge.EVENT_BUS.register(new RightClick());
    }
}
