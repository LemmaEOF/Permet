package kishar.runes.relics.proto;

import java.util.function.Supplier;

import kishar.runes.relics.trigger.TriggerContext;

public interface Relic {
    // relics should try to implement all normal triggers, along with whatever extended triggers are supported for this type.
    public <R, T extends TriggerContext<R>> R trigger(T ctx);
    public default <R, T extends TriggerContext<R>> R trigger(T ctx, Supplier<R> orElse) {
        var result = trigger(ctx);
        return result != null ? result : orElse.get();
    }

}
