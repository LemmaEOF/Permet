package gay.lemmaeof.permet.relics;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class F {
    @SafeVarargs
    public static <T> T reduce(BiFunction<T, T, T> func, T... args){
        T f = args[0];
        for (int i=1; i<args.length; i++) {
            f = func.apply(f, args[i]);
        }
        return f;
    }

    @SafeVarargs
    public static <T> Function<T, T> compose(Function<T, T>... funcs) {
        return reduce(Function<T, T>::compose, funcs);
    }

    @SafeVarargs
    public static <T> Function<T, T> pipe(Function<T, T>... funcs) {
        return reduce(Function<T, T>::andThen, funcs);
    }

    public static <T> Function<T, T> set(Consumer<T> consumer) {
        return e -> {
            consumer.accept(e);
            return e;
        };
    }
}
