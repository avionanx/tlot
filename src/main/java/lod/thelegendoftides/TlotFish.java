package lod.thelegendoftides;

import legend.game.inventory.Equipment;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import legend.lodmod.LodItems;
import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotFish {
  private TlotFish() { }

  private static final Registrar<Fish, RegisterFishEvent> REGISTRAR = new Registrar<>(Tlot.FISH_REGISTRY, Tlot.MOD_ID);

  // Fish
  public static final RegistryDelegate<Fish> CARP = REGISTRAR.register("carp", () -> new RegularFish(new FishIcon(0), 30.0f, 0.2f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> SILVER_CARP = REGISTRAR.register("silver_carp", () -> new RegularFish(new FishIcon(0), 30.0f, 0.3f, () -> new ItemStack(TlotItems.SILVER_CARP.get())));
  public static final RegistryDelegate<Fish> GRAND_CARP = REGISTRAR.register("grand_carp", () -> new RegularFish(new FishIcon(0), 40.0f, 0.4f, () -> new ItemStack(TlotItems.GRAND_CARP.get())));
  public static final RegistryDelegate<Fish> BLACK_BASS = REGISTRAR.register("black_bass", () -> new RegularFish(new FishIcon(3), 30.0f, 1.0f, () -> new ItemStack(TlotItems.BLACK_BASS.get())));
  public static final RegistryDelegate<Fish> GRAND_BASS = REGISTRAR.register("grand_bass", () -> new RegularFish(new FishIcon(3), 30.0f, 1.0f, () -> new ItemStack(TlotItems.GRAND_BASS.get())));
  public static final RegistryDelegate<Fish> RAINBOW_TROUT = REGISTRAR.register("rainbow_trout", () -> new RegularFish(new FishIcon(1), 30.0f, 0.3f, () -> new ItemStack(TlotItems.RAINBOW_TROUT.get())));
  public static final RegistryDelegate<Fish> GRAND_RAINBOW_TROUT = REGISTRAR.register("grand_rainbow_trout", () -> new RegularFish(new FishIcon(1), 40.0f, 0.5f, () -> new ItemStack(TlotItems.GRAND_RAINBOW_TROUT.get())));
  public static final RegistryDelegate<Fish> ROCKHEAD_PUFFERFISH = REGISTRAR.register("rockhead_pufferfish", () -> new RegularFish(new FishIcon(2), 30.0f, 0.2f, () -> new ItemStack(TlotItems.ROCKHEAD_PUFFERFISH.get())));
  public static final RegistryDelegate<Fish> KOI = REGISTRAR.register("koi", () -> new RegularFish(new FishIcon(8), 40.0f, 0.5f, () -> new ItemStack(TlotItems.KOI.get())));
  public static final RegistryDelegate<Fish> PRICKLEBACK = REGISTRAR.register("prickleback", () -> new RegularFish(new FishIcon(7), 40.0f, 0.2f, () -> new ItemStack(TlotItems.PRICKLEBACK.get())));
  public static final RegistryDelegate<Fish> SWORDFISH = REGISTRAR.register("swordfish", () -> new RegularFish(new FishIcon(6), 40.0f, 0.5f, () -> new ItemStack(TlotItems.SWORDFISH.get())));
  public static final RegistryDelegate<Fish> STURGEON = REGISTRAR.register("sturgeon", () -> new RegularFish(new FishIcon(5), 40.0f, 0.5f, () -> new ItemStack(TlotItems.STURGEON.get())));
  public static final RegistryDelegate<Fish> SILVERFISH = REGISTRAR.register("silverfish", () -> new RegularFish(new FishIcon(4), 30.0f, 0.2f, () -> new ItemStack(TlotItems.SILVERFISH.get())));
  public static final RegistryDelegate<Fish> GOLDENFISH = REGISTRAR.register("goldenfish", () -> new RegularFish(new FishIcon(4), 36.0f, 0.25f, () -> new ItemStack(TlotItems.GOLDENFISH.get())));
  public static final RegistryDelegate<Fish> GRAND_GOLDENFISH = REGISTRAR.register("grand_goldenfish", () -> new RegularFish(new FishIcon(4), 42.0f, 0.35f, () -> new ItemStack(TlotItems.GRAND_GOLDENFISH.get())));
  public static final RegistryDelegate<Fish> STARDUSTFISH = REGISTRAR.register("stardustfish", () -> new RegularFish(new FishIcon(4), 80.0f, 0.35f, () -> new ItemStack(TlotItems.STARDUSTFISH.get())).setLegendary(0));
  public static final RegistryDelegate<Fish> LAST_KRAKEN_JR = REGISTRAR.register("last_kraken_jr", () -> new RegularFish(new FishIcon(9), 80.0f, 0.35f, () -> new ItemStack(TlotItems.LAST_KRAKEN_JR.get())).setLegendary(9));
  public static final RegistryDelegate<Fish> AZEEL_GLADIATOR = REGISTRAR.register("azeel_gladiator", () -> new RegularFish(new FishIcon(10), 120.0f, 0.5f, () -> new ItemStack(TlotItems.AZEEL_GLADIATOR.get())).setLegendary(10));

  // Treasures
  public static final RegistryDelegate<Fish> LIGHTSABER = REGISTRAR.register("lightsaber", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.LIGHTSABER).setLegendary(1).setHidden());
  public static final RegistryDelegate<Fish> DRAGONSLAYER_SWORDSPEAR = REGISTRAR.register("dragonslayer_swordspear", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.DRAGONSLAYER_SWORDSPEAR).setLegendary(2).setHidden());
  public static final RegistryDelegate<Fish> DWARVEN_XBOW = REGISTRAR.register("dwarven_xbow", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.BIANCA).setLegendary(3).setHidden());
  public static final RegistryDelegate<Fish> ENERGY_SWORD = REGISTRAR.register("energy_sword", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.ENERGY_SWORD).setLegendary(4).setHidden());
  public static final RegistryDelegate<Fish> SPATULA = REGISTRAR.register("spatula", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.SPATULA).setLegendary(5).setHidden());
  public static final RegistryDelegate<Fish> ENDS_OF_THE_EARTH = REGISTRAR.register("ends_of_the_earth", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.ENDS_OF_THE_EARTH).setLegendary(6).setHidden());

  public static final RegistryDelegate<Fish> OLD_BOOTS = REGISTRAR.register("old_boots", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.OLD_BOOTS).setLegendary(7).setHidden());
  public static final RegistryDelegate<Fish> THE_ONE_RING = REGISTRAR.register("the_one_ring", () -> new TreasureFish(new FishIcon(0), 80.0f, 0.35f, TlotEquipments.THE_ONE_RING).setLegendary(8).setHidden());

  // Misc
  public static final RegistryDelegate<Fish> COMMON_TRASH = REGISTRAR.register("common_trash", () -> new RandomRewardFish(ItemIcon.SACK, 30.0f, 1.5f, () -> new ItemStack(LodItems.HEALING_POTION.get()), () -> new ItemStack(LodItems.CHARM_POTION.get())).setHidden());

  static void register(final RegisterFishEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
