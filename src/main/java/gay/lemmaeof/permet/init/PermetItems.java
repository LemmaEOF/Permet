package gay.lemmaeof.permet.init;

import gay.lemmaeof.permet.Permet;
import gay.lemmaeof.permet.util.PermetToolMaterial;
import gay.lemmaeof.permet.weapon.cardbox.OlReliableItem;
import gay.lemmaeof.permet.weapon.star.MagitekBowItem;
import gay.lemmaeof.permet.weapon.zydra.BigKnifeItem;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PermetItems {
	public static final Item PERMET = register("permet", new Item(new Item.Settings()));
	public static final Item MAGITEK_BOW = register("magitek_bow", new MagitekBowItem(new Item.Settings()));
	public static final Item OL_RELIABLE = register("ol_reliable", new OlReliableItem(PermetToolMaterial.INSTANCE, 3, -4, new Item.Settings()));
	public static final Item BIG_KNIFE = register("big_knife", new BigKnifeItem(PermetToolMaterial.INSTANCE, 3, -2.4f, new Item.Settings()));

	public static final ItemGroup PERMET_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(Permet.MODID, "permet"),
			FabricItemGroup.builder()
					.displayName(Text.translatable("itemGroup.permet.permet"))
					.icon(() -> new ItemStack(PERMET))
					.entries(((displayContext, entries) -> {
						entries.add(PERMET);
						entries.add(MAGITEK_BOW);
						entries.add(OL_RELIABLE);
						entries.add(BIG_KNIFE);
					}))
					.build()
	);

	public static void init() {}

	private static Item register(String name, Item item) {
		return Registry.register(Registries.ITEM, new Identifier(Permet.MODID, name), item);
	}
}
