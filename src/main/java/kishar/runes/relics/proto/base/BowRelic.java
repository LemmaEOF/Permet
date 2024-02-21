package kishar.runes.relics.proto.base;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.core.ItemRelicCore;
import kishar.runes.relics.proto.core.RangedRelicCore;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;

public class BowRelic extends BowItem implements Relic {

    public static final class Core extends RelicCoreBase<Core> implements ItemRelicCore<Core>, RangedRelicCore<Core> {}

    protected Multimap<Trigger, Effect> effects = HashMultimap.create();
    protected final RelicCore<?> core;

    public BowRelic(Item.Settings settings, RelicCore<?> core, List<Effect> effects) {
        super(settings);
        this.core = core;
        for (Effect e : effects) {
            for (var trigger : e.getTriggers().keys()) {
                this.effects.put(trigger, e);
            }
        }
    }

    @Override
    public <R, T extends TriggerContext<R>> R trigger(T ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'trigger'");
    }


}