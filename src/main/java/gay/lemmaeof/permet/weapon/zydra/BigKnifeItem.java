package gay.lemmaeof.permet.weapon.zydra;

import gay.lemmaeof.permet.init.PermetDamageTypes;
import gay.lemmaeof.permet.init.PermetTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BigKnifeItem extends SwordItem {
	public BigKnifeItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
		if (!user.getWorld().isClient()
				&& !entity.getType().isIn(PermetTags.KNIFE_UNCARVEABLE)
				&& !entity.getCommandTags().contains("permet:carved")) {
			user.setCurrentHand(hand);
			ItemStack activeStack = user.getActiveItem();
			activeStack.getOrCreateNbt().putUuid("target", entity.getUuid());
			return ActionResult.CONSUME_PARTIAL;
		}
		return super.useOnEntity(stack, user, entity, hand);
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 60;
	}

	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		super.onStoppedUsing(stack, world, user, remainingUseTicks);
		if (!world.isClient()) stack.getOrCreateNbt().remove("target");
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BRUSH;
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		super.usageTick(world, user, stack, remainingUseTicks);
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

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (!world.isClient() && user instanceof PlayerEntity player) {
			EntityHitResult res = raycast(user, 5);
			if (res != null) {
				Entity target = res.getEntity();
				if (target.getUuid().equals(stack.getOrCreateNbt().getUuid("target")) && target instanceof LivingEntity living) {
					DamageSource carve = world.getDamageSources().create(PermetDamageTypes.CARVE, player);
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
					//TODO: carve sound
					living.addCommandTag("permet:carved");
					living.damage(carve, Math.min(living.getHealth() / 2, 10));

				}
			}
			stack.getOrCreateNbt().remove("target");
		}
		return super.finishUsing(stack, world, user);
	}

	@Nullable
	private EntityHitResult raycast(LivingEntity user, float distance) {
		Vec3d view = user.getRotationVec(1);
		Vec3d eye = user.getEyePos();
		Vec3d limit = eye.add(view.x * distance, view.y * distance, view.z * distance);
		return ProjectileUtil.raycast(user, eye, limit, new Box(eye, limit), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR, 0f);
	}
}
