package lod.thelegendoftides;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;

import java.util.List;
import java.util.Random;

// Putting this in here so it does not make main 3000 lines
public class FishMeta {
  private final Int2ObjectMap<FishLocationData> fishLocationData = new Int2ObjectOpenHashMap<>();

  public FishMeta() {
    this.loadData();
  }

  private void loadData() {
    this.fishLocationData.put(133, new FishLocationData(6, List.of(ObjectIntImmutablePair.of(TlotFish.CARP.get(), 100), ObjectIntImmutablePair.of(TlotFish.RAINBOW_TROUT.get(), 50))));
  }

  public FishLocationData getCutFish(final int cut) {
    return this.fishLocationData.get(cut);
  }

  public Fish getRandomFishForBait(final FishLocationData locationData, final Bait bait) {
    // Total weight
    int weightSum = 0;
    for(final var fishData : locationData.fishWeightRegistries()) {
      weightSum += fishData.secondInt() * TlotFishBaitWeight.getBaitWeightForFish(fishData.first(), bait);
    }
    // If used wrong bait, return null to inform later (nothing is biting)
    if(weightSum == 0) {
      return null;
    }

    final Random rng = new Random();
    final int chosen = rng.nextInt(weightSum);

    int cumulative = 0;

    int index = 0;
    for(final var fishData : locationData.fishWeightRegistries()) {
      cumulative += fishData.secondInt() * TlotFishBaitWeight.getBaitWeightForFish(fishData.first(), bait);
      if(chosen < cumulative) {
        return locationData.fishWeightRegistries().get(index).first();
      }
      index++;
    }
    throw new RuntimeException("Could not find fish");
  }
}
