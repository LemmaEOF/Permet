package kishar.runes.relics.proto.core;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public interface ToolRelicCore<R extends ToolRelicCore<R>> extends RelicCore<R> {

    public static final Aspect<ToolMaterial> MATERIAL = new Aspect<ToolMaterial>(new RelicToolMaterial(0, 0, 0, 0, 0, Ingredient::empty));
    public static final Aspect<Float> MINING_SPEED_MULTIPLIER = new Aspect<>(1f);
    public static final Aspect<Integer> MINING_LEVEL = new Aspect<>(0);
    public static final Aspect<TagKey<Block>> EFFECTIVE_BLOCKS = new Aspect<>();

    public default R material(ToolMaterial v){
        return aspect(MATERIAL, v);
    }

    public default R miningSpeedMultiplier (float v){
        return aspect(MINING_SPEED_MULTIPLIER, v);
    }

    public default R miningLevel (int v){
        return aspect(MINING_LEVEL, v);
    }

    public default R effectiveBlocks (TagKey<Block> v){
        return aspect(EFFECTIVE_BLOCKS, v);
    }
}
