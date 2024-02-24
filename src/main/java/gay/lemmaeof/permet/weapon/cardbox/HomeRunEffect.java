package gay.lemmaeof.permet.weapon.cardbox;

import java.util.function.Predicate;

import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.init.PermetDamageTypes;
import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;

public class HomeRunEffect extends Effect {

    public HomeRunEffect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers, RelicCore<?> core) {
        super(triggers, core);
    }

    @Override
    public void apply(TriggerContext<?> ctx) {
        if (ctx.trigger == Trigger.HIT) {
            if (ctx.user.getRandom().nextInt(25) == 0) {
                var target = ((TriggerContext.Hit) ctx).target;
                target.damage(ctx.world.getDamageSources().create(PermetDamageTypes.HOME_RUN, ctx.user), 8);
				target.takeKnockback(5, ctx.user.getX() - target.getX(), ctx.user.getZ() - target.getZ());
            }
        }
    }
    
}