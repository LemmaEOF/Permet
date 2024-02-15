package gay.lemmaeof.permet.relics.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.F;
import gay.lemmaeof.permet.relics.effect.Effect;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class RelicForge {
    // cooler name for RelicBuilder
    // accepts modifications, then builds a Relic Item

    private float attackSpeed;
    private float attackDamage;
    private float miningSpeedMultiplier;
    private int durability;
    private int miningLevel;
    private int enchantability;
    private Supplier<Ingredient> repairIngredient;
    TagKey<Block> effectiveBlocks = BlockTags.CANDLES; // idk
    private ToolMaterial material = null; //new RelicToolMaterial(0, 0, 0, 0, 0, Ingredient::empty);

    private List<Effect> effects = new ArrayList<>();

    private Item.Settings settings;

    public static Relic forge(Function<RelicForge, RelicForge> spell) {
        return spell.apply(new RelicForge()).buildRelic();
    }
    
    private Relic buildRelic(){
        if (material == null) 
            material = new RelicToolMaterial(0, 0, 0, 0, 0, Ingredient::empty);

        if (repairIngredient == null)
            repairIngredient = () -> material.getRepairIngredient();

        material = new RelicToolMaterial(
            durability + material.getDurability(),
            miningSpeedMultiplier + material.getMiningSpeedMultiplier(),
            attackDamage + material.getAttackDamage(), 
            miningLevel + material.getMiningLevel(), 
            enchantability + material.getMiningLevel(), 
            repairIngredient
        );

        if (settings == null) settings = new Item.Settings();

        return new Relic(attackSpeed, material, effectiveBlocks, settings);
    };

    public static Function<RelicForge, RelicForge> applyMaterial(ToolMaterial mat){
        return F.set(r -> r.material = mat);
    }

    public static Function<RelicForge, RelicForge> attackSpeed (float v){
        return F.set(r -> r.attackSpeed = v);
    }

    public static Function<RelicForge, RelicForge> attackDamage (float v){
        return F.set(r -> r.attackDamage = v);
    }

    public static Function<RelicForge, RelicForge> miningSpeedMultiplier (float v){
        return F.set(r -> r.miningSpeedMultiplier = v);
    }

    public static Function<RelicForge, RelicForge> durability (int v){
        return F.set(r -> r.durability = v);
    }

    public static Function<RelicForge, RelicForge> miningLevel (int v){
        return F.set(r -> r.miningLevel = v);
    }

    public static Function<RelicForge, RelicForge> enchantability (int v){
        return F.set(r -> r.enchantability = v);
    }

    public static Function<RelicForge, RelicForge> repairIngredient (Supplier<Ingredient> v){
        return F.set(r -> r.repairIngredient = v);
    }

    public static Function<RelicForge, RelicForge> effectiveBlocks (TagKey<Block> v){
        return F.set(r -> r.effectiveBlocks = v);
    }

    public static Function<RelicForge, RelicForge> effect (Supplier<Effect> e){
        return F.set(r -> r.effects.add(e.get()));
    }
}
