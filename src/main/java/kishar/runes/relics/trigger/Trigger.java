package kishar.runes.relics.trigger;

import java.util.function.Predicate;

public record Trigger (String type) {
    public static final Trigger HIT = new Trigger("hit"); // todo
    public static final Trigger DAMAGED = new Trigger("damaged"); // todo
    public static final Trigger USE = new Trigger("use"); //todo
    public static final Trigger USE_ENTITY = new Trigger("use_entity");
    public static final Trigger USE_BLOCK = new Trigger("use_block");
    public static final Trigger USE_TICK = new Trigger("use_tick");
    public static final Trigger USE_FINISH = new Trigger("use_finish");
    public static final Trigger USE_STOP = new Trigger("use_stop");

    public static class Condition {
        public static final Predicate<TriggerContext<?>> ALWAYS = c -> true;
    }
}
