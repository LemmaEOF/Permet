package gay.lemmaeof.permet.init;

import gay.lemmaeof.permet.Permet;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PermetTags {
	public static final TagKey<Block> HAMMER_MINEABLE = TagKey.of(Registries.BLOCK.getKey(), new Identifier(Permet.MODID, "mineable/hammer"));

	public static void init() {}
}
