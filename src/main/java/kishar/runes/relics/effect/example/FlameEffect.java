package kishar.runes.relics.effect.example;

import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.Multimap;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.effect.EffectProperty;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

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
