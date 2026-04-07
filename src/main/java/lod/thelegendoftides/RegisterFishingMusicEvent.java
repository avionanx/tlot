package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishingMusicEvent extends RegistryEvent.Register<FishingMusic> {
  public RegisterFishingMusicEvent(final MutableRegistry<FishingMusic> registry) {
    super(registry);
  }
}
