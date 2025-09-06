package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishBaitWeightEvent extends RegistryEvent.Register<FishBaitWeight> {
  public RegisterFishBaitWeightEvent(final MutableRegistry<FishBaitWeight> registry) {
    super(registry);
  }
}
