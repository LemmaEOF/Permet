package gay.lemmaeof.permet.relics.effect;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import com.mojang.datafixers.util.Function3;

import gay.lemmaeof.permet.relics.magic.Forge;
import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;

public class EffectForge extends Forge<Effect, EffectForge> {

    private final Function3<Trigger, Target, Map<EffectProperty<?>, Object>, Effect> effect;
    private Trigger trigger;
    private Target target;
    private Map<EffectProperty<?>, Object> properties = new IdentityHashMap<>();
    private Function<Effect, Effect> modifications = e -> e;

	@Override
	public Effect forge() {
		return modifications.apply(effect.apply(trigger, target, properties));
        
	}

    public EffectForge(Function3<Trigger, Target, Map<EffectProperty<?>, Object>, Effect> constructor ) {
        effect = constructor;
    }

    public EffectForge trigger(Trigger t){ this.trigger = t; return this; }
    public EffectForge target(Target t) { this.target = t; return this; }

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
