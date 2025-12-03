package lod.thelegendoftides;

import legend.core.GameEngine;
import legend.game.inventory.Item;
import legend.game.inventory.ItemRegistryEvent;
import lod.thelegendoftides.items.AzeelGladiatorItem;
import lod.thelegendoftides.items.BaitBoxItem;
import lod.thelegendoftides.items.BlackBassItem;
import lod.thelegendoftides.items.CarpItem;
import lod.thelegendoftides.items.GoldenfishItem;
import lod.thelegendoftides.items.GrandBassItem;
import lod.thelegendoftides.items.GrandCarpItem;
import lod.thelegendoftides.items.GrandGoldenfishItem;
import lod.thelegendoftides.items.GrandRainbowTroutItem;
import lod.thelegendoftides.items.KoiItem;
import lod.thelegendoftides.items.AzeelTrackerItem;
import lod.thelegendoftides.items.PricklebackItem;
import lod.thelegendoftides.items.RainbowTroutItem;
import lod.thelegendoftides.items.RockheadPufferfishItem;
import lod.thelegendoftides.items.SilverCarpItem;
import lod.thelegendoftides.items.SilverfishItem;
import lod.thelegendoftides.items.StardustFishItem;
import lod.thelegendoftides.items.SturgeonItem;
import lod.thelegendoftides.items.SwordfishItem;
import lod.thelegendoftides.items.LastKrakenJrItem;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotItems {
  private TlotItems() { }

  private static final Registrar<Item, ItemRegistryEvent> ITEM_REGISTRAR = new Registrar<>(GameEngine.REGISTRIES.items, Tlot.MOD_ID);

  public static final RegistryDelegate<BaitBoxItem> REGULAR_BAIT_BOX = ITEM_REGISTRAR.register("regular_bait_box", () -> new BaitBoxItem(TlotBait.REGULAR, 20, 200));
  public static final RegistryDelegate<BaitBoxItem> SPARKLING_BAIT_BOX = ITEM_REGISTRAR.register("sparkling_bait_box", () -> new BaitBoxItem(TlotBait.SPARKLING, 10, 400));
  public static final RegistryDelegate<BaitBoxItem> INFUSED_BAIT_BOX = ITEM_REGISTRAR.register("infused_bait_box", () -> new BaitBoxItem(TlotBait.INFUSED, 10, 200));
  public static final RegistryDelegate<BaitBoxItem> MAGNETIC_BAIT_BOX = ITEM_REGISTRAR.register("magnetic_bait_box", () -> new BaitBoxItem(TlotBait.MAGNETIC, 5, 500));

  // Fish
  public static final RegistryDelegate<CarpItem> CARP = ITEM_REGISTRAR.register("carp", () -> new CarpItem(25));
  public static final RegistryDelegate<SilverCarpItem> SILVER_CARP = ITEM_REGISTRAR.register("silver_carp", () -> new SilverCarpItem(50));
  public static final RegistryDelegate<GrandCarpItem> GRAND_CARP = ITEM_REGISTRAR.register("grand_carp", () -> new GrandCarpItem(100));
  public static final RegistryDelegate<BlackBassItem> BLACK_BASS = ITEM_REGISTRAR.register("black_bass", () -> new BlackBassItem(40));
  public static final RegistryDelegate<GrandBassItem> GRAND_BASS = ITEM_REGISTRAR.register("grand_bass", () -> new GrandBassItem(100));
  public static final RegistryDelegate<RainbowTroutItem> RAINBOW_TROUT = ITEM_REGISTRAR.register("rainbow_trout", () -> new RainbowTroutItem(30));
  public static final RegistryDelegate<GrandRainbowTroutItem> GRAND_RAINBOW_TROUT = ITEM_REGISTRAR.register("grand_rainbow_trout", () -> new GrandRainbowTroutItem(100));
  public static final RegistryDelegate<RockheadPufferfishItem> ROCKHEAD_PUFFERFISH = ITEM_REGISTRAR.register("rockhead_pufferfish", () -> new RockheadPufferfishItem(50));
  public static final RegistryDelegate<KoiItem> KOI = ITEM_REGISTRAR.register("koi", () -> new KoiItem(50));
  public static final RegistryDelegate<PricklebackItem> PRICKLEBACK = ITEM_REGISTRAR.register("prickleback", () -> new PricklebackItem(50));
  public static final RegistryDelegate<SwordfishItem> SWORDFISH = ITEM_REGISTRAR.register("swordfish", () -> new SwordfishItem(50));
  public static final RegistryDelegate<SturgeonItem> STURGEON = ITEM_REGISTRAR.register("sturgeon", () -> new SturgeonItem(50));
  public static final RegistryDelegate<SilverfishItem> SILVERFISH = ITEM_REGISTRAR.register("silverfish", () -> new SilverfishItem(150));
  public static final RegistryDelegate<GoldenfishItem> GOLDENFISH = ITEM_REGISTRAR.register("goldenfish", () -> new GoldenfishItem(300));
  public static final RegistryDelegate<GrandGoldenfishItem> GRAND_GOLDENFISH = ITEM_REGISTRAR.register("grand_goldenfish", () -> new GrandGoldenfishItem(500));
  public static final RegistryDelegate<StardustFishItem> STARDUSTFISH = ITEM_REGISTRAR.register("stardustfish", () -> new StardustFishItem(4000));
  public static final RegistryDelegate<LastKrakenJrItem> LAST_KRAKEN_JR = ITEM_REGISTRAR.register("last_kraken_jr", () -> new LastKrakenJrItem(800));
  public static final RegistryDelegate<AzeelGladiatorItem> AZEEL_GLADIATOR = ITEM_REGISTRAR.register("azeel_gladiator", () -> new AzeelGladiatorItem(1000));

  public static final RegistryDelegate<AzeelTrackerItem> MESSAGE_BOTTLE = ITEM_REGISTRAR.register("message_bottle", () -> new AzeelTrackerItem(0));

  static void register(final ItemRegistryEvent event) {
    ITEM_REGISTRAR.registryEvent(event);
  }
}
