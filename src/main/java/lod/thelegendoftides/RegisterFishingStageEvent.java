package lod.thelegendoftides;

import org.legendofdragoon.modloader.events.registries.RegistryEvent;
import org.legendofdragoon.modloader.registries.MutableRegistry;

public class RegisterFishingStageEvent extends RegistryEvent.Register<FishingStage> {
  public RegisterFishingStageEvent(final MutableRegistry<FishingStage> registry) { super(registry); }
}
