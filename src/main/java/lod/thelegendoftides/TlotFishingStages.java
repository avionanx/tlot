package lod.thelegendoftides;

import org.joml.Vector3f;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import static lod.thelegendoftides.Tlot.MOD_ID;

public class TlotFishingStages {
  private TlotFishingStages() {}

  private static final Registrar<FishingStage, RegisterFishingStageEvent> REGISTRAR = new Registrar<>(Tlot.FISHING_STAGE_REGISTRY, MOD_ID);

  public static final RegistryDelegate<FishingStage> LIMESTONE_CAVE = REGISTRAR.register("limestone_cave", () -> new FishingStage(7, new Vector3f(1000, 0, -4800), 0, new Vector3f(10000.0f, -1200.0f, -8000.0f), new Vector3f(-3500.0f, 0.0f, -7500.0f)));
  public static final RegistryDelegate<FishingStage> NEST_OF_DRAGON = REGISTRAR.register("nest_of_dragon", () -> new FishingStage(13, new Vector3f(-2680, 0, -5200), 0, new Vector3f(-8000.0f, -2000.0f, -15000.0f), new Vector3f(0.0f, 0.0f, -5000.0f)));
  public static final RegistryDelegate<FishingStage> UNDERSEA_CAVERN = REGISTRAR.register("undersea_cavern", () -> new FishingStage(33, new Vector3f(3300, 0, -4400), 0, new Vector3f(9000.0f, -2500.0f, -15000.0f), new Vector3f(-2000.0f, 2000.0f, 0.0f)));
  public static final RegistryDelegate<FishingStage> EVERGREEN_FOREST = REGISTRAR.register("evergreen_forest", () -> new FishingStage(41, new Vector3f(-8800, 1000, -9500), 0, new Vector3f(-17000.0f, -500.0f, -20000.0f), new Vector3f(5000, 0, 0)));

  static void register(final RegisterFishingStageEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
