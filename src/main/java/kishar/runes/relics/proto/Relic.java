package kishar.runes.relics.proto;

import java.util.function.Supplier;

import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

public interface Relic extends ItemConvertible {
    // relics should try to implement all normal triggers, along with whatever extended triggers are supported for this type.
    <R, T extends TriggerContext<R>> R trigger(T ctx);
    default <R, T extends TriggerContext<R>> R trigger(T ctx, Supplier<R> orElse) {
        var result = trigger(ctx);
        return result != null ? result : orElse.get();
    }

    // Relic should only be implemented on item or the harpies come to kill you
    default Item asItem() {
        return (Item)this;
    }

    <T extends RelicCore<T>> RelicCore<T> core();

}
