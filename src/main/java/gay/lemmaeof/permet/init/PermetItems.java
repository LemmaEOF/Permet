package gay.lemmaeof.permet.init;

import java.util.List;

import gay.lemmaeof.permet.Permet;
import gay.lemmaeof.permet.relics.Example;
import gay.lemmaeof.permet.util.PermetToolMaterial;
import gay.lemmaeof.permet.weapon.cardbox.OlReliableItem;
import gay.lemmaeof.permet.weapon.star.MagitekBowItem;
import gay.lemmaeof.permet.weapon.zydra.BigKnifeItem;
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
	public static final Item BIG_KNIFE = register("big_knife", new BigKnifeItem(PermetToolMaterial.PERMET, 3, -2.4f, flagged(new Item.Settings(), PermetFlags.PERFECTED)));

	public static final List<Item> SWAGGER_WARNING = Example.test(PermetItems::register);

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
						entries.add(BIG_KNIFE);

						for (var EPIC_WIN_ITEM : SWAGGER_WARNING) {
							entries.add(EPIC_WIN_ITEM);
						}
					}))
					.build()
	);

	

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
