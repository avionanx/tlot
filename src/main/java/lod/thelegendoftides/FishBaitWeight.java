package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.RegistryEntry;

import java.util.function.Supplier;

public class FishBaitWeight extends RegistryEntry {
  public final Supplier<Fish> fish;
  public final Supplier<Bait> bait;
  public final int weight;

  public FishBaitWeight(final Supplier<Fish> fish, final Supplier<Bait> bait, final int weight) {
    this.fish = fish;
    this.bait = bait;
    this.weight = weight;
  }
}
