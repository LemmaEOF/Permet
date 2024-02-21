package kishar.runes.relics.proto.core;

import java.util.function.Supplier;

import net.minecraft.recipe.Ingredient;
import net.minecraft.util.UseAction;

public interface ItemRelicCore<R extends ItemRelicCore<R>> extends RelicCore<R> {
    public static final Aspect<Integer> MAX_USE_TIME = new Aspect<>(0);
    public static final Aspect<UseAction> USE_ACTION = new Aspect<>(UseAction.NONE);
    public static final Aspect<Float> ATTACK_SPEED = new Aspect<>(1f);
    public static final Aspect<Float> ATTACK_DAMAGE = new Aspect<>(0f);
    public static final Aspect<Integer> ENCHANTABILITY = new Aspect<>(0);
    public static final Aspect<Supplier<Ingredient>> REPAIR_INGREDIENT = new Aspect<>(() -> Ingredient.EMPTY);
    public static final Aspect<Integer> DURABILITY = new Aspect<>(64);

    public default R maxUseTime(int v) {
        return aspect(MAX_USE_TIME, v);
    }

    public default R useAction(UseAction v) {
        return aspect(USE_ACTION, v);
    }

    public default R attackSpeed (float v){
        return aspect(ATTACK_SPEED, v);
    }

    public default R attackDamage (float v){
        return aspect(ATTACK_DAMAGE, v);
    }

    public default R enchantability (int v){
        return aspect(ENCHANTABILITY, v);
    }

    public default R repairIngredient (Supplier<Ingredient> v){
        return aspect(REPAIR_INGREDIENT, v);
    }
    
    public default R durability (int v){
        return aspect(DURABILITY, v);
    }
}
