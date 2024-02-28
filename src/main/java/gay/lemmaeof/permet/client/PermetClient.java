package gay.lemmaeof.permet.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unascribed.lib39.recoil.api.RecoilEvents;
import gay.lemmaeof.permet.Permet;
import gay.lemmaeof.permet.init.PermetItems;
import gay.lemmaeof.permet.weapon.lemma.AxiomataRelic;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.util.Identifier;

public class PermetClient implements ClientModInitializer {
	private static final MinecraftClient mc = MinecraftClient.getInstance();
	private static final Identifier AXIOMATA_CROSSHAIRS = new Identifier(Permet.MODID, "textures/gui/axiomata_crosshairs.png");

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
		RecoilEvents.RENDER_CROSSHAIRS.register(matrices -> {
			if (mc.player != null && mc.player.getMainHandStack().getItem() instanceof AxiomataRelic) {
				RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
				RenderSystem.setShaderColor(1, 1, 1, 1);
				RenderSystem.setShaderTexture(0, AXIOMATA_CROSSHAIRS);
				int windowWidth = mc.getWindow().getScaledWidth();
				int windowHeight = mc.getWindow().getScaledHeight();
				int w = 17;
				int h = 17;
				DrawContext ctx = new DrawContext(mc, mc.getBufferBuilders().getEntityVertexConsumers());
				ctx.drawTexture(AXIOMATA_CROSSHAIRS, (windowWidth-w)/2, (windowHeight-h)/2, 0, 0, w, h, w, h);
				return true;
			}
			return false;
		});
	}
}
