package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotBait {
  private TlotBait() { }

  private static final Registrar<Bait, RegisterBaitEvent> REGISTRAR = new Registrar<>(Tlot.BAIT_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<Bait> REGULAR = REGISTRAR.register("regular", () -> new Bait(1));
  public static final RegistryDelegate<Bait> PREMIUM = REGISTRAR.register("premium", () -> new Bait(2));
  public static final RegistryDelegate<Bait> SHRIMP = REGISTRAR.register("shrimp", () -> new Bait(3));

  static void register(final RegisterBaitEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
