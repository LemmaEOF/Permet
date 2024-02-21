package kishar.runes.relics.proto.base;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.core.ItemRelicCore;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.proto.core.RelicCoreBase;
import kishar.runes.relics.proto.RelicToolMaterial;
import kishar.runes.relics.proto.core.ToolRelicCore;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class MiningToolRelic extends MiningToolItem implements Relic {

    public static final class Core extends RelicCoreBase<Core> implements ItemRelicCore<Core>, ToolRelicCore<Core> {}
    
    protected Multimap<Trigger, Effect> effects = HashMultimap.create();
    protected final RelicCore<?> core;

    public MiningToolRelic(Item.Settings settings, RelicCore<?> core, List<Effect> effects) {
        super(
            core.getAspect(ItemRelicCore.ATTACK_DAMAGE),
            core.getAspect(ItemRelicCore.ATTACK_SPEED),
            RelicToolMaterial.of(core),
            core.getAspect(ToolRelicCore.EFFECTIVE_BLOCKS),
            settings
        );
        this.core = core;
        for (Effect e : effects) {
            for (var trigger : e.getTriggers().keys()) {
                this.effects.put(trigger, e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends RelicCore<T>> RelicCore<T> core() {
        return (T) core;
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return isSuitableFor(state) ? this.miningSpeed : 1.0F;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) );
        return true;
    }

    // optional overrides for the most salient methods of Item
    @Override
	public int getMaxUseTime(ItemStack stack) {
		return core.getAspect(ItemRelicCore.MAX_USE_TIME);
	}

    @Override
	public UseAction getUseAction(ItemStack stack) {
		return core.getAspect(ItemRelicCore.USE_ACTION);
	}

    // events
    public <R, T extends TriggerContext<R>> R trigger(T ctx){
        effects.get(ctx.trigger).forEach(e -> e.apply(ctx));
        return ctx.getResult();
    }


    @Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
		trigger(new TriggerContext.Using(this, Trigger.USE_STOP, stack, world, user, remainingUseTicks));
	}

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.usageTick(world, user, stack, remainingUseTicks);
        trigger(new TriggerContext.Using(this, Trigger.USE_TICK, stack, world, user, remainingUseTicks));
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return trigger(new TriggerContext<>(this, Trigger.USE_FINISH, stack, world, user),
            () -> super.finishUsing(stack, world, user));
        
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return trigger(new TriggerContext.OnEntity(this, stack, entity.getWorld(), user, entity, hand),
            () -> super.useOnEntity(stack, user, entity, hand));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return trigger(new TriggerContext.OnBlock(this, context.getStack(), context.getWorld(), context.getPlayer(), context),
            () -> super.useOnBlock(context));
    }

}
