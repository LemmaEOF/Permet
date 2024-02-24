package gay.lemmaeof.permet.weapon.zydra;

import java.util.function.Supplier;

import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.proto.RelicForge;
import kishar.runes.relics.proto.base.SwordRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.Trigger;
import net.minecraft.util.UseAction;

public class BigKnife {
        public static final EffectForge carvingModifier = new EffectForge(CarvingEffect::new)
            // Carving effect requires all these triggers to function
            .trigger(Trigger.USE_ENTITY)
            .trigger(Trigger.USE_TICK)
            .trigger(Trigger.USE_STOP)
            .trigger(Trigger.USE_FINISH);

        private static final Supplier<RelicCore<?>> bigKnifeCore = () -> new SwordRelic.Core()
            .attackDamage(3)
            .attackSpeed(-2.4f)
            .maxUseTime(60)
            .useAction(UseAction.BRUSH);

        public static final Supplier<RelicForge> base = () -> RelicForge.sword()
            .core(bigKnifeCore.get());
}
