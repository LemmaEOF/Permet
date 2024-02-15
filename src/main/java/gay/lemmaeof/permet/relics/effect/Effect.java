package gay.lemmaeof.permet.relics.effect;

import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public abstract class Effect {

    public abstract void apply(TriggerContext ctx, Target target);

    public Effect(Trigger trg, Target tgt) {
        trigger = trg;
        target = tgt;
    }

    protected Trigger trigger;
    protected Target target;
}
