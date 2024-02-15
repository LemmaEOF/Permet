package gay.lemmaeof.permet.relics.effect;

import java.util.function.Function;

public abstract class EffectProperty  {

    public static interface Duration<R extends Effect> {
        public R duration(int v);
        public int getDuration();
        public static Function<Effect, Effect> of(int v) {
            return (e) -> {
                if (e instanceof Duration ed) ed.duration(v);
                return e;
            };
        }
    }

    public static interface Intensity<R extends Effect> {
        public R intensity(int v);
        public int getIntensity();
        public static Function<Effect, Effect> of(int v) {
            return (e) -> {
                if (e instanceof Intensity ed) ed.intensity(v);
                return e;
            };
        }
    }
}
