package kishar.runes.relics.proto.core;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public interface ToolRelicCore<R extends ToolRelicCore<R>> extends RelicCore<R> {

    Aspect<ToolMaterial> MATERIAL = new Aspect<>(new RelicToolMaterial(0, 0, 0, 0, 0, Ingredient::empty));
    Aspect<Float> MINING_SPEED_MULTIPLIER = new Aspect<>(1f);
    Aspect<Integer> MINING_LEVEL = new Aspect<>(0);
    Aspect<TagKey<Block>> EFFECTIVE_BLOCKS = new Aspect<>();

    default R material(ToolMaterial v){
        return aspect(MATERIAL, v);
    }

    default R miningSpeedMultiplier (float v){
        return aspect(MINING_SPEED_MULTIPLIER, v);
    }

    default R miningLevel (int v){
        return aspect(MINING_LEVEL, v);
    }

    default R effectiveBlocks (TagKey<Block> v){
        return aspect(EFFECTIVE_BLOCKS, v);
    }
}
