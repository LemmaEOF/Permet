package gay.lemmaeof.permet.relics.effect;

import java.util.function.Function;

public abstract interface Property {

    public static interface Duration extends Property {
        
        public void setDuration(int v);
        public int getDuration();
        public static <T> Function<T,T> of(int v) {
            return (e) -> {
                if (e instanceof Duration ed) ed.setDuration(v);
                return e;
            };
        }
    }

    public static interface Intensity {
        public void setIntensity(int v);
        public int getIntensity();
        public static <T> Function<T,T> of(int v) {
            return (e) -> {
                if (e instanceof Intensity ed) ed.setIntensity(v);
                return e;
            };
        }
    }
}
