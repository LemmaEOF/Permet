package gay.lemmaeof.permet.weapon.zydra;

import java.util.Map;
import java.util.function.Predicate;

import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Multimap;

import gay.lemmaeof.permet.init.PermetDamageTypes;
import gay.lemmaeof.permet.init.PermetItems;
import gay.lemmaeof.permet.init.PermetTags;
import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.effect.EffectProperty;
import kishar.runes.relics.trigger.Trigger;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CarvingEffect extends Effect {

    public CarvingEffect(Multimap<Trigger, Predicate<TriggerContext<?>>> triggers,
            Map<EffectProperty<?>, Object> props) {
        super(triggers, props);
    }

    @Override
    public void apply(TriggerContext<?> ctx) {
        if (ctx.trigger == Trigger.USE_TICK) {
            var ctxUse = (TriggerContext.Using) ctx;
            usageTick(ctxUse.world, ctxUse.user, ctxUse.stack, ctxUse.remainingUseTicks);
        } else if (ctx.trigger == Trigger.USE_ENTITY) {
            useOnEntity((TriggerContext.OnEntity) ctx);
        } else if (ctx.trigger == Trigger.USE_STOP) {
            onStoppedUsing(ctx.stack, ctx.world);
        } else if (ctx.trigger == Trigger.USE_FINISH) {
            finishUsing(ctx.stack, ctx.world, ctx.user, ctx.relic.core().getAspect(PermetItems.SHELL_TIER));
        }
    }


	private void useOnEntity(TriggerContext.OnEntity ctx) {
		if (!ctx.user.getWorld().isClient()
				&& !ctx.entity.getType().isIn(PermetTags.KNIFE_UNCARVEABLE)
				&& !ctx.entity.getCommandTags().contains("permet:carved")) {
			ctx.user.setCurrentHand(ctx.hand);
			ItemStack activeStack = ctx.user.getActiveItem();
			activeStack.getOrCreateNbt().putUuid("target", ctx.entity.getUuid());
			ctx.setResult(ActionResult.CONSUME_PARTIAL);
		}
	}

	private void onStoppedUsing(ItemStack stack, World world) {
		if (!world.isClient()) stack.getOrCreateNbt().remove("target");
	}

	private void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (!world.isClient && remainingUseTicks % 5 == 4) {
			EntityHitResult res = raycast(user, 5);
			if (res != null) {
				Entity target = res.getEntity();
				if (!stack.getOrCreateNbt().containsUuid("target") || !target.getUuid().equals(stack.getOrCreateNbt().getUuid("target"))) {
					user.clearActiveItem();
					stack.getOrCreateNbt().remove("target");
				}
			} else {
				user.clearActiveItem();
				stack.getOrCreateNbt().remove("target");
			}
		}
	}

	private void finishUsing(ItemStack stack, World world, LivingEntity user, int tier) {
		if (!world.isClient() && user instanceof PlayerEntity player) {
			EntityHitResult res = raycast(user, 5);
			if (res != null) {
				Entity target = res.getEntity();
				if (target.getUuid().equals(stack.getOrCreateNbt().getUuid("target")) && target instanceof LivingEntity living) {
					DamageSource carve = world.getDamageSources().create(PermetDamageTypes.CARVE, player);
					
					if (tier >= 4) {
						Identifier identifier = living.getLootTable();
						LootTable lootTable = world.getServer().getLootManager().getLootTable(identifier);
						LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)world)
								.add(LootContextParameters.THIS_ENTITY, target)
								.add(LootContextParameters.ORIGIN, living.getPos())
								.add(LootContextParameters.DAMAGE_SOURCE, carve)
								.addOptional(LootContextParameters.KILLER_ENTITY, user)
								.addOptional(LootContextParameters.DIRECT_KILLER_ENTITY, user)
								.add(LootContextParameters.LAST_DAMAGE_PLAYER, player).luck(player.getLuck());
							
						LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
						lootTable.generateLoot(lootContextParameterSet, living.getLootTableSeed(), living::dropStack);
					}
					
					//TODO: carve sound
					living.addCommandTag("permet:carved");
					living.damage(carve, Math.min(living.getHealth() / 2, 10));

				}
			}
			stack.getOrCreateNbt().remove("target");
		}
	}
    
    @Nullable
	private EntityHitResult raycast(LivingEntity user, float distance) {
		Vec3d view = user.getRotationVec(1);
		Vec3d eye = user.getEyePos();
		Vec3d limit = eye.add(view.x * distance, view.y * distance, view.z * distance);
		return ProjectileUtil.raycast(user, eye, limit, new Box(eye, limit), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR, 0f);
	}
}
