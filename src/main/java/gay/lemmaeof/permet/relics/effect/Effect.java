package gay.lemmaeof.permet.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public abstract class Effect {

    public abstract void apply(TriggerContext<?> ctx);

    public Effect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, Map<EffectProperty<?>, Object> props) {
        this.triggers = ImmutableMultimap.copyOf(triggers);
        this.properties = new IdentityHashMap<>(props);
    }

    protected ImmutableMultimap<Trigger, Predicate<TriggerContext<?>>> triggers = ImmutableMultimap.of();
    protected Map<EffectProperty<?>, Object> properties;

    // the argument will guarantee the type!! I hope!!!
    // vanilla does this!
    @SuppressWarnings("unchecked")
    public <T> T property(EffectProperty<T> prop) {
        return (T)properties.get(prop);
    }

    public ImmutableMultimap<Trigger, Predicate<TriggerContext<?>>> getTriggers() {
        return ImmutableMultimap.copyOf(triggers);
    }
}
