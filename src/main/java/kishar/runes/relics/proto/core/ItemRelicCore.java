package kishar.runes.relics.proto.core;

import java.util.function.Supplier;

import net.minecraft.recipe.Ingredient;
import net.minecraft.util.UseAction;

public interface ItemRelicCore<R extends ItemRelicCore<R>> extends RelicCore<R> {
    Aspect<Integer> MAX_USE_TIME = new Aspect<>(0);
    Aspect<UseAction> USE_ACTION = new Aspect<>(UseAction.NONE);
    Aspect<Float> ATTACK_SPEED = new Aspect<>(1f);
    Aspect<Float> ATTACK_DAMAGE = new Aspect<>(0f);
    Aspect<Integer> ENCHANTABILITY = new Aspect<>(0);
    Aspect<Supplier<Ingredient>> REPAIR_INGREDIENT = new Aspect<>(() -> Ingredient.EMPTY);
    Aspect<Integer> DURABILITY = new Aspect<>(64);

    default R maxUseTime(int v) {
        return aspect(MAX_USE_TIME, v);
    }

    default R useAction(UseAction v) {
        return aspect(USE_ACTION, v);
    }

    default R attackSpeed (float v){
        return aspect(ATTACK_SPEED, v);
    }

    default R attackDamage (float v){
        return aspect(ATTACK_DAMAGE, v);
    }

    default R enchantability (int v){
        return aspect(ENCHANTABILITY, v);
    }

    default R repairIngredient (Supplier<Ingredient> v){
        return aspect(REPAIR_INGREDIENT, v);
    }
    
    default R durability (int v){
        return aspect(DURABILITY, v);
    }
}
