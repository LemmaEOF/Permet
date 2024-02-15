package gay.lemmaeof.permet.relics.trigger;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.effect.Effect;

public interface Trigger extends Predicate<TriggerContext> {
    public static final Supplier<Trigger> HIT = HitTrigger::new;
    public static final Supplier<Trigger> BREAK = BlockBreakTrigger::new;
    public static final Supplier<Trigger> DAMAGED = DamagedTrigger::new;
}
