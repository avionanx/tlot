package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishingHoleEvent extends RegistryEvent.Register<FishingHole> {
  public RegisterFishingHoleEvent(final MutableRegistry<FishingHole> registry) {
    super(registry);
  }
}
