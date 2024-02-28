package gay.lemmaeof.permet.weapon.lemma;

import com.unascribed.lib39.recoil.api.DirectClickItem;
import kishar.runes.relics.proto.Relic;
import kishar.runes.relics.proto.core.RelicCore;
import kishar.runes.relics.trigger.TriggerContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class AxiomataRelic extends Item implements Relic, DirectClickItem {
	public AxiomataRelic(Settings settings) {
		super(settings);
	}

	@Override
	public <R, T extends TriggerContext<R>> R trigger(T ctx) {
		return null;
	}

	@Override
	public <T extends RelicCore<T>> RelicCore<T> core() {
		return null;
	}

	@Override
	public ActionResult onDirectAttack(PlayerEntity player, Hand hand) {
		return null;
	}

	@Override
	public ActionResult onDirectUse(PlayerEntity player, Hand hand) {
		return null;
	}
}
