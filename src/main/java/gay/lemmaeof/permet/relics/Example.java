package gay.lemmaeof.permet.relics;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.effect.Effect;
import gay.lemmaeof.permet.relics.effect.EffectForge;
import gay.lemmaeof.permet.relics.effect.FlameEffect;
import gay.lemmaeof.permet.relics.effect.EffectProperty;
import gay.lemmaeof.permet.relics.proto.Relic;
import gay.lemmaeof.permet.relics.proto.RelicForge;
import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;

public class Example {
    public static Item[] test(BiFunction<String, Item, Item> regFunc) {
        /*
        * we use the builder pattern for more traditional object
        * composition, and compose functions for more complex behavior.
        */

        // let's experiment with a magic axe upgrade tree

        // First, let's decide on some basic attributes to define an "axe."
        Function<RelicForge, RelicForge> axeAttribues = r -> r
            .attackSpeed(-3)
            .attackDamage(5);

        // We also want some fun magic effects. This one should set
        // an enemy on fire on hit.
        Supplier<EffectForge> burnGuysEffect = () -> new EffectForge(FlameEffect::new)
            .trigger(Trigger.HIT)
            .target(Target.ENTITY);
        
        // We can make a stronger version of the effect by modifying its properties.
        Function<EffectForge, EffectForge> enhanceEffect = e -> e
            .property(EffectProperty.DURATION, 10)
            .property(EffectProperty.INTENSITY, 5f);

        Supplier<EffectForge> burnGuysStronger = () -> enhanceEffect.apply(burnGuysEffect.get());

        // We can also determine upgrade attributes without considering the actual item we're upgrading
        Function<RelicForge, RelicForge> t2Upgrade = r -> r.effect(burnGuysEffect);
        Function<RelicForge, RelicForge> t3Upgrade = t2Upgrade.andThen(r -> r.material(ToolMaterials.NETHERITE));
        Function<RelicForge, RelicForge> t3Sidegrade = t2Upgrade.andThen(r -> r.effect(burnGuysStronger));

        // Finally, we apply all spells to create the upgrade tree.
        // There's a number of ways to do this, depending on what you think is pretty.
        Relic baseAxe = new RelicForge().cast(axeAttribues).forge();

        RelicForge swagAxeT2F = new RelicForge().cast(axeAttribues).cast(t2Upgrade);
        Relic swagAxeT2 = swagAxeT2F.forge();

        Relic swagAxeT3 = new RelicForge().cast(axeAttribues.compose(t3Upgrade)).forge();
        Relic magicAxeT3 = swagAxeT2F.forge(t3Sidegrade);

        // then register all of them etc
        var SWAG = regFunc.apply("swag_axe", baseAxe);
        var SWAG2 = regFunc.apply("swag_axe_t2", swagAxeT2);
        var SWAG3 = regFunc.apply("swag_axe_t3", swagAxeT3);
        var MAGIC_SWAG = regFunc.apply("volcano_axe_t3", magicAxeT3);

        return new Item[]{SWAG, SWAG2, SWAG3, MAGIC_SWAG};
    }
}
