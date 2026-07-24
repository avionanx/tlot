package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotBait {
  private TlotBait() { }

  private static final Registrar<Bait, RegisterBaitEvent> REGISTRAR = new Registrar<>(Tlot.BAIT_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<Bait> REGULAR = REGISTRAR.register("regular", () -> new Bait(1));
  public static final RegistryDelegate<Bait> SPARKLING = REGISTRAR.register("sparkling", () -> new Bait(2));
  public static final RegistryDelegate<Bait> INFUSED = REGISTRAR.register("infused", () -> new Bait(2));
  public static final RegistryDelegate<Bait> MAGNETIC = REGISTRAR.register("magnetic", () -> new Bait(3));
  public static final RegistryDelegate<Bait> ANCIENT = REGISTRAR.register("ancient", () -> new Bait(4));
  public static final RegistryDelegate<Bait> PARAMETER = REGISTRAR.register("parameter", () -> new Bait(5));

  public static final RegistryDelegate<Bait> DRAGONIC = REGISTRAR.register("dragonic", () -> new Bait(6));
  public static final RegistryDelegate<Bait> BIG_FISH = REGISTRAR.register("big", () -> new Bait(6));
  public static final RegistryDelegate<Bait> BIGGER_FISH = REGISTRAR.register("bigger", () -> new Bait(6));
  public static final RegistryDelegate<Bait> BIGGEREST_FISH = REGISTRAR.register("biggerest", () -> new Bait(6));

  static void register(final RegisterBaitEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
