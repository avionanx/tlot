package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.MutableRegistry;

import static lod.thelegendoftides.TlotFishingHolePrerequisites.REGISTRAR;

public class FishingHolePrerequisitesRegistry extends MutableRegistry<FishingHolePrerequisites> {
  public FishingHolePrerequisitesRegistry() {
    super(Tlot.id("fishing_hole_prerequisities"));
  }


}
