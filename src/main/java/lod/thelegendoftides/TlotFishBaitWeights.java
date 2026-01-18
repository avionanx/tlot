package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

public final class TlotFishBaitWeights {
  private TlotFishBaitWeights() { }

  private static final Registrar<FishBaitWeight, RegisterFishBaitWeightEvent> REGISTRAR = new Registrar<>(Tlot.FISH_BAIT_WEIGHT_REGISTRY, Tlot.MOD_ID);
  private static final int DEFAULT_FISH_BAIT_WEIGHT = 10;

  public static final RegistryDelegate<FishBaitWeight> SILVERFISH_SPARKLING = REGISTRAR.register("silverfish_sparkling", () -> new FishBaitWeight(TlotFish.SILVERFISH, TlotBait.SPARKLING, 20));
  public static final RegistryDelegate<FishBaitWeight> GOLDENFISH_SPARKLING = REGISTRAR.register("goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GOLDENFISH, TlotBait.SPARKLING, 20));
  public static final RegistryDelegate<FishBaitWeight> GRAND_GOLDENFISH_SPARKLING = REGISTRAR.register("grand_goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GRAND_GOLDENFISH, TlotBait.SPARKLING, 20));
  public static final RegistryDelegate<FishBaitWeight> STARDUSTFISH_SPARKLING = REGISTRAR.register("stardustfish_infused", () -> new FishBaitWeight(TlotFish.STARDUSTFISH, TlotBait.SPARKLING, 50));

  public static final RegistryDelegate<FishBaitWeight> RAINBOW_TROUT_INFUSED = REGISTRAR.register("rainbow_trout_infused", () -> new FishBaitWeight(TlotFish.RAINBOW_TROUT, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> GRAND_RAINBOW_TROUT_INFUSED = REGISTRAR.register("grand_rainbow_trout_infused", () -> new FishBaitWeight(TlotFish.GRAND_RAINBOW_TROUT, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> KOI_INFUSED = REGISTRAR.register("koi_infused", () -> new FishBaitWeight(TlotFish.KOI, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> LAST_KRAKEN_JR_INFUSED = REGISTRAR.register("last_kraken_jr_infused", () -> new FishBaitWeight(TlotFish.LAST_KRAKEN_JR, TlotBait.INFUSED, 20));

  public static final RegistryDelegate<FishBaitWeight> GLOWSTICK_MAGNETIC = REGISTRAR.register("glowstick_magnetic", () -> new FishBaitWeight(TlotFish.GLOWSTICK, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> NAMELESS_SPEAR_MAGNETIC = REGISTRAR.register("nameless_spear_magnetic", () -> new FishBaitWeight(TlotFish.NAMELESS_SPEAR, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> BIANCA_MAGNETIC = REGISTRAR.register("bianca_magnetic", () -> new FishBaitWeight(TlotFish.BIANCA, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> ENERGY_SWORD_MAGNETIC = REGISTRAR.register("energy_sword_magnetic", () -> new FishBaitWeight(TlotFish.ENERGY_SWORD, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> PUFFERFISH_KNUCKLES_MAGNETIC = REGISTRAR.register("pufferfish_gauntlets_magnetic", () -> new FishBaitWeight(TlotFish.PUFFERFISH_KNUCKLES, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> GUITAR_MAGNETIC = REGISTRAR.register("guitar_magnetic", () -> new FishBaitWeight(TlotFish.GUITAR, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> OVERSIZED_KEY_MAGNETIC = REGISTRAR.register("oversized_key_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> ORTHOS_PRIME_MAGNETIC = REGISTRAR.register("orthos_prime_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));

  public static final RegistryDelegate<FishBaitWeight> MAGIS_BOOTS_MAGNETIC = REGISTRAR.register("magis_boots_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> OLD_BOOTS_MAGNETIC = REGISTRAR.register("old_boots_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> THE_ONE_RING_MAGNETIC = REGISTRAR.register("the_one_ring_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> GIGANTO_SKIRT_MAGNETIC = REGISTRAR.register("giganto_skirt_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> THIGH_HIGHS_MAGNETIC = REGISTRAR.register("thigh_highs_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));
  public static final RegistryDelegate<FishBaitWeight> CAT_EARS_MAGNETIC = REGISTRAR.register("cat_ears_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 40));

  public static final RegistryDelegate<FishBaitWeight> AZEEL_TRACKER_MAGNETIC = REGISTRAR.register("azeel_tracker_magnetic", () -> new FishBaitWeight(TlotFish.AZEEL_TRACKER, TlotBait.MAGNETIC, 80));

  static void register(final RegisterFishBaitWeightEvent event) {
    REGISTRAR.registryEvent(event);
  }

  public static int getBaitWeightForFish(final Fish fish, final Bait bait) {
    for(final RegistryId id : Tlot.FISH_BAIT_WEIGHT_REGISTRY) {
      final FishBaitWeight weight = Tlot.FISH_BAIT_WEIGHT_REGISTRY.getEntry(id).get();

      if(weight.fish.get() == fish && weight.bait.get() == bait) {
        if(!weight.fish.get().canBeCaught()) return 0;
        return weight.weight;
      }
    }

    return DEFAULT_FISH_BAIT_WEIGHT;
  }
}
