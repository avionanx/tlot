package lod.thelegendoftides;

import it.unimi.dsi.fastutil.objects.ObjectIntPair;

import java.util.List;

public record FishLocationData(int collisionPrimitive, List<ObjectIntPair<Fish>> fishWeightRegistries) {
}
