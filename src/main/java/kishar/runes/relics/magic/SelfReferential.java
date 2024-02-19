package kishar.runes.relics.magic;

public interface SelfReferential<T extends SelfReferential<T>> {
    @SuppressWarnings("unchecked")
    public default T self(){ return (T) this; }
}
