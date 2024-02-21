package gay.lemmaeof.permet.init;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.util.PermetToolMaterial;
import gay.lemmaeof.permet.weapon.zydra.CarvingEffect;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.effect.EffectProperty;
import kishar.runes.relics.effect.example.FlameEffect;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.RelicForge;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.base.SwordRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.ToolRelicCore;
import kishar.runes.relics.trigger.Trigger;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.UseAction;

public class RelicExample {
    // public interface RelicSpell extends Function<RelicForge<?>, RelicForge<?>> {}

    public static Item[] test(BiFunction<String, Item, Item> regFunc) {
        /*
        * we use the builder pattern for more traditional object
        * composition, and compose functions for more complex behavior.
        */

        // let's experiment with a magic axe upgrade tree

        // First, let's decide on some basic attributes to define an "axe."
        // When we know what type we're modifying, builder grammar becomes available.
        Supplier<MiningToolRelic.Core> axeAttributes = () -> new MiningToolRelic.Core()
            .attackSpeed(-3)
            .attackDamage(5);

        // We also want some fun magic effects. This one should set an enemy on fire on hit.
        Supplier<EffectForge> burnGuysEffect = () -> new EffectForge(FlameEffect::new);
        
        // We can make a stronger version of the effect by modifying its properties.
        Function<EffectForge, EffectForge> enhanceEffect = e -> e
            .property(EffectProperty.DURATION, 10)
            .property(EffectProperty.INTENSITY, 5f);

        Supplier<EffectForge> burnGuysStronger = () -> enhanceEffect.apply(burnGuysEffect.get());

        // We can also determine upgrade attributes without considering the actual item we're upgrading
        Function<RelicForge, RelicForge> t2Upgrade = r -> r.effect(burnGuysEffect);
        var t3Upgrade = t2Upgrade.andThen(
            r -> r.core(c -> c.aspect(ToolRelicCore.MATERIAL, ToolMaterials.NETHERITE))
        );
        var t3Sidegrade = t2Upgrade.andThen(r -> r.effect(burnGuysStronger));

        // Finally, we apply all spells to create the upgrade tree.
        // There's a number of ways to do this, depending on what you think is pretty.
        
        Supplier<RelicForge> base = () -> new RelicForge(MiningToolRelic::new, axeAttributes);
        Relic baseAxe = base.get().forge();

        Supplier<RelicForge> swagAxeT2F = () -> base.get().cast(t2Upgrade);
        Relic swagAxeT2 = swagAxeT2F.get().forge();

        Relic swagAxeT3 = swagAxeT2F.get().cast(t3Upgrade).forge();
        Relic magicAxeT3 = swagAxeT2F.get().forge(t3Sidegrade);

        // then register all of them etc
        var SWAG = regFunc.apply("swag_axe", baseAxe.asItem());
        var SWAG2 = regFunc.apply("swag_axe_t2", swagAxeT2.asItem());
        var SWAG3 = regFunc.apply("swag_axe_t3", swagAxeT3.asItem());
        var MAGIC_SWAG = regFunc.apply("volcano_axe_t3", magicAxeT3.asItem());

        return new Item[]{SWAG, SWAG2, SWAG3, MAGIC_SWAG};
    }

    public static Item makeBigKnife(BiFunction<String, Item, Item> regFunc, BiFunction<Item.Settings, FeatureFlag, Item.Settings> flagged) {
        // var carvingModifier = F.<Trigger, EffectForge>fold(EffectForge::trigger, 
        //         Trigger.USE_ENTITY, Trigger.USE_TICK, Trigger.USE_STOP, Trigger.USE_FINISH
        //     ).apply(new EffectForge(CarvingEffect::new));

        // sorry, had to get that out of my system. here's a normal one:
        EffectForge carvingModifier = new EffectForge(CarvingEffect::new)
            // Carving effect requires all these triggers to function
            .trigger(Trigger.USE_ENTITY)
            .trigger(Trigger.USE_TICK)
            .trigger(Trigger.USE_STOP)
            .trigger(Trigger.USE_FINISH);

        Supplier<RelicCore<?>> bigKnifeCore = () -> new SwordRelic.Core()
            .attackDamage(3)
            .attackSpeed(-2.4f)
            .maxUseTime(60)
            .useAction(UseAction.BRUSH)
            .material(PermetToolMaterial.PERMET);

        var bigKnife = RelicForge.sword().baseCore(bigKnifeCore)
            .settings(flagged.apply(new Item.Settings(), PermetFlags.PERFECTED))
            .effect(carvingModifier.forge())
            .forge();

        return regFunc.apply("relic_knife", bigKnife.asItem());
    }
}
