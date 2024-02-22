package gay.lemmaeof.permet.init;

import java.util.function.Function;
import java.util.function.Supplier;

import gay.lemmaeof.permet.Permet;
import gay.lemmaeof.permet.util.PermetToolMaterial;
import gay.lemmaeof.permet.weapon.cardbox.OlReliableItem;
import gay.lemmaeof.permet.weapon.star.MagitekBowItem;
import gay.lemmaeof.permet.weapon.zydra.BigKnife;
import kishar.runes.relics.effect.Effect;
import kishar.runes.relics.proto.RelicForge;
import kishar.runes.relics.proto.core.ToolRelicCore;
import kishar.runes.relics.proto.core.RelicCore.Aspect;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PermetItems {
	public static final Item PERMET = register("permet", new Item(new Item.Settings()));
	public static final Item EMPTY_SHELL_UNIT = register("empty_shell_unit", new Item(new Item.Settings()));
	public static final Item VANADIC_SHELL_UNIT = register("vanadic_shell_unit", new Item(new Item.Settings()));
	public static final Item BLUE_SHELL_UNIT = register("blue_shell_unit", new Item(flagged(new Item.Settings(), PermetFlags.PHASE_2)));
	public static final Item SYMBIONIC_SHELL_UNIT = register("symbionic_shell_unit", new Item(flagged(new Item.Settings(), PermetFlags.SYMBIONIC)));
	public static final Item PERFECTED_SHELL_UNIT = register("perfected_shell_unit", new Item(flagged(new Item.Settings(), PermetFlags.PERFECTED)));
	public static final Item MAGITEK_BOW = register("magitek_bow", new MagitekBowItem(flagged(new Item.Settings(), PermetFlags.PERFECTED)));
	public static final Item OL_RELIABLE = register("ol_reliable", new OlReliableItem(PermetToolMaterial.PERMET, 3, -4, flagged(new Item.Settings(), PermetFlags.PERFECTED)));

	static {
		Foundry.build("dagger", "big_knife", BigKnife.base, BigKnife.carvingModifier::forge);
	}

	public static final Aspect<Integer> SHELL_TIER = new Aspect<>(1);

	public static final ItemGroup PERMET_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Permet.MODID, "permet"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.permet.permet"))
					.icon(() -> new ItemStack(PERMET))
					.entries(((displayContext, entries) -> {
						entries.add(PERMET);
						entries.add(EMPTY_SHELL_UNIT);
						entries.add(VANADIC_SHELL_UNIT);
						entries.add(BLUE_SHELL_UNIT);
						entries.add(SYMBIONIC_SHELL_UNIT);
						entries.add(PERFECTED_SHELL_UNIT);
						entries.add(MAGITEK_BOW);
						entries.add(OL_RELIABLE);
						
					}))
					.build()
	);

	private static class Foundry {
		// you get the idea.
		private static Function<RelicForge, RelicForge> setShell(int v) {
			return r -> r.aspect(SHELL_TIER, v);
		}

		private static Item make (Supplier<RelicForge> s, int t, FeatureFlag flags, Function<RelicForge, RelicForge> f) {
			return f.apply(s.get()).cast(setShell(t)).settings(flagged(new Item.Settings(), flags)).forge().asItem();
		}

		private static Item make (Supplier<RelicForge> s, int t, Function<RelicForge, RelicForge> f) {
			return f.apply(s.get()).cast(setShell(t)).settings(new Item.Settings()).forge().asItem();
		}

		private static Function<RelicForge, RelicForge> diamond = r -> r.aspect(ToolRelicCore.MATERIAL, PermetToolMaterial.PERMET_DIAMOND);

		private static Function<RelicForge, RelicForge> netherite = r -> r.aspect(ToolRelicCore.MATERIAL, PermetToolMaterial.PERMET_NETHERITE);

		public static void build(String baseName, String finalName, Supplier<RelicForge> base, Supplier<Effect> t2Effect, Supplier<Effect> t4Effect) {
			register("vanadic_"+baseName, make(base, 1, r -> r.cast(diamond)));
			register(base+"_t2", make(base, 2, PermetFlags.PHASE_2, r -> r.cast(diamond).effect(t2Effect.get())));
			register("symbionic_"+baseName, make(base, 3, PermetFlags.SYMBIONIC, r -> r.cast(netherite).effect(t2Effect.get())));
			if (t4Effect == null) { // no new effect, effect will use shell level to determine result
				register(finalName, make(base, 4, PermetFlags.PERFECTED, r -> r.cast(netherite)));
			} else {
				register(finalName, make(base, 4, PermetFlags.PERFECTED, r -> r.cast(netherite).effect(t4Effect.get())));
			}
		}

		public static void build(String baseName, String finalName, Supplier<RelicForge> base, Supplier<Effect> t2Effect) {
			build(baseName, finalName, base, t2Effect, null);
		}

	}	

	public static void init() {}

	private static Item register(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(Permet.MODID, name), item);
	}

	private static Item.Settings flagged(Item.Settings settings, FeatureFlag flag) {
		//TODO: config to bypass phases
		if (!FabricLoader.getInstance().isDevelopmentEnvironment()) {
			settings.requires(flag);
		}
		return settings;
	}
}
