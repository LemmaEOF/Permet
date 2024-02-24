package gay.lemmaeof.permet.weapon.cardbox;

import java.util.function.Supplier;

import gay.lemmaeof.permet.init.PermetTags;
import kishar.runes.relics.proto.base.MiningToolRelic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.effect.EffectForge;
import kishar.runes.relics.proto.RelicForge;

public class OlReliable {
    public static final EffectForge homeRunModifier = new EffectForge(HomeRunEffect::new)
        .trigger(Trigger.HIT);

    private static final Supplier<RelicCore<?>> olReliableCore = () -> new MiningToolRelic.Core()
        .attackDamage(3)
        .attackSpeed(-3f)
        .effectiveBlocks(PermetTags.HAMMER_MINEABLE);
        
    public static final Supplier<RelicForge> base = () -> RelicForge.tool()
        .core(olReliableCore.get());
}
