package gay.lemmaeof.permet.relics.proto;

import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Relic extends MiningToolItem {

    protected Relic(float attackSpeed, ToolMaterial material, TagKey<Block> effectiveBlocks, Item.Settings settings) {
        super(0, attackSpeed, material, effectiveBlocks, settings);
	}

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return isSuitableFor(state) ? this.miningSpeed : 1.0F;
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
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
}
