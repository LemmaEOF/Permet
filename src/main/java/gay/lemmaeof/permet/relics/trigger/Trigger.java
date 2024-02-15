package gay.lemmaeof.permet.relics.trigger;

import java.util.function.Predicate;

public interface Trigger extends Predicate<TriggerContext> {
    public static final HitTrigger HIT = new HitTrigger();
    public static final BlockBreakTrigger BREAK = new BlockBreakTrigger();
    public static final DamagedTrigger DAMAGED = new DamagedTrigger();
}
