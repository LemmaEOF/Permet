package gay.lemmaeof.permet.relics.effect;

import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.F;
import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public abstract class Effect {

    public abstract void apply(TriggerContext ctx, Target target);

    public abstract Effect of(Function<Effect, Effect> c);

    protected Trigger trigger;
    public Effect setTrigger(Trigger t){ this.trigger = t; return this; }
    public static Function<Effect, Effect> trigger(Supplier<Trigger> t){
        return F.set(e -> e.setTrigger(t.get()));
    }

    protected Target target;
    public void setTarget(Target t) { this.target = t; }
    public static Function<Effect, Effect> target(Supplier<Target> t){
        return F.set(e -> e.setTarget(t.get()));
    }
}
