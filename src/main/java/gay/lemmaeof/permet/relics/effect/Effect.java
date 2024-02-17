package gay.lemmaeof.permet.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;

import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public abstract class Effect {

    public abstract void apply(TriggerContext ctx, Target target);

    public Effect(Trigger trg, Target tgt, Map<EffectProperty<?>, Object> props) {
        trigger = trg;
        target = tgt;
        properties = new IdentityHashMap<>(props);
    }

    protected Trigger trigger;
    protected Target target;
    protected Map<EffectProperty<?>, Object> properties;

    // the argument will guarantee the type!! I hope!!!
    // vanilla does this!
    @SuppressWarnings("unchecked")
    public <T> T property(EffectProperty<T> prop) {
        return (T)properties.get(prop);
    }
}
