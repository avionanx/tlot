package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

public final class TlotFishBaitWeights {
  private TlotFishBaitWeights() { }

  private static final Registrar<FishBaitWeight, RegisterFishBaitWeightEvent> REGISTRAR = new Registrar<>(Tlot.FISH_BAIT_WEIGHT_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<FishBaitWeight> SILVERFISH_SPARKLING = REGISTRAR.register("silverfish_sparkling", () -> new FishBaitWeight(TlotFish.SILVERFISH, TlotBait.SPARKLING, 20));
  public static final RegistryDelegate<FishBaitWeight> GOLDENFISH_SPARKLING = REGISTRAR.register("goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GOLDENFISH, TlotBait.SPARKLING, 20));
  public static final RegistryDelegate<FishBaitWeight> GRAND_GOLDENFISH_SPARKLING = REGISTRAR.register("grand_goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GRAND_GOLDENFISH, TlotBait.SPARKLING, 20));


  static void register(final RegisterFishBaitWeightEvent event) {
    REGISTRAR.registryEvent(event);
  }

  public static int getBaitWeightForFish(final Fish fish, final Bait bait) {
    for(final RegistryId id : Tlot.FISH_BAIT_WEIGHT_REGISTRY) {
      final FishBaitWeight weight = Tlot.FISH_BAIT_WEIGHT_REGISTRY.getEntry(id).get();

      if(!weight.fish.get().canBeCaught()) return 0;

      if(weight.fish.get() == fish && weight.bait.get() == bait) {
        return weight.weight;
      }
    }

    return 10;
  }
}
