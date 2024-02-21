package kishar.runes.relics.effect.example;

import java.util.function.Predicate;

import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.weapon.zydra.CarvingEffect;
import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.core.EffectRelicCore;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

public class FlameEffect extends Effect {

    public static final class Core extends RelicCoreBase<Core> implements EffectRelicCore<Core> {}

    public FlameEffect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, RelicCore<?> core) {
        super(triggers, core);
    }

    @Override
    public void apply(TriggerContext<?> ctx) {
        // flame stuff
    }
}
