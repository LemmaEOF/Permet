package kishar.runes.relics.proto.core;

import kishar.runes.relics.magic.SelfReferential;

//TODO: do we ned subtypes? We could just have builders or something...
public interface RelicCore<R extends RelicCore<R>> extends SelfReferential<R> {
    
    class Aspect<T> {
        public final T defaultValue;
        public Aspect(){ defaultValue = null; }
        public Aspect(T dv) { defaultValue = dv; }
    }

    <T> boolean hasAspect(RelicCore.Aspect<T> aspect);

    // setter
    <T> R aspect(RelicCore.Aspect<T> aspect, T val);

    // getter
    <T> T getAspect(RelicCore.Aspect<T> aspect);
    default <T> T getAspect(RelicCore.Aspect<T> aspect, T defaultValue) {
        // priorizize method default over per-aspect default
        if (!hasAspect(aspect)) return defaultValue;
        return getAspect(aspect);
    }
}
