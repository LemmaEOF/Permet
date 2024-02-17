package gay.lemmaeof.permet.relics.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Function5;

import gay.lemmaeof.permet.relics.effect.Effect;
import gay.lemmaeof.permet.relics.effect.EffectForge;
import gay.lemmaeof.permet.relics.magic.F;
import gay.lemmaeof.permet.relics.magic.Forge;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.UseAction;

public final class RelicForge extends Forge<Relic, RelicForge> {
    // cooler name for RelicBuilder
    // accepts modifications, then builds a Relic Item

    private Function<Relic, Relic> modifiers = r -> r;

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

    private Function5<Float, ToolMaterial, TagKey<Block>, Item.Settings, List<Effect>, Relic> makeFunc = Relic::new;

    public static RelicForge of(Function5<Float, ToolMaterial, TagKey<Block>, Item.Settings, List<Effect>, Relic> alternate) {
        var forge = new RelicForge();
        forge.makeFunc = alternate;
        return forge;
    }

    @Override
    public Relic forge(){
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

        return modifiers.apply(
            makeFunc.apply(attackSpeed, material, effectiveBlocks, settings == null ? new Item.Settings() : settings, effects)
        );
    };

    public RelicForge material(ToolMaterial mat){
        material = mat; return this;
    }

    public RelicForge attackSpeed (float v){
        attackSpeed = v; return this;
    }

    public RelicForge attackDamage (float v){
        attackDamage = v; return this;
    }

    public RelicForge miningSpeedMultiplier (float v){
        miningSpeedMultiplier = v; return this;
    }

    public RelicForge durability (int v){
        durability = v; return this;
    }

    public RelicForge miningLevel (int v){
        miningLevel = v; return this;
    }

    public RelicForge enchantability (int v){
        enchantability = v; return this;
    }

    public RelicForge repairIngredient (Supplier<Ingredient> v){
        repairIngredient = v; return this;
    }

    public RelicForge effectiveBlocks (TagKey<Block> v){
        effectiveBlocks = v; return this;
    }

    public RelicForge effect (Effect e){
        effects.add(e); return this;
    }

    public RelicForge effect (Supplier<EffectForge> e){
        return effect(e.get().forge());
    }

    public RelicForge settings (Item.Settings v) {
        settings = v; return this;
    }

    // ill conceived ideas below ----------- wip, needs better design
    public RelicForge maxUseTime(BiFunction<Relic, ItemStack, Integer> cb) {
        modifiers = modifiers.compose(F.map(r -> r.setMaxUseTime(cb)));
        return this;
    }

    public RelicForge useAction(BiFunction<Relic, ItemStack, UseAction> cb) {
        modifiers = modifiers.compose(F.map(r -> r.setUseAction(cb)));
        return this;
    }
}
