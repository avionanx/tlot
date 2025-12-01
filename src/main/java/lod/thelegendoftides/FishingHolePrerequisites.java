package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.RegistryEntry;

import java.util.function.Supplier;

public class FishingHolePrerequisites extends RegistryEntry {
  public final Supplier<Boolean>[] requirements;

  public FishingHolePrerequisites(final Supplier<Boolean>[] requirements) {
    this.requirements = requirements;
  }
}
