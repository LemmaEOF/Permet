package gay.lemmaeof.permet.client;

import gay.lemmaeof.permet.init.PermetItems;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class PermetClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModelPredicateProviderRegistry.register(PermetItems.MAGITEK_BOW, new Identifier("pull"), (stack, world, entity, seed) -> {
			if (entity == null) {
				return 0.0F;
			} else {
				return entity.getActiveItem() != stack ? 0.0F : (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0F;
			}
		});
		ModelPredicateProviderRegistry.register(PermetItems.MAGITEK_BOW, new Identifier("pulling"), (stack, world, entity, seed) ->
				entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
		);
	}
}
