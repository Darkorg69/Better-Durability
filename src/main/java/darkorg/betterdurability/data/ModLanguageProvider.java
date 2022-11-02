package darkorg.betterdurability.data;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator generator, String modId, String locale) {
        super(generator, modId, locale);
    }

    @Override
    protected void addTranslations() {
        add("tooltip.betterdurability.broken", "Broken");
        //add("tooltip.betterdurability.broken", "\u00A7cBroken");
    }
}
