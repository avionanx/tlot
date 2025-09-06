package lod.thelegendoftides;

import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Putting this in here so it does not make main 3000 lines
public class FishMeta {
    private final HashMap<String, Fish> fishData;
    private final HashMap<Integer, FishLocationData> fishLocationData;
    private final HashMap<BaitFishStruct, Integer>  baitFishData;
    public FishMeta() {
        this.fishData = new HashMap<>();
        this.fishLocationData = new HashMap<>();
        this.baitFishData = new HashMap<>();
        this.loadData();
    }

    private void loadData() {
        this.fishData.put("carp", new Fish("Carp"));
        this.fishData.put("rainbow_trout", new Fish("Rainbow Trout"));
        this.fishLocationData.put(133, new FishLocationData(6, new ArrayList<>(List.of(Pair.of("carp", 100), Pair.of("rainbow_trout", 50)))));
        this.baitFishData.put(new BaitFishStruct(TlotBait.REGULAR.get(), "carp"), 1);
    }

    public Fish getFish(final String key) { return this.fishData.get(key); }

    public FishLocationData getCutFish(final int cut) {
        return this.fishLocationData.get(cut);
    }

    public Integer getBaitWeightData(final Bait bait, final String fish) {
        return this.baitFishData.getOrDefault(new BaitFishStruct(bait, fish), 0);
    }

    public Fish getRandomFishForBait(final FishLocationData locationData, final Bait bait) {
        // Total weight
        int weightSum = 0;
        for(final var fishData : locationData.fishWeightRegistries()) {
            weightSum += fishData.second() * this.getBaitWeightData(bait, fishData.first());
        }
        // If used wrong bait, return null to inform later (nothing is biting)
        if(weightSum == 0) { return null; }

        final Random rng = new Random();
        final int chosen = rng.nextInt(weightSum);

        int cumulative = 0;

        int index = 0;
        for(final var fishData : locationData.fishWeightRegistries()) {
            cumulative += fishData.second() * this.getBaitWeightData(bait, fishData.first());
            if (chosen < cumulative) {
                return this.fishData.get(locationData.fishWeightRegistries().get(index).first());
            }
            index++;
        }
        throw new RuntimeException("Could not find fish");
    }
}
