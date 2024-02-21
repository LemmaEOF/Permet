package kishar.runes.relics.proto.core;

import kishar.runes.relics.magic.Forge;

import java.util.function.Function;
import java.util.function.Supplier;

public class CoreForge<R extends RelicCore<R>> extends Forge<RelicCore<R>, CoreForge<R>> {

	protected Supplier<? extends RelicCore<R>> coreSource;
	protected Function<RelicCore<R>, RelicCore<R>> coreAugments = c -> c;

	public static <R extends RelicCore<R>> CoreForge<R> of(Supplier<RelicCore<R>> newBase) {
		CoreForge<R> builder = new CoreForge<>();
		builder.coreSource = newBase;
		return builder;
	}

	public CoreForge<R> core(Function<RelicCore<R>, RelicCore<R>> augment) {
		coreAugments = coreAugments.andThen(augment);
		return this;
	}

	public <T> CoreForge<R> aspect(RelicCore.Aspect<T> aspect, T value) {
		coreAugments = coreAugments.andThen(c -> c.aspect(aspect, value));
		return this;
	}

	@Override
	public RelicCore<R> forge() {
		return coreAugments.apply(coreSource.get());
	}

}
