package kishar.runes.relics.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Function3;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.magic.Forge;
import kishar.runes.relics.proto.base.BowRelic;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.base.SwordRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import net.minecraft.item.Item;

public class RelicForge extends Forge<Relic, RelicForge> {
    // cooler name for RelicBuilder
    // accepts modifications, then builds a Relic Item
    
    protected Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource;
    protected RelicCore<?> core;
    protected Item.Settings settings;
    protected List<Effect> effects = new ArrayList<>();

    public static RelicForge of(Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource) {
        return new RelicForge(relicSource, new RelicCoreBase.BasicCore());
    }

    public static RelicForge tool() {
        return new RelicForge(MiningToolRelic::new, new MiningToolRelic.Core());
    }

    public static RelicForge sword() {
        return new RelicForge(SwordRelic::new, new SwordRelic.Core());
    }

    public static RelicForge bow() {
        return new RelicForge(BowRelic::new, new BowRelic.Core());
    }

    public RelicForge(Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource, RelicCore<?> core) {
        this.relicSource = relicSource;
        this.core = core;
    }

    @Override
    public Relic forge(){
        return relicSource.apply(
            settings == null ? new Item.Settings() : settings,
            core,
            new ArrayList<>(effects)
        );
    }
    
    public RelicForge effect (Effect e){
        effects.add(e);
        return this;
    }

    public RelicForge effect (Supplier<EffectForge> e){
        return effect(e.get().forge());
    }

    public RelicForge settings (Item.Settings v) {
        settings = v;
        return this;
    }

    public RelicForge core(RelicCore<?> core) {
        this.core = core;
        return this;
    }

    public <T> RelicForge aspect(RelicCore.Aspect<T> aspect, T value) {
        core.aspect(aspect, value);
        return this;
    }

}
