package lod.thelegendoftides;

import org.joml.Vector3f;

public record FishingStageData(int stageId, Vector3f playerPosition, float playerRotation, Vector3f cameraViewpoint, Vector3f cameraRefpoint) {
}
