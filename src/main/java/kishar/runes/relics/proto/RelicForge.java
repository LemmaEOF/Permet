package kishar.runes.relics.proto;

import java.util.List;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Function3;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.base.BowRelic;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.base.SwordRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import net.minecraft.item.Item;

public class RelicForge extends AbstractRelicForge<RelicForge>{
    
    public RelicForge(Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource, Supplier<? extends RelicCore<?>> coreSource) {
        this.relicSource = relicSource;
        this.coreSource = coreSource;
    }

    public static RelicForge of(Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource) {
        return new RelicForge(relicSource, RelicCoreBase.BasicCore::new);
    }

    public static RelicForge tool() {
        return new RelicForge(MiningToolRelic::new, MiningToolRelic.Core::new);
    }

    public static RelicForge sword() {
        return new RelicForge(SwordRelic::new, SwordRelic.Core::new);
    }

    public static RelicForge bow() {
        return new RelicForge(BowRelic::new, BowRelic.Core::new);
    }
}
