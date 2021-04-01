package devarcana.darkrites.common;

import devarcana.darkrites.common.registry.DRRitualFunctions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class DarkRites implements ModInitializer {
	public static final String MODID = "darkrites";

	@Override
	public void onInitialize() {
		DRRitualFunctions.init();
	}

	public static Identifier id(String name) {
		return new Identifier(MODID, name);
	}
}
