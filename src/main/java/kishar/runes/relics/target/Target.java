package kishar.runes.relics.target;

public abstract class Target {
    public static final EntityTarget ENTITY = new EntityTarget();
    public static final PositionTarget POS = new PositionTarget();
    public static final BlockTarget BLOCK = new BlockTarget();

    public static final class EntityTarget extends Target { private EntityTarget() {} }
    public static final class PositionTarget extends Target { private PositionTarget() {} }
    public static final class BlockTarget extends Target { private BlockTarget() {} }
}
