package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishEvent extends RegistryEvent.Register<Fish> {
  public RegisterFishEvent(final MutableRegistry<Fish> registry) {
    super(registry);
  }
}
