package kishar.runes.relics.proto.core;

import java.util.function.Predicate;

import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;

public interface RangedRelicCore<R extends RangedRelicCore<R>> extends RelicCore<R> {
    Aspect<Predicate<ItemStack>> PROJECTILES = new Aspect<>(BowItem.BOW_PROJECTILES);
    Aspect<Integer> RANGE = new Aspect<>(BowItem.RANGE);

    default R projectiles(Predicate<ItemStack> v) {
        return aspect(PROJECTILES, v);
    }

    default R range(int v) {
        return aspect(RANGE, v);
    }
}
