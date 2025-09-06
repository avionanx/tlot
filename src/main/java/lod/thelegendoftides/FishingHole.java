package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.RegistryEntry;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public final class FishingHole extends RegistryEntry {
  public final int submapCut;
  public final int collisionPrimitive;
  public final List<FishWeight> fish;

  public FishingHole(final int submapCut, final int collisionPrimitive, final FishWeight... fish) {
    this.submapCut = submapCut;
    this.collisionPrimitive = collisionPrimitive;
    this.fish = List.of(fish);
  }

  public Fish getFishForBait(final Bait bait) {
    // Total weight
    int weightSum = 0;
    for(final FishWeight fish : this.fish) {
      weightSum += fish.weight * TlotFishBaitWeights.getBaitWeightForFish(fish.fish.get(), bait);
    }

    // If used wrong bait, return null to inform later (nothing is biting)
    if(weightSum == 0) {
      return null;
    }

    final Random rng = new Random();
    final int chosen = rng.nextInt(weightSum);

    int cumulative = 0;

    int index = 0;
    for(final FishWeight fishData : this.fish) {
      cumulative += fishData.weight * TlotFishBaitWeights.getBaitWeightForFish(fishData.fish.get(), bait);

      if(chosen < cumulative) {
        return this.fish.get(index).fish.get();
      }

      index++;
    }

    throw new RuntimeException("Could not find fish");
  }

  public static class FishWeight {
    public final Supplier<Fish> fish;
    public final int weight;

    public FishWeight(final Supplier<Fish> fish, final int weight) {
      this.fish = fish;
      this.weight = weight;
    }
  }
}
