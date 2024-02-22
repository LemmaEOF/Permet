package kishar.runes.relics.effect.core;

import kishar.runes.relics.proto.core.RelicCore;

public interface EffectCore<R extends EffectCore<R>> extends RelicCore<R> {
    Aspect<Integer> DURATION = new Aspect<>(1);
    Aspect<Float> INTENSITY = new Aspect<>(1f);

    default R duration(int duration) {
        return aspect(DURATION, duration);
    }

    default R intensity(float intensity) {
        return aspect(INTENSITY, intensity);
    }
}
