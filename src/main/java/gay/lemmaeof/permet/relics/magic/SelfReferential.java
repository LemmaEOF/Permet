package gay.lemmaeof.permet.relics.magic;

public interface SelfReferential<T extends SelfReferential<T>> {
    @SuppressWarnings("unchecked")
    public default T self(){ return (T) this; }
}
