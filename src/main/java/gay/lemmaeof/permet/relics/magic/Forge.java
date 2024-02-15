package gay.lemmaeof.permet.relics.magic;

import java.util.function.Function;

// abstract prototype builder
public abstract class Forge<T, R extends Forge<T, R>> implements SelfReferential<R> {

    // apply function
    public R cast(Function<R, R> spell) {
        return spell.apply(self());
    }
    
    // build real object
    public abstract T forge();
    public T forge(Function<R, R> spell) {
        return cast(spell).forge();
    };
}
