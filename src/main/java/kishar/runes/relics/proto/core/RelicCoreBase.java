package kishar.runes.relics.proto.core;

import java.util.HashMap;
import java.util.Map;

public class RelicCoreBase<R extends RelicCoreBase<R>> implements RelicCore<R> {

    public static final class BasicCore extends RelicCoreBase<BasicCore> {}

    private final Map<RelicCore.Aspect<?>, Object> aspects = new HashMap<>();

    @Override
    public <T> R aspect(Aspect<T> key, T val) {
        aspects.put(key, val);
        return self();
    }

    @Override
    public <T> boolean hasAspect(Aspect<T> key) {
        return aspects.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAspect(Aspect<T> key) {
        return (T) aspects.get(key);
    }
    
}
