package kishar.runes.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

public abstract class Effect {

    protected ImmutableMultimap<Trigger, Predicate<TriggerContext<?>>> triggers;
    protected Map<EffectProperty<?>, Object> properties;

    public Effect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, Map<EffectProperty<?>, Object> props) {
        this.triggers = ImmutableMultimap.copyOf(triggers);
        this.properties = new IdentityHashMap<>(props);
    }

    public abstract void apply(TriggerContext<?> ctx);

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
