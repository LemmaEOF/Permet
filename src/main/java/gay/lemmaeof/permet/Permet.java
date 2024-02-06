package gay.lemmaeof.permet;

import gay.lemmaeof.permet.init.PermetDamageTypes;
import gay.lemmaeof.permet.init.PermetItems;
import gay.lemmaeof.permet.init.PermetTags;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Permet implements ModInitializer {
	public static final String MODID = "permet";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {
		PermetItems.init();
		PermetDamageTypes.init();
		PermetTags.init();
	}
}