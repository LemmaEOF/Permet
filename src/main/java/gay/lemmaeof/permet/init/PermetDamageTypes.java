package gay.lemmaeof.permet.init;

import gay.lemmaeof.permet.Permet;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class PermetDamageTypes {
	public static final RegistryKey<DamageType> HOME_RUN = of("home_run");
	public static final RegistryKey<DamageType> CARVE = of("carve");

	public static void init() {}

	private static RegistryKey<DamageType> of(String name) {
		return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Permet.MODID, name));
	}
}
