package lod.thelegendoftides;

import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotFish {
  private TlotFish() { }

  private static final Registrar<Fish, RegisterFishEvent> REGISTRAR = new Registrar<>(Tlot.FISH_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<Fish> CARP = REGISTRAR.register("carp", () -> new Fish(new FishIcon(0)));
  public static final RegistryDelegate<Fish> RAINBOW_TROUT = REGISTRAR.register("rainbow_trout", () -> new Fish(new FishIcon(1)));

  static void register(final RegisterFishEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
