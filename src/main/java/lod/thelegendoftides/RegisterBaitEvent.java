package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterBaitEvent extends RegistryEvent.Register<Bait> {
  public RegisterBaitEvent(final MutableRegistry<Bait> registry) {
    super(registry);
  }
}
