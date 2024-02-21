package kishar.runes.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

public abstract class Effect {

    protected ImmutableMultimap<Trigger, Predicate<TriggerContext<?>>> triggers;
    protected RelicCore<?> core;

    public Effect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, RelicCore<?> core) {
        this.triggers = ImmutableMultimap.copyOf(triggers);
        this.core = core;
    }

    public abstract void apply(TriggerContext<?> ctx);

    // the argument will guarantee the type!! I hope!!!
    // vanilla does this!
    @SuppressWarnings("unchecked")
    public <T> T aspect(RelicCore.Aspect<T> aspect) {
        return (T)core.getAspect(aspect);
    }

    public ImmutableMultimap<Trigger, Predicate<TriggerContext<?>>> getTriggers() {
        return ImmutableMultimap.copyOf(triggers);
    }
}
