package lod.thelegendoftides;

import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

// Putting this in here so it does not make main 3000 lines
public class FishMeta {
    private HashMap<String, Fish> fishData;
    private HashMap<String, Bait> baitData;
    private HashMap<Integer, FishLocationData> fishLocationData;
    private HashMap<BaitFishStruct, Integer>  baitFishData;
    public FishMeta() {
        this.fishData = new HashMap<>();
        this.baitData = new HashMap<>();
        this.fishLocationData = new HashMap<>();
        this.baitFishData = new HashMap<>();
        this.loadData();
    }

    private void loadData() {
        this.fishData.put("carp", new Fish("Carp"));
        this.fishData.put("rainbow_trout", new Fish("Rainbow Trout"));
        this.baitData.put("bait", new Bait("Bait"));
        this.baitData.put("premium_bait", new Bait("Premium Bait"));
        this.baitData.put("shrimp", new Bait("Shrimp"));
        this.fishLocationData.put(133, new FishLocationData(6, new ArrayList<>(List.of(Pair.of("carp", 100), Pair.of("rainbow_trout", 50)))));
        this.baitFishData.put(new BaitFishStruct("bait", "carp"), 1);
    }

    public Fish getFish(final String key) { return this.fishData.get(key); }

    public FishLocationData getCutFish(final int cut) {
        return this.fishLocationData.get(cut);
    }

    public Integer getBaitWeightData(final String bait, final String fish) {
        return this.baitFishData.getOrDefault(new BaitFishStruct(bait, fish), 0);
    }

    public HashMap<String, Bait> getBaitData() { return this.baitData; }

    public Fish getRandomFishForBait(final FishLocationData locationData, final String bait) {
        // Total weight
        int weightSum = 0;
        for(var fishData : locationData.fishWeightRegistries()) {
            weightSum += fishData.second() * this.getBaitWeightData(bait, fishData.first());
        }
        // If used wrong bait, return null to inform later (nothing is biting)
        if(weightSum == 0) { return null; }

        Random rng = new Random();
        int chosen = rng.nextInt(weightSum);

        int cumulative = 0;

        int index = 0;
        for(var fishData : locationData.fishWeightRegistries()) {
            cumulative += (int) (fishData.second() * this.getBaitWeightData(bait, fishData.first()));
            if (chosen < cumulative) {
                return this.fishData.get(locationData.fishWeightRegistries().get(index).first());
            }
            index++;
        }
        throw new RuntimeException("Could not find fish");
    }
}
