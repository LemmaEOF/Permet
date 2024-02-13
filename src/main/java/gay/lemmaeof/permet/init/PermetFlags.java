package gay.lemmaeof.permet.init;

import gay.lemmaeof.featureful.api.FeatureRegistry;
import gay.lemmaeof.permet.Permet;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.Identifier;

public class PermetFlags {
	//TODO: figure out a name for phase 2
	public static final FeatureFlag PHASE_2 = FeatureRegistry.getFeatureFlag(new Identifier(Permet.MODID, ""));
	public static final FeatureFlag SYMBIONIC = FeatureRegistry.getFeatureFlag(new Identifier(Permet.MODID, "symbionic"));
	public static final FeatureFlag PERFECTED = FeatureRegistry.getFeatureFlag(new Identifier(Permet.MODID, "perfected"));
}
