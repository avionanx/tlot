package lod.thelegendoftides;

import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayList;

public record FishLocationData(int collisionPrimitive, ArrayList<Pair<String, Integer>> fishWeightRegistries) {
}
