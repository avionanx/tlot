package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

public final class TlotFishBaitWeights {
  private TlotFishBaitWeights() { }

  private static final Registrar<FishBaitWeight, RegisterFishBaitWeightEvent> REGISTRAR = new Registrar<>(Tlot.FISH_BAIT_WEIGHT_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<FishBaitWeight> CARP_REGULAR = REGISTRAR.register("carp_regular", () -> new FishBaitWeight(TlotFish.CARP, TlotBait.REGULAR, 1));
  public static final RegistryDelegate<FishBaitWeight> CARP_PREMIUM = REGISTRAR.register("carp_premium", () -> new FishBaitWeight(TlotFish.CARP, TlotBait.PREMIUM, 1));

  static void register(final RegisterFishBaitWeightEvent event) {
    REGISTRAR.registryEvent(event);
  }

  public static int getBaitWeightForFish(final Fish fish, final Bait bait) {
    for(final RegistryId id : Tlot.FISH_BAIT_WEIGHT_REGISTRY) {
      final FishBaitWeight weight = Tlot.FISH_BAIT_WEIGHT_REGISTRY.getEntry(id).get();

      if(weight.fish.get() == fish && weight.bait.get() == bait) {
        return weight.weight;
      }
    }

    return 0;
  }
}
