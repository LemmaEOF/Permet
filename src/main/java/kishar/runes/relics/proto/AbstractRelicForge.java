package kishar.runes.relics.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Function3;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.magic.Forge;
import kishar.runes.relics.proto.core.RelicCore;
import net.minecraft.item.Item;

public abstract class AbstractRelicForge<R extends AbstractRelicForge<R>> extends Forge<Relic, R> {
    // cooler name for RelicBuilder
    // accepts modifications, then builds a Relic Item
    
    protected Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, Relic> relicSource;
    protected Supplier<? extends RelicCore<?>> coreSource;

    protected Item.Settings settings;
    protected List<Effect> effects = new ArrayList<>();
    protected Function<RelicCore<?>, RelicCore<?>> coreAugments = c -> c;

    @Override
    public Relic forge(){
        return relicSource.apply(
            settings == null ? new Item.Settings() : settings,
            coreAugments.apply(coreSource.get()),
            new ArrayList<>(effects)
        );
    };
    
    public R effect (Effect e){
        effects.add(e); return self();
    }

    public R effect (Supplier<EffectForge> e){
        return effect(e.get().forge());
    }

    public R settings (Item.Settings v) {
        settings = v; return self();
    }

    public R core(Function<RelicCore<?>, RelicCore<?>> augment) {
        coreAugments = coreAugments.andThen(augment);
        return self();
    }

    public R baseCore(Supplier<RelicCore<?>> newBase) {
        this.coreSource = newBase;
        return self();
    }

}
