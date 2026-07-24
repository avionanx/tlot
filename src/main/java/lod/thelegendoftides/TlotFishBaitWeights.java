package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;
import org.legendofdragoon.modloader.registries.RegistryId;

public final class TlotFishBaitWeights {
  private TlotFishBaitWeights() { }

  private static final Registrar<FishBaitWeight, RegisterFishBaitWeightEvent> REGISTRAR = new Registrar<>(Tlot.FISH_BAIT_WEIGHT_REGISTRY, Tlot.MOD_ID);
  private static final int DEFAULT_FISH_BAIT_WEIGHT = 10;

  public static final RegistryDelegate<FishBaitWeight> SILVERFISH_SPARKLING = REGISTRAR.register("silverfish_sparkling", () -> new FishBaitWeight(TlotFish.SILVERFISH, TlotBait.SPARKLING, 40));
  public static final RegistryDelegate<FishBaitWeight> GOLDENFISH_SPARKLING = REGISTRAR.register("goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GOLDENFISH, TlotBait.SPARKLING, 40));
  public static final RegistryDelegate<FishBaitWeight> GRAND_GOLDENFISH_SPARKLING = REGISTRAR.register("grand_goldenfish_sparkling", () -> new FishBaitWeight(TlotFish.GRAND_GOLDENFISH, TlotBait.SPARKLING, 40));
  public static final RegistryDelegate<FishBaitWeight> STARDUSTFISH_SPARKLING = REGISTRAR.register("stardustfish_infused", () -> new FishBaitWeight(TlotFish.STARDUSTFISH, TlotBait.SPARKLING, 80));

  public static final RegistryDelegate<FishBaitWeight> THUNDER_JELLY_INFUSED = REGISTRAR.register("thunder_jelly_infused", () -> new FishBaitWeight(TlotFish.THUNDER_JELLY, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> MURKRAY_INFUSED = REGISTRAR.register("murkray_infused", () -> new FishBaitWeight(TlotFish.MURKRAY, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> MAGMA_EATER_INFUSED = REGISTRAR.register("magma_eater_infused", () -> new FishBaitWeight(TlotFish.MAGMA_EATER, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> PETRICHOR_INFUSED = REGISTRAR.register("petrichor_infused", () -> new FishBaitWeight(TlotFish.PETRICHOR, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> LAST_KRAKEN_JR_INFUSED = REGISTRAR.register("last_kraken_jr_infused", () -> new FishBaitWeight(TlotFish.LAST_KRAKEN_JR, TlotBait.INFUSED, 20));
  public static final RegistryDelegate<FishBaitWeight> ARGENTFIN_INFUSED = REGISTRAR.register("argentfin_infused", () -> new FishBaitWeight(TlotFish.ARGENTFIN, TlotBait.INFUSED, 40));
  public static final RegistryDelegate<FishBaitWeight> ABANCEX_INFUSED = REGISTRAR.register("abancex_infused", () -> new FishBaitWeight(TlotFish.ABANCEX, TlotBait.INFUSED, 120));
  public static final RegistryDelegate<FishBaitWeight> AURCIS_INFUSED = REGISTRAR.register("aurcis_infused", () -> new FishBaitWeight(TlotFish.AURCIS, TlotBait.INFUSED, 120));
  public static final RegistryDelegate<FishBaitWeight> ENDINESS_SERPENT_INFUSED = REGISTRAR.register("endiness_serpent_infused", () -> new FishBaitWeight(TlotFish.ENDINESS_SERPENT, TlotBait.INFUSED, 120));
  public static final RegistryDelegate<FishBaitWeight> RYUJIN_INFUSED = REGISTRAR.register("ryujin_infused", () -> new FishBaitWeight(TlotFish.RYUJIN, TlotBait.INFUSED, 120));

  public static final RegistryDelegate<FishBaitWeight> ABANCEX_ANCIENT = REGISTRAR.register("abancex_ancient", () -> new FishBaitWeight(TlotFish.ABANCEX, TlotBait.ANCIENT, 120));
  public static final RegistryDelegate<FishBaitWeight> AURCIS_ANCIENT = REGISTRAR.register("aurcis_ancient", () -> new FishBaitWeight(TlotFish.AURCIS, TlotBait.ANCIENT, 120));
  public static final RegistryDelegate<FishBaitWeight> ENDINESS_SERPENT_ANCIENT = REGISTRAR.register("endiness_serpent_ancient", () -> new FishBaitWeight(TlotFish.ENDINESS_SERPENT, TlotBait.ANCIENT, 120));
  public static final RegistryDelegate<FishBaitWeight> RYUJIN_ANCIENT = REGISTRAR.register("ryujin_ancient", () -> new FishBaitWeight(TlotFish.RYUJIN, TlotBait.ANCIENT, 120));

  public static final RegistryDelegate<FishBaitWeight> SWORDFISH_PARAMETER = REGISTRAR.register("swordfish_parameter", () -> new FishBaitWeight(TlotFish.STURGEON, TlotBait.PARAMETER, 20));
  public static final RegistryDelegate<FishBaitWeight> STURGEON_PARAMETER = REGISTRAR.register("sturgeon_parameter", () -> new FishBaitWeight(TlotFish.STURGEON, TlotBait.PARAMETER, 20));
  public static final RegistryDelegate<FishBaitWeight> PRICKLEBACK_PARAMETER = REGISTRAR.register("prickleback_parameter", () -> new FishBaitWeight(TlotFish.PRICKLEBACK, TlotBait.PARAMETER, 20));
  public static final RegistryDelegate<FishBaitWeight> KOI_PARAMETER = REGISTRAR.register("koi_parameter", () -> new FishBaitWeight(TlotFish.KOI, TlotBait.PARAMETER, 20));
  public static final RegistryDelegate<FishBaitWeight> SHADESEEKER_PARAMETER = REGISTRAR.register("shadeseeker_parameter", () -> new FishBaitWeight(TlotFish.SHADESEEKER, TlotBait.PARAMETER, 20));

  public static final RegistryDelegate<FishBaitWeight> GLOWSTICK_MAGNETIC = REGISTRAR.register("glowstick_magnetic", () -> new FishBaitWeight(TlotFish.GLOWSTICK, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> NAMELESS_SPEAR_MAGNETIC = REGISTRAR.register("nameless_spear_magnetic", () -> new FishBaitWeight(TlotFish.NAMELESS_SPEAR, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> BIANCA_MAGNETIC = REGISTRAR.register("bianca_magnetic", () -> new FishBaitWeight(TlotFish.BIANCA, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> ENERGY_SWORD_MAGNETIC = REGISTRAR.register("energy_sword_magnetic", () -> new FishBaitWeight(TlotFish.ENERGY_SWORD, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> PUFFERFISH_KNUCKLES_MAGNETIC = REGISTRAR.register("pufferfish_gauntlets_magnetic", () -> new FishBaitWeight(TlotFish.PUFFERFISH_KNUCKLES, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> GUITAR_MAGNETIC = REGISTRAR.register("guitar_magnetic", () -> new FishBaitWeight(TlotFish.GUITAR, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> OVERSIZED_KEY_MAGNETIC = REGISTRAR.register("oversized_key_magnetic", () -> new FishBaitWeight(TlotFish.OVERSIZED_KEY, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> ORTHOS_PRIME_MAGNETIC = REGISTRAR.register("orthos_prime_magnetic", () -> new FishBaitWeight(TlotFish.ORTHOS_PRIME, TlotBait.MAGNETIC, 80));

  public static final RegistryDelegate<FishBaitWeight> KERNVITER_MAGNETIC = REGISTRAR.register("kernviter_magnetic", () -> new FishBaitWeight(TlotFish.KERNVITER, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> LIVART_MAGNETIC = REGISTRAR.register("livart_magnetic", () -> new FishBaitWeight(TlotFish.LIVART, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> BRILLANTE_MAGNETIC = REGISTRAR.register("brillante_magnetic", () -> new FishBaitWeight(TlotFish.BRILLANTE, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> ERICCIL_MAGNETIC = REGISTRAR.register("ericcil_magnetic", () -> new FishBaitWeight(TlotFish.ERICCIL, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> HIS_HANDS_MAGNETIC = REGISTRAR.register("his_hands_magnetic", () -> new FishBaitWeight(TlotFish.HIS_HANDS, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> PAWS_MAGNETIC = REGISTRAR.register("paws_magnetic", () -> new FishBaitWeight(TlotFish.PAWS, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> POOL_NOODLE_MAGNETIC = REGISTRAR.register("pool_noodle_magnetic", () -> new FishBaitWeight(TlotFish.POOL_NOODLE, TlotBait.MAGNETIC, 80));

  public static final RegistryDelegate<FishBaitWeight> MAGIS_BOOTS_MAGNETIC = REGISTRAR.register("magis_boots_magnetic", () -> new FishBaitWeight(TlotFish.MAGIS_BOOTS, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> OLD_BOOTS_MAGNETIC = REGISTRAR.register("old_boots_magnetic", () -> new FishBaitWeight(TlotFish.OLD_BOOTS, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> THE_ONE_RING_MAGNETIC = REGISTRAR.register("the_one_ring_magnetic", () -> new FishBaitWeight(TlotFish.THE_ONE_RING, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> GIGANTO_SKIRT_MAGNETIC = REGISTRAR.register("giganto_skirt_magnetic", () -> new FishBaitWeight(TlotFish.GIGANTO_SKIRT, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> THIGH_HIGHS_MAGNETIC = REGISTRAR.register("thigh_highs_magnetic", () -> new FishBaitWeight(TlotFish.THIGH_HIGHS, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> CAT_EARS_MAGNETIC = REGISTRAR.register("cat_ears_magnetic", () -> new FishBaitWeight(TlotFish.CAT_EARS, TlotBait.MAGNETIC, 80));

  public static final RegistryDelegate<FishBaitWeight> AZEEL_TRACKER_MAGNETIC = REGISTRAR.register("azeel_tracker_magnetic", () -> new FishBaitWeight(TlotFish.AZEEL_TRACKER, TlotBait.MAGNETIC, 200));

  public static final RegistryDelegate<FishBaitWeight> CRITICAL_RING_MAGNETIC = REGISTRAR.register("critical_ring_magnetic", () -> new FishBaitWeight(TlotFish.CRITICAL_RING, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> CHEATER_RING_MAGNETIC = REGISTRAR.register("cheater_ring_magnetic", () -> new FishBaitWeight(TlotFish.CHEATER_RING, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> BLUE_AMULET_MAGNETIC = REGISTRAR.register("blue_amulet_magnetic", () -> new FishBaitWeight(TlotFish.BLUE_AMULET, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> AZURE_AMULET_MAGNETIC = REGISTRAR.register("azure_amulet_magnetic", () -> new FishBaitWeight(TlotFish.AZURE_AMULET, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> BERSERK_PLUME_MAGNETIC = REGISTRAR.register("berserk_plume_magnetic", () -> new FishBaitWeight(TlotFish.BERSERK_PLUME, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> BOUNDLESS_PLATE_MAGNETIC = REGISTRAR.register("boundless_plate_magnetic", () -> new FishBaitWeight(TlotFish.BOUNDLESS_PLATE, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> ASSAULT_GEAR_MAGNETIC = REGISTRAR.register("assault_gear_magnetic", () -> new FishBaitWeight(TlotFish.ASSAULT_GEAR, TlotBait.MAGNETIC, 80));
  public static final RegistryDelegate<FishBaitWeight> STORMCROWN_MAGNETIC = REGISTRAR.register("stormcrown_magnetic", () -> new FishBaitWeight(TlotFish.STORMCROWN, TlotBait.MAGNETIC, 80));

  static void register(final RegisterFishBaitWeightEvent event) {
    REGISTRAR.registryEvent(event);
  }

  public static int getBaitWeightForFish(final Fish fish, final Bait bait) {
    if(!fish.canBeCaught()) {
      return 0;
    }

    for(final RegistryId id : Tlot.FISH_BAIT_WEIGHT_REGISTRY) {
      final FishBaitWeight weight = Tlot.FISH_BAIT_WEIGHT_REGISTRY.getEntry(id).get();

      if(weight.fish.get() == fish && weight.bait.get() == bait) {
        return weight.weight;
      }
    }

    return DEFAULT_FISH_BAIT_WEIGHT;
  }
}
