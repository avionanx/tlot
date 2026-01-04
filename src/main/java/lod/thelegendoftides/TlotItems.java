package lod.thelegendoftides;

import legend.core.GameEngine;
import legend.game.inventory.Item;
import legend.game.inventory.ItemRegistryEvent;
import lod.thelegendoftides.items.ArgentfinItem;
import lod.thelegendoftides.items.AzeelGladiatorItem;
import lod.thelegendoftides.items.BaitBoxItem;
import lod.thelegendoftides.items.BlackBassItem;
import lod.thelegendoftides.items.CarpItem;
import lod.thelegendoftides.items.GoldenfishItem;
import lod.thelegendoftides.items.GooseItem;
import lod.thelegendoftides.items.GrandBassItem;
import lod.thelegendoftides.items.GrandCarpItem;
import lod.thelegendoftides.items.GrandGoldenfishItem;
import lod.thelegendoftides.items.GrandRainbowTroutItem;
import lod.thelegendoftides.items.KoiItem;
import lod.thelegendoftides.items.AzeelTrackerItem;
import lod.thelegendoftides.items.MirageCarpItem;
import lod.thelegendoftides.items.MirageTroutItem;
import lod.thelegendoftides.items.MurkrayItem;
import lod.thelegendoftides.items.PricklebackItem;
import lod.thelegendoftides.items.RainbowTroutItem;
import lod.thelegendoftides.items.RockheadPufferfishItem;
import lod.thelegendoftides.items.ShadeseekerItem;
import lod.thelegendoftides.items.SilverCarpItem;
import lod.thelegendoftides.items.SilverfishItem;
import lod.thelegendoftides.items.StardustFishItem;
import lod.thelegendoftides.items.SturgeonItem;
import lod.thelegendoftides.items.SwordfishItem;
import lod.thelegendoftides.items.LastKrakenJrItem;
import lod.thelegendoftides.items.WiseSalmonItem;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotItems {
  private TlotItems() { }

  private static final Registrar<Item, ItemRegistryEvent> ITEM_REGISTRAR = new Registrar<>(GameEngine.REGISTRIES.items, Tlot.MOD_ID);

  public static final RegistryDelegate<BaitBoxItem> REGULAR_BAIT_BOX = ITEM_REGISTRAR.register("regular_bait_box", () -> new BaitBoxItem(TlotBait.REGULAR, 20, 5));
  public static final RegistryDelegate<BaitBoxItem> SPARKLING_BAIT_BOX = ITEM_REGISTRAR.register("sparkling_bait_box", () -> new BaitBoxItem(TlotBait.SPARKLING, 10, 20));
  public static final RegistryDelegate<BaitBoxItem> INFUSED_BAIT_BOX = ITEM_REGISTRAR.register("infused_bait_box", () -> new BaitBoxItem(TlotBait.INFUSED, 10, 10));
  public static final RegistryDelegate<BaitBoxItem> MAGNETIC_BAIT_BOX = ITEM_REGISTRAR.register("magnetic_bait_box", () -> new BaitBoxItem(TlotBait.MAGNETIC, 5, 40));

  // Fish
  public static final RegistryDelegate<CarpItem> CARP = ITEM_REGISTRAR.register("carp", CarpItem::new);
  public static final RegistryDelegate<SilverCarpItem> SILVER_CARP = ITEM_REGISTRAR.register("silver_carp", SilverCarpItem::new);
  public static final RegistryDelegate<GrandCarpItem> GRAND_CARP = ITEM_REGISTRAR.register("grand_carp", GrandCarpItem::new);
  public static final RegistryDelegate<MirageCarpItem> MIRAGE_CARP = ITEM_REGISTRAR.register("mirage_carp", MirageCarpItem::new);
  public static final RegistryDelegate<BlackBassItem> BLACK_BASS = ITEM_REGISTRAR.register("black_bass", BlackBassItem::new);
  public static final RegistryDelegate<GrandBassItem> GRAND_BASS = ITEM_REGISTRAR.register("grand_bass", GrandBassItem::new);
  public static final RegistryDelegate<WiseSalmonItem> WISE_SALMON = ITEM_REGISTRAR.register("wise_salmon", WiseSalmonItem::new);
  public static final RegistryDelegate<RainbowTroutItem> RAINBOW_TROUT = ITEM_REGISTRAR.register("rainbow_trout", RainbowTroutItem::new);
  public static final RegistryDelegate<GrandRainbowTroutItem> GRAND_RAINBOW_TROUT = ITEM_REGISTRAR.register("grand_rainbow_trout", GrandRainbowTroutItem::new);
  public static final RegistryDelegate<MirageTroutItem> MIRAGE_TROUT = ITEM_REGISTRAR.register("mirage_trout", MirageTroutItem::new);
  public static final RegistryDelegate<RockheadPufferfishItem> ROCKHEAD_PUFFERFISH = ITEM_REGISTRAR.register("rockhead_pufferfish", RockheadPufferfishItem::new);
  public static final RegistryDelegate<ArgentfinItem> ARGENTFIN = ITEM_REGISTRAR.register("argentfin", ArgentfinItem::new);
  public static final RegistryDelegate<KoiItem> KOI = ITEM_REGISTRAR.register("koi", KoiItem::new);
  public static final RegistryDelegate<PricklebackItem> PRICKLEBACK = ITEM_REGISTRAR.register("prickleback", PricklebackItem::new);
  public static final RegistryDelegate<SwordfishItem> SWORDFISH = ITEM_REGISTRAR.register("swordfish", SwordfishItem::new);
  public static final RegistryDelegate<SturgeonItem> STURGEON = ITEM_REGISTRAR.register("sturgeon", SturgeonItem::new);
  public static final RegistryDelegate<ShadeseekerItem> SHADESEEKER = ITEM_REGISTRAR.register("shadeseeker", ShadeseekerItem::new);
  public static final RegistryDelegate<MurkrayItem> MURKRAY = ITEM_REGISTRAR.register("murkray", MurkrayItem::new);
  public static final RegistryDelegate<SilverfishItem> SILVERFISH = ITEM_REGISTRAR.register("silverfish", SilverfishItem::new);
  public static final RegistryDelegate<GoldenfishItem> GOLDENFISH = ITEM_REGISTRAR.register("goldenfish", GoldenfishItem::new);
  public static final RegistryDelegate<GrandGoldenfishItem> GRAND_GOLDENFISH = ITEM_REGISTRAR.register("grand_goldenfish", GrandGoldenfishItem::new);
  public static final RegistryDelegate<StardustFishItem> STARDUSTFISH = ITEM_REGISTRAR.register("stardustfish", StardustFishItem::new);
  public static final RegistryDelegate<LastKrakenJrItem> LAST_KRAKEN_JR = ITEM_REGISTRAR.register("last_kraken_jr", LastKrakenJrItem::new);
  public static final RegistryDelegate<AzeelGladiatorItem> AZEEL_GLADIATOR = ITEM_REGISTRAR.register("azeel_gladiator", AzeelGladiatorItem::new);
  public static final RegistryDelegate<GooseItem> GOOSE = ITEM_REGISTRAR.register("goose", GooseItem::new);

  public static final RegistryDelegate<AzeelTrackerItem> AZEEL_TRACKER = ITEM_REGISTRAR.register("azeel_tracker", AzeelTrackerItem::new);

  static void register(final ItemRegistryEvent event) {
    ITEM_REGISTRAR.registryEvent(event);
  }
}
