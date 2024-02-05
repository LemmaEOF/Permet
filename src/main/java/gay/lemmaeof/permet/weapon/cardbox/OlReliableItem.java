package gay.lemmaeof.permet.weapon.cardbox;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import gay.lemmaeof.permet.init.PermetDamageTypes;
import gay.lemmaeof.permet.init.PermetTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Ol' Reliable! Cardbox's relic weapon!
 * It will do regular flat damage, however it will have a *slight chance*
 * of doing a fuckton more with a lot of knockback and a home run sound effect.
 * High damage, low swing rate, squeaky noise when it hits.
 */
public class OlReliableItem extends ToolItem implements Vanishable {
	private final float attackDamage;
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public OlReliableItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
		super(material, settings);
		this.attackDamage = (float)attackDamage + material.getAttackDamage();
		ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative();
	}

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		if (state.isOf(Blocks.COBWEB)) {
			return 15.0F;
		} else {
			return state.isIn(PermetTags.HAMMER_MINEABLE) ? 1.5F : 1.0F;
		}
	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		World world = attacker.getWorld();
		if (!world.isClient) {
			if (attacker.getRandom().nextInt(25) == 0) {
				target.damage(world.getDamageSources().create(PermetDamageTypes.HOME_RUN, attacker), 8);
				target.takeKnockback(5, target.getX() - attacker.getX(), target.getZ() - attacker.getZ());
			}
		}
		stack.damage(1, attacker, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		return true;
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (state.getHardness(world, pos) != 0.0F) {
			stack.damage(2, miner, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
		}

		return true;
	}

	public boolean isSuitableFor(BlockState state) {
		return state.isIn(PermetTags.HAMMER_MINEABLE);
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}
}
