package kishar.runes.relics.proto;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class Relic extends MiningToolItem {
    

    protected Multimap<Trigger, Effect> effects = HashMultimap.create();

    protected Relic(float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Item.Settings settings,
        List<Effect> effects) {
        super(0, attackSpeed, material, effectiveBlocks, settings);
        
        for (Effect e : effects) {
            for (var trigger : e.getTriggers().keys()) {
                this.effects.put(trigger, e);
            }
        }
	}

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return isSuitableFor(state) ? this.miningSpeed : 1.0F;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND) );
        return true;
    }

    // optional overrides for most salient methods of Item
    // is this a good idea? ¯\_(ツ)_/¯
    // maybe code generation is the way to go
    // or do it like LootContextParameterSet
    protected BiFunction<Relic, ItemStack, Integer> maxUseTime;
    void setMaxUseTime(BiFunction<Relic, ItemStack, Integer> v) {
        maxUseTime = v;
    }
    @Override
	public int getMaxUseTime(ItemStack stack) {
		return maxUseTime == null ? super.getMaxUseTime(stack) : maxUseTime.apply(this, stack);
	}

    protected BiFunction<Relic, ItemStack, UseAction> useAction;
    void setUseAction(BiFunction<Relic, ItemStack, UseAction> v) {
        useAction = v;
    }
    @Override
	public UseAction getUseAction(ItemStack stack) {
		return useAction == null ? super.getUseAction(stack) : useAction.apply(this, stack);
	}

    // events
    protected <R, T extends TriggerContext<R>> R trigger(T ctx){
        effects.get(ctx.trigger).forEach(e -> e.apply(ctx));
        return ctx.getResult();
    }

    protected <R, T extends TriggerContext<R>> R trigger(T ctx, Supplier<R> orElse){
        var result = trigger(ctx);
        return result != null ? result : orElse.get();
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
        return trigger(new TriggerContext<ItemStack>(this, Trigger.USE_FINISH, stack, world, user),
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
