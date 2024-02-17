package gay.lemmaeof.permet.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.relics.magic.Forge;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public class EffectForge extends Forge<Effect, EffectForge> {

    protected final BiFunction<Multimap<Trigger, Predicate<TriggerContext<?>>>, Map<EffectProperty<?>, Object>, Effect> effect;
    protected final Multimap<Trigger, Predicate<TriggerContext<?>>> triggers = HashMultimap.create();
    protected final Map<EffectProperty<?>, Object> properties = new IdentityHashMap<>();
    protected Function<Effect, Effect> modifications = e -> e;

	@Override
	public Effect forge() {
		return modifications.apply(effect.apply(triggers, properties));
	}

    public EffectForge(BiFunction<Multimap<Trigger, Predicate<TriggerContext<?>>>, Map<EffectProperty<?>, Object>, Effect> factory ) {
        effect = factory;
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
    
}
