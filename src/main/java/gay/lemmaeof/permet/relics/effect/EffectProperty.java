package gay.lemmaeof.permet.relics.effect;

public class EffectProperty<T>  {
    private EffectProperty() {}

    public static EffectProperty<Integer> DURATION = new EffectProperty<>();
    public static EffectProperty<Float> INTENSITY = new EffectProperty<>();
}
