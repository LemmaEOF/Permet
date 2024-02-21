package kishar.runes.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.magic.Forge;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

public class EffectForge extends Forge<Effect, EffectForge> {

    protected final EffectFactory factory;
    protected final Multimap<Trigger, Predicate<TriggerContext<?>>> triggers = HashMultimap.create();
    protected final Map<EffectProperty<?>, Object> properties = new IdentityHashMap<>();
    protected Function<Effect, Effect> modifications = e -> e;

	@Override
	public Effect forge() {
		return modifications.apply(factory.create(triggers, properties));
	}

    public EffectForge(EffectFactory factory ) {
        this.factory = factory;
    }

    public EffectForge trigger(Trigger trig) {return trigger(trig, Trigger.Condition.ALWAYS); }
    public EffectForge trigger(Trigger trig, Predicate<TriggerContext<?>> predicate) {
        triggers.put(trig, predicate);
        return this;
    }

    public EffectForge modify(Function<Effect, Effect> spell){
        modifications = modifications.compose(spell); return this;
    }
    public EffectForge resetModifications(){
        modifications = e -> e; return this;
    }

    public <T> EffectForge property(EffectProperty<T> prop, T value) {
        properties.put(prop, value);
        return this;
    }

    @FunctionalInterface
    public interface EffectFactory {
        Effect create(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, Map<EffectProperty<?>, Object> effects);
    }
    
}
