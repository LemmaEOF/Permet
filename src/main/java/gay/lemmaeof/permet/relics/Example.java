package gay.lemmaeof.permet.relics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.effect.Effect;
import gay.lemmaeof.permet.relics.effect.FlameEffect;
import gay.lemmaeof.permet.relics.effect.Property;
import gay.lemmaeof.permet.relics.proto.Relic;
import gay.lemmaeof.permet.relics.proto.RelicForge;
import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;

public class Example {
    public static List<Item> test(BiFunction<String, Item, Item> regFunc) {
        /*
        * 
        * working via composition means we don't need to worry about
        * copying etc. the composition chain acts as the prototyping spell, 
        * effectively a constructed factory. further composition modifies
        * the spell, but the relic is only ever passed through once.
        */

        // let's experiment with a magic axe upgrade tree

        Supplier<Effect> burnGuysEffect = () -> F.<Effect>pipe(
            Property.Duration.of(5),
            Property.Intensity.of(1),
            Effect.trigger(Trigger.HIT),
            Effect.target(Target.ENTITY)
        ).apply(new FlameEffect());

        Supplier<Effect> burnGuysTwiceButStronger = () -> F.<Effect>pipe(
            Property.Duration.of(10),
            Property.Intensity.of(3),
            Effect.trigger(Trigger.HIT),
            Effect.target(Target.ENTITY)
        ).apply(new FlameEffect());

        Function<RelicForge, RelicForge> summonBaseAxe = F.<RelicForge>pipe(
            RelicForge.applyMaterial(ToolMaterials.DIAMOND),
            RelicForge.attackSpeed(-3),
            RelicForge.attackDamage(5)
        );

        Function<RelicForge, RelicForge> t1Upgrade = RelicForge.effect(burnGuysEffect);
        Function<RelicForge, RelicForge> t2Upgrade = t1Upgrade.andThen(RelicForge.applyMaterial(ToolMaterials.NETHERITE));
        Function<RelicForge, RelicForge> t2Sidegrade = t1Upgrade.andThen(RelicForge.effect(burnGuysTwiceButStronger));

        Relic baseAxe = RelicForge.forge(summonBaseAxe);
        Relic swagAxeT2 = RelicForge.forge(t1Upgrade.compose(summonBaseAxe));
        Relic swagAxeT3 = RelicForge.forge(t2Upgrade.compose(summonBaseAxe));
        Relic magicAxeT3 = RelicForge.forge(t2Sidegrade.compose(summonBaseAxe));

        // then register all of them etc
        var SWAG = regFunc.apply("swag_axe", baseAxe);
        var SWAG2 = regFunc.apply("swag_axe_t2", swagAxeT2);
        var SWAG3 = regFunc.apply("swag_axe_t3", swagAxeT3);
        var MAGIC_SWAG = regFunc.apply("volcano_axe_t3", magicAxeT3);

        return List.of(SWAG, SWAG2, SWAG3, MAGIC_SWAG);
    }
}
