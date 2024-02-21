package gay.lemmaeof.permet.init;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.util.PermetToolMaterial;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.effect.EffectProperty;
import kishar.runes.relics.effect.example.CarvingEffect;
import kishar.runes.relics.effect.example.FlameEffect;
import kishar.runes.relics.magic.F;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.RelicForge;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.core.ItemRelicCore;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.ToolRelicCore;
import kishar.runes.relics.trigger.Trigger;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.util.UseAction;

public class RelicExample {
    public static Item[] test(BiFunction<String, Item, Item> regFunc) {
        /*
        * we use the builder pattern for more traditional object
        * composition, and compose functions for more complex behavior.
        */

        // let's experiment with a magic axe upgrade tree

        // First, let's decide on some basic attributes to define an "axe."
        // When we know what type we're modifying, builder grammar becomes available.
        Function<MiningToolRelic.Core, MiningToolRelic.Core> axeAttribues = core -> core
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

        final class upgrades {
            public static <A extends Relic, B extends RelicCore<B>, T extends RelicForge<A, B, T>> T t2Upgrade(T r) { return r.effect(burnGuysEffect); }

            Supplier<RelicForge<MiningToolRelic, MiningToolRelic.Core, ?>> base = () -> RelicForge.tool().core(axeAttribues);
        }
        // var t2Upgrade = F.<RelicForge<?, ?, ?>>save(r -> r.effect(burnGuysEffect));
        var t3Upgrade = t2Upgrade.andThen(
            r -> r.core(c -> c.aspect(ToolRelicCore.MATERIAL, ToolMaterials.NETHERITE))
        );
        var t3Sidegrade = F.<RelicForge<?, ?, ?>>save(t2Upgrade.andThen(r -> r.effect(burnGuysStronger)));

        // Finally, we apply all spells to create the upgrade tree.
        // There's a number of ways to do this, depending on what you think is pretty.
        
        
        MiningToolRelic baseAxe = base.get().forge();

        RelicForge<MiningToolRelic, MiningToolRelic.Core, ?> swagAxeT2F = upgrades.t2Upgrade(base.get());
        MiningToolRelic swagAxeT2 = swagAxeT2F.forge();

        MiningToolRelic swagAxeT3 = base.get().cast(c -> c).forge();
        MiningToolRelic magicAxeT3 = swagAxeT2F.forge(t3Sidegrade);

        // then register all of them etc
        var SWAG = regFunc.apply("swag_axe", baseAxe);
        var SWAG2 = regFunc.apply("swag_axe_t2", swagAxeT2);
        var SWAG3 = regFunc.apply("swag_axe_t3", swagAxeT3);
        var MAGIC_SWAG = regFunc.apply("volcano_axe_t3", magicAxeT3);

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

        MiningToolRelic bigKnife = new RelicForge()
            .attackDamage(3)
            .attackSpeed(-2.4f)
            .effect(carvingModifier.forge())
            .maxUseTime((r, i) -> 60)
            .useAction((r, i) -> UseAction.BRUSH)
            .material(PermetToolMaterial.PERMET)
            .settings(flagged.apply(new Item.Settings(), PermetFlags.PERFECTED))
            .forge();

        return regFunc.apply("relic_knife", bigKnife);
    }
}
