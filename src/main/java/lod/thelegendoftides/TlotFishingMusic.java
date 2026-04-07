package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import static lod.thelegendoftides.Tlot.MOD_ID;

public class TlotFishingMusic {
  public TlotFishingMusic() {}

  private static final Registrar<FishingMusic, RegisterFishingMusicEvent> REGISTRAR = new Registrar<>(Tlot.FISHING_MUSIC_REGISTRY, MOD_ID);

  public static final RegistryDelegate<FishingMusic> WMAP_1 = REGISTRAR.register("wmap_1", () -> new FishingMusicWmap(0));

  static void register(final RegisterFishingMusicEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
