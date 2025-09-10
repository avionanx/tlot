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

  public static final RegistryDelegate<FishingHole> NEST_OF_DRAGON_RIVER = REGISTRAR.register("nest_of_dragon_river", () -> new FishingHole(133, 6, new Vector3f(-30.0f, 50.0f, -1200.0f), TlotFishingStages.NEST_OF_DRAGON, new FishingHole.FishWeight(TlotFish.CARP, 100), new FishingHole.FishWeight(TlotFish.RAINBOW_TROUT, 50)));

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
