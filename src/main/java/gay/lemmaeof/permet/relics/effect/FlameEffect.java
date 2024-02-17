package gay.lemmaeof.permet.relics.effect;

import java.util.Map;

import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public class FlameEffect extends Effect {

    public FlameEffect(Trigger trg, Target tgt, Map<EffectProperty<?>, Object> props) {
        super(trg, tgt, props);
        this.properties.putIfAbsent(EffectProperty.DURATION, DEFAULT_DURATION);
        this.properties.putIfAbsent(EffectProperty.INTENSITY, DEFAULT_INTENSITY); 
    }

    private int DEFAULT_DURATION = 1;
    private float DEFAULT_INTENSITY = 1f;

    @Override
    public void apply(TriggerContext ctx, Target target) {
        // flame stuff
    }
}
