package kishar.runes.relics.proto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.mojang.datafixers.util.Function3;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.magic.Forge;
import kishar.runes.relics.proto.base.BowRelic;
import kishar.runes.relics.proto.base.SwordRelic;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import net.minecraft.item.Item;

public class RelicForge<T extends Relic, C extends RelicCore<C>, R extends RelicForge<T, C, R>> extends Forge<T, R> {
    // cooler name for RelicBuilder
    // accepts modifications, then builds a Relic Item
    
    protected final Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, T> relicSource;
    protected final Supplier<? extends C> coreSource;

    protected Supplier<Item.Settings> settings;
    protected List<Effect> effects = new ArrayList<>();
    protected Function<C, C> coreAugments = c -> c;

    public RelicForge(final Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, T> relicSource, final Supplier<? extends C> coreSource) {
        this.relicSource = relicSource;
        this.coreSource = coreSource;
    }

    public static <T extends Relic, C extends RelicCore<?>> RelicForge<T, RelicCoreBase.BasicCore, ?> of(Function3<Item.Settings, ? super RelicCore<?>, List<Effect>, T> relicSource) {
        return new RelicForge<>(relicSource, RelicCoreBase.BasicCore::new);
    }

    public static RelicForge<MiningToolRelic, MiningToolRelic.Core, ?> tool() {
        return new RelicForge<>(MiningToolRelic::new, MiningToolRelic.Core::new);
    }

    public static RelicForge<SwordRelic, SwordRelic.Core, ?> sword() {
        return new RelicForge<>(SwordRelic::new, SwordRelic.Core::new);
    }

    public static RelicForge<BowRelic, BowRelic.Core, ?> bow() {
        return new RelicForge<>(BowRelic::new, BowRelic.Core::new);
    }

    @Override
    public T forge(){
        return relicSource.apply(
            settings == null ? new Item.Settings() : settings.get(),
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

    public R settings (Supplier<Item.Settings> v) {
        settings = v; return self();
    }

    public R core(Function<? super C, ? extends C> augment) {
        coreAugments = coreAugments.andThen(augment);
        return self();
    }

}
