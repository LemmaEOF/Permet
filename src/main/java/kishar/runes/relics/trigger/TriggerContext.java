package kishar.runes.relics.trigger;

import kishar.runes.relics.proto.Relic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class TriggerContext<ResultType> {
    public final Relic relic;
    public final Trigger trigger;
    public final ItemStack stack;
    public final LivingEntity user;
    public final World world;
    public ResultType result;

    public TriggerContext(Relic relic, Trigger trigger, ItemStack stack, World world, LivingEntity user) {
        this.relic = relic;
        this.trigger = trigger;
        this.stack = stack;
        this.world = world;
        this.user = user;
    }

    public void setResult(ResultType r) { result = r; }
    public ResultType getResult() { return result; }

    public String type() { return trigger.type(); }

/*

examples to consider:

jump: effect that launches entities into the air
- target self (Target.SELF)
- target entity (Target.ENTITY)
- target mobs around self (Target.SELF.aoe())
- target mobs around entity (Target.ENTITY.aoe())

smelt
- smelt broken block 
- smelt placed block

burn
- trigger on damaged: target is entity hitting user
- trigger on hit: target is entity hit by user

* specialized targeting seems to be a behavior associated with type
* usually an effect will only affect one type of target
* target should be defined via effect methods?
* maybe use fancy composition for complex targeting

*/

    public static final class OnEntity extends TriggerContext<ActionResult> {
        public final LivingEntity entity;
        public final Hand hand;
        public OnEntity(Relic relic, ItemStack stack, World world, LivingEntity user, LivingEntity e, Hand h) {
            super(relic, Trigger.USE_ENTITY, stack, world, user);
            entity = e;
            hand = h;
        }
    }

    public static final class OnBlock extends TriggerContext<ActionResult> {
        public final ItemUsageContext usageContext;
        public OnBlock(Relic relic, ItemStack stack, World world, LivingEntity user, ItemUsageContext ctx) {
            super(relic, Trigger.USE_BLOCK, stack, world, user);
            usageContext = ctx;
        }
    }

    public static final class Using extends TriggerContext<Void> {
        public final int remainingUseTicks;
        public Using(Relic relic, Trigger type, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
            super(relic, type, stack, world, user);
            this.remainingUseTicks = remainingUseTicks;
        }
    }
}
