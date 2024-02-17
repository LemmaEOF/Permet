package gay.lemmaeof.permet.relics.effect.example;

import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.relics.effect.Effect;
import gay.lemmaeof.permet.relics.effect.EffectProperty;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public class FlameEffect extends Effect {
    
    private int DEFAULT_DURATION = 1;
    private float DEFAULT_INTENSITY = 1f;

    public FlameEffect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, Map<EffectProperty<?>, Object> props) {
        super(triggers, props);
        this.properties.putIfAbsent(EffectProperty.DURATION, DEFAULT_DURATION);
        this.properties.putIfAbsent(EffectProperty.INTENSITY, DEFAULT_INTENSITY); 
    }

    @Override
    public void apply(TriggerContext<?> ctx) {
        // flame stuff
    }
}
