package gay.lemmaeof.permet.relics.effect;

import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.Trigger;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public class FlameEffect extends Effect implements EffectProperty.Duration<FlameEffect>, EffectProperty.Intensity<FlameEffect> {

    public FlameEffect(Trigger trg, Target tgt) { super(trg, tgt); }

	private int intensity = 1;
    private int duration = 1;

    @Override public FlameEffect intensity(int v) { intensity = v; return this; }
    @Override public int getIntensity() { return intensity; }
    @Override public FlameEffect duration(int v) { duration = v; return this; }
    @Override public int getDuration() { return duration; }

    @Override
    public void apply(TriggerContext ctx, Target target) {
        // flame stuff
    }
}
