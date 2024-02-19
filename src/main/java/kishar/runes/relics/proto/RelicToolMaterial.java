package kishar.runes.relics.proto;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public class RelicToolMaterial implements ToolMaterial {
	private final int durability;
	private final float miningSpeedMultiplier;
	private final float attackDamage;
	private final int miningLevel;
	private final int enchantability;
	private final Supplier<Ingredient> repairIngredient;

	RelicToolMaterial(int durability, float miningSpeedMultiplier, float attackDamage,
							   int miningLevel, int enchantability, Supplier<Ingredient> repairIngredient) {
		this.durability = durability;
		this.miningSpeedMultiplier = miningSpeedMultiplier;
		this.attackDamage = attackDamage;
		this.miningLevel = miningLevel;
		this.enchantability = enchantability;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurability() {
		return durability;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return miningSpeedMultiplier;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public int getMiningLevel() {
		return miningLevel;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return repairIngredient.get();
	}
}
