package lod.thelegendoftides;

import org.joml.Vector3f;
import org.legendofdragoon.modloader.registries.RegistryEntry;

public class FishingStage extends RegistryEntry {
  public final int stageId;
  public final Vector3f playerPosition;
  public final float playerRotation;
  public final Vector3f cameraViewpoint;
  public final Vector3f cameraRefpoint;

  public FishingStage(final int stageId, final Vector3f playerPosition, final float playerRotation, final Vector3f cameraViewpoint, final Vector3f cameraRefpoint) {
    this.stageId = stageId;
    this.playerPosition = playerPosition;
    this.playerRotation = playerRotation;
    this.cameraViewpoint = cameraViewpoint;
    this.cameraRefpoint = cameraRefpoint;
  }
}
