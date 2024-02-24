package kishar.runes.relics.effect;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import kishar.runes.relics.magic.Forge;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

import java.util.function.Function;
import java.util.function.Predicate;

public class EffectForge extends Forge<Effect, EffectForge> {

    protected final EffectFactory factory;
    protected final Multimap<Trigger, Predicate<TriggerContext<?>>> triggers = HashMultimap.create();
    //TODO: do we need a type on the core?
    protected final RelicCore<?> core;
    protected Function<Effect, Effect> modifications = e -> e;

	@Override
	public Effect forge() {
		return modifications.apply(factory.create(triggers, core));
	}

    public EffectForge(EffectFactory factory, RelicCore<?> core) {
        this.factory = factory;
        this.core = core;
    }

    public EffectForge(EffectFactory factory) {
        this.factory = factory;
        this.core = new RelicCoreBase<>();
    }

    public EffectForge trigger(Trigger trig) {
        return trigger(trig, Trigger.Condition.ALWAYS);
    }

    public EffectForge trigger(Trigger trig, Predicate<TriggerContext<?>> predicate) {
        triggers.put(trig, predicate);
        return this;
    }

    public EffectForge modify(Function<Effect, Effect> spell) {
        modifications = modifications.compose(spell); return this;
    }
    public EffectForge resetModifications() {
        modifications = e -> e; return this;
    }

    public <T> EffectForge aspect(RelicCore.Aspect<T> aspect, T value) {
        core.aspect(aspect, value);
        return this;
    }

    @FunctionalInterface
    public interface EffectFactory {
        Effect create(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, RelicCore<?> core);
    }
    
}
