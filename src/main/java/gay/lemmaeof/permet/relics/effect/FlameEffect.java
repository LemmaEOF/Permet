package gay.lemmaeof.permet.relics.effect;

import java.util.function.Function;

import gay.lemmaeof.permet.relics.target.Target;
import gay.lemmaeof.permet.relics.trigger.TriggerContext;

public class FlameEffect extends Effect implements Property.Duration, Property.Intensity {
    private int intensity = 1;
    private int duration = 1;

    @Override public void setIntensity(int v) { intensity = v; }
    @Override public int getIntensity() { return intensity; }
    @Override public void setDuration(int v) { duration = v; }
    @Override public int getDuration() { return duration; }

    @Override
    public void apply(TriggerContext ctx, Target target) {
    }

    @Override
    public Effect of(Function<Effect, Effect> c) {
        return c.apply(new FlameEffect());
    }

}
