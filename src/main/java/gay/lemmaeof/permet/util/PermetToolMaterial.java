package gay.lemmaeof.permet.util;

import gay.lemmaeof.permet.init.PermetItems;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PermetToolMaterial implements ToolMaterial {
	public static final PermetToolMaterial INSTANCE = new PermetToolMaterial();

	private PermetToolMaterial() {}

	@Override
	public int getDurability() {
		return 0;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return 0;
	}

	@Override
	public float getAttackDamage() {
		return 6;
	}

	@Override
	public int getMiningLevel() {
		return 4;
	}

	@Override
	public int getEnchantability() {
		return 18;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(PermetItems.PERMET);
	}
}
