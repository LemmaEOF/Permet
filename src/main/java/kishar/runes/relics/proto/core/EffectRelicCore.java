package kishar.runes.relics.proto.core;

public interface EffectRelicCore<R extends EffectRelicCore<R>> extends RelicCore<R> {
    Aspect<Integer> DURATION = new Aspect<>(1);
    Aspect<Float> INTENSITY = new Aspect<>(1f);

    default R duration(int duration) {
        return aspect(DURATION, duration);
    }

    default R intensity(float intensity) {
        return aspect(INTENSITY, intensity);
    }
}
