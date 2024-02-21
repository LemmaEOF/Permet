package kishar.runes.relics.proto.core;

import kishar.runes.relics.magic.SelfReferential;

public interface RelicCore<R extends RelicCore<R>> extends SelfReferential<R> {
    
    public static class Aspect<T> {
        public final T defaultValue;
        public Aspect(){ defaultValue = null; }
        public Aspect(T dv) { defaultValue = dv; }
    }

    public <T> boolean hasAspect(RelicCore.Aspect<T> aspect);

    // setter
    public <T> R aspect(RelicCore.Aspect<T> aspect, T val);

    // getter
    public <T> T getAspect(RelicCore.Aspect<T> aspect);
    public default <T> T getAspect(RelicCore.Aspect<T> aspect, T defaultValue) {
        // priorizize method default over per-aspect default
        if (!hasAspect(aspect)) return defaultValue;
        return getAspect(aspect);
    }
}
