package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishingHolePrerequisitiesEvent extends RegistryEvent.Register<FishingHolePrerequisites> {
  public RegisterFishingHolePrerequisitiesEvent(final MutableRegistry<FishingHolePrerequisites> event) { super(event); }
}
