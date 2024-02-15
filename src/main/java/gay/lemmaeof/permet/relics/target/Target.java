package gay.lemmaeof.permet.relics.target;

import java.util.function.Supplier;

import gay.lemmaeof.permet.relics.effect.Effect;

public abstract class Target {
    public static final Supplier<Target> ENTITY = EntityTarget::new;
    public static final Supplier<Target> POSITION = PositionTarget::new;
    public static final Supplier<Target> BLOCK = BlockTarget::new;

    public static final class EntityTarget extends Target {}
    public static final class PositionTarget extends Target {}
    public static final class BlockTarget extends Target {}
}
