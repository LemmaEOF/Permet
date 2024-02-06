package gay.lemmaeof.permet.init;

import gay.lemmaeof.permet.Permet;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class PermetTags {
	public static final TagKey<Block> HAMMER_MINEABLE = TagKey.of(Registries.BLOCK.getKey(), new Identifier(Permet.MODID, "mineable/hammer"));
	public static final TagKey<EntityType<?>> KNIFE_UNCARVEABLE = TagKey.of(Registries.ENTITY_TYPE.getKey(), new Identifier(Permet.MODID, "knife_uncarvevable"));

	public static void init() {}
}
