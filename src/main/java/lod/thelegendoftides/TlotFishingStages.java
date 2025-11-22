package lod.thelegendoftides;

import legend.core.MathHelper;
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
  public static final RegistryDelegate<FishingStage> QUEEN_FURY = REGISTRAR.register("queen_fury", () -> new FishingStage(88, new Vector3f(-2000, 1600, -12500), 0, new Vector3f(6000, -750, -11500), new Vector3f(-2000, 250, -14500)));
  public static final RegistryDelegate<FishingStage> VOLCANO_VILLUDE = REGISTRAR.register("volcano_villude", () -> new FishingStage(91, new Vector3f(-8000, 0, 1250), MathHelper.HALF_PI, new Vector3f(-6500, -5000, -2000), new Vector3f(-11500, 3000, 3000)));
  public static final RegistryDelegate<FishingStage> MESA_GREEN = REGISTRAR.register("mesa_green", () -> new FishingStage(25, new Vector3f(11000, 0, 1500), MathHelper.PI * 1.4f, new Vector3f(23000, -1200, 5000), new Vector3f(7000, 1200, -2000)));
  public static final RegistryDelegate<FishingStage> MESA_DAY = REGISTRAR.register("mesa_day", () -> new FishingStage(94, new Vector3f(11000, 0, 1500), MathHelper.PI * 1.4f, new Vector3f(23000, -1200, 5000), new Vector3f(7000, 1200, -2000)));
  public static final RegistryDelegate<FishingStage> MESA_NIGHT = REGISTRAR.register("mesa_night", () -> new FishingStage(95, new Vector3f(11000, 0, 1500), 0, new Vector3f(23000, -1200, 5000), new Vector3f(7000, 1200, -2000)));

  static void register(final RegisterFishingStageEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
