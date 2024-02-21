package kishar.runes.relics.proto.base;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.core.ItemRelicCore;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import kishar.runes.relics.proto.core.ToolRelicCore;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

public class SwordRelic extends SwordItem implements Relic {

    public static final class Core extends RelicCoreBase<Core> implements ItemRelicCore<Core>, ToolRelicCore<Core> {}

    protected Multimap<Trigger, Effect> effects = HashMultimap.create();
    protected final RelicCore<?> core;

    public SwordRelic(Item.Settings settings, RelicCore<?> core, List<Effect> effects) {
        super(
            core.getAspect(ToolRelicCore.MATERIAL),
            Math.round(core.getAspect(ItemRelicCore.ATTACK_DAMAGE)),
            core.getAspect(ItemRelicCore.ATTACK_SPEED),
            settings
        );
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
