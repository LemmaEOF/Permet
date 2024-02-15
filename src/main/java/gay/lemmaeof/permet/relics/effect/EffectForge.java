package gay.lemmaeof.permet.relics.effect;

import java.util.function.BiFunction;
import java.util.function.Function;

import gay.lemmaeof.permet.relics.magic.Forge;
import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;

public class EffectForge extends Forge<Effect, EffectForge> {

    private final BiFunction<Trigger, Target, Effect> effect;
    private Trigger trigger;
    private Target target;
    private Function<Effect, Effect> modifications = e -> e;

	@Override
	public Effect forge() {
		return modifications.apply(effect.apply(trigger, target));
	}

    public EffectForge(BiFunction<Trigger, Target, Effect> supplier ) {
        effect = supplier;
    }

    public EffectForge trigger(Trigger t){ this.trigger = t; return this; }
    public EffectForge target(Target t) { this.target = t; return this; }

    public EffectForge modify(Function<Effect, Effect> spell){
        modifications = modifications.compose(spell); return this;
    }
    public EffectForge resetModifications(){
        modifications = e -> e; return this;
    }
    
}
