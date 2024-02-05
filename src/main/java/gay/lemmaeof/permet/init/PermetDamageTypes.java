package gay.lemmaeof.permet.init;

import gay.lemmaeof.permet.Permet;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class PermetDamageTypes {
	public static final RegistryKey<DamageType> HOME_RUN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(Permet.MODID, "home_run"));

	public static void init() {}
}
