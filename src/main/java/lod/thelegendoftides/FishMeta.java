package lod.thelegendoftides;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;

import java.util.List;
import java.util.Random;

// Putting this in here so it does not make main 3000 lines
public class FishMeta {
  private final Int2ObjectMap<FishLocationData> fishLocationData = new Int2ObjectOpenHashMap<>();
  private final Object2IntMap<BaitFishStruct> baitFishData = new Object2IntOpenHashMap<>();

  public FishMeta() {
    this.loadData();
  }

  private void loadData() {
    this.fishLocationData.put(133, new FishLocationData(6, List.of(ObjectIntImmutablePair.of(TlotFish.CARP.get(), 100), ObjectIntImmutablePair.of(TlotFish.RAINBOW_TROUT.get(), 50))));
    this.baitFishData.put(new BaitFishStruct(TlotBait.REGULAR.get(), TlotFish.CARP.get()), 1);
  }

  public FishLocationData getCutFish(final int cut) {
    return this.fishLocationData.get(cut);
  }

  public Integer getBaitWeightData(final Bait bait, final Fish fish) {
    return this.baitFishData.getOrDefault(new BaitFishStruct(bait, fish), 0);
  }

  public Fish getRandomFishForBait(final FishLocationData locationData, final Bait bait) {
    // Total weight
    int weightSum = 0;
    for(final var fishData : locationData.fishWeightRegistries()) {
      weightSum += fishData.second() * this.getBaitWeightData(bait, fishData.first());
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
      cumulative += fishData.second() * this.getBaitWeightData(bait, fishData.first());
      if(chosen < cumulative) {
        return locationData.fishWeightRegistries().get(index).first();
      }
      index++;
    }
    throw new RuntimeException("Could not find fish");
  }
}
