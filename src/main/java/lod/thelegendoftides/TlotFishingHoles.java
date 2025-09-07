package lod.thelegendoftides;

import org.joml.Vector3f;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.ArrayList;
import java.util.List;

public final class TlotFishingHoles {
  private TlotFishingHoles() { }

  private static final Registrar<FishingHole, RegisterFishingHoleEvent> REGISTRAR = new Registrar<>(Tlot.FISHING_HOLE_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<FishingHole> CAVE_RIVER = REGISTRAR.register("cave_river", () -> new FishingHole(-1, -1, 7, new Vector3f(1000, 0, -4800), 0, new Vector3f(10000.0f, -1200.0f, -8000.0f), new Vector3f(-3500.0f, 0.0f, -7500.0f), new FishingHole.FishWeight(TlotFish.CARP, 100), new FishingHole.FishWeight(TlotFish.RAINBOW_TROUT, 50)));
  public static final RegistryDelegate<FishingHole> NEST_OF_DRAGON_RIVER = REGISTRAR.register("nest_of_dragon_river", () -> new FishingHole(133, 6, 13, new Vector3f(-2680, 0, -5200), 0, new Vector3f(-8000.0f, -2000.0f, -15000.0f), new Vector3f(0.0f, 0.0f, -5000.0f), new FishingHole.FishWeight(TlotFish.CARP, 100), new FishingHole.FishWeight(TlotFish.RAINBOW_TROUT, 50)));
  public static final RegistryDelegate<FishingHole> UNDERSEA_CAVERN = REGISTRAR.register("undersea_cavern_river", () -> new FishingHole(-1, -1, 33, new Vector3f(3300, 0, -4400), 0, new Vector3f(9000.0f, -2500.0f, -15000.0f), new Vector3f(-2000.0f, 2000.0f, 0.0f), new FishingHole.FishWeight(TlotFish.CARP, 100), new FishingHole.FishWeight(TlotFish.RAINBOW_TROUT, 50)));
  public static final RegistryDelegate<FishingHole> EVERGREEN_FOREST = REGISTRAR.register("evergreen_forest_sea", () -> new FishingHole(-1, -1, 41, new Vector3f(-8800, 1000, -9500), 0, new Vector3f(-17000.0f, -500.0f, -20000.0f), new Vector3f(5000, 0, 0), new FishingHole.FishWeight(TlotFish.CARP, 100), new FishingHole.FishWeight(TlotFish.RAINBOW_TROUT, 50)));


  static void register(final RegisterFishingHoleEvent event) {
    REGISTRAR.registryEvent(event);
  }

  public static List<FishingHole> getFishingHolesForCut(final int cut) {
    final List<FishingHole> fishingHoles = new ArrayList<>();

    for(final RegistryId id : Tlot.FISHING_HOLE_REGISTRY) {
      final FishingHole fishingHole = Tlot.FISHING_HOLE_REGISTRY.getEntry(id).get();

      if(fishingHole.submapCut == cut) {
        fishingHoles.add(fishingHole);
      }
    }

    return fishingHoles;
  }
}
