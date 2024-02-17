package gay.lemmaeof.permet.relics.trigger;

import java.util.function.Predicate;

public interface Trigger extends Predicate<TriggerContext> {
    public static final HitTrigger HIT = new HitTrigger();
    public static final BlockBreakTrigger BREAK = new BlockBreakTrigger();
    public static final DamagedTrigger DAMAGED = new DamagedTrigger();
    // finishUsing
    // right click, left click
    // release
    // hold
    // eat?

    /*
     * effects contain a list of triggers which assign them to "events" on relic creation
     * 
     * some effects may have mandatory triggers, others may be general and apply to any trigger.
     * 
     * 
     */
}
