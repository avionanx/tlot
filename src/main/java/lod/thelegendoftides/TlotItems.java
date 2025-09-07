package lod.thelegendoftides;

import legend.core.GameEngine;
import legend.game.inventory.Item;
import legend.game.inventory.ItemRegistryEvent;
import lod.thelegendoftides.items.BaitBoxItem;
import lod.thelegendoftides.items.CarpItem;
import lod.thelegendoftides.items.RainbowTroutItem;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotItems {
  private TlotItems() { }

  private static final Registrar<Item, ItemRegistryEvent> ITEM_REGISTRAR = new Registrar<>(GameEngine.REGISTRIES.items, Tlot.MOD_ID);

  public static final RegistryDelegate<BaitBoxItem> REGULAR_BAIT_BOX = ITEM_REGISTRAR.register("regular_bait_box", () -> new BaitBoxItem(TlotBait.REGULAR, 20, 150));
  public static final RegistryDelegate<BaitBoxItem> PREMIUM_BAIT_BOX = ITEM_REGISTRAR.register("premium_bait_box", () -> new BaitBoxItem(TlotBait.PREMIUM, 20, 250));
  public static final RegistryDelegate<BaitBoxItem> SHRIMP_BAIT_BOX = ITEM_REGISTRAR.register("shrimp_bait_box", () -> new BaitBoxItem(TlotBait.SHRIMP, 10, 300));

  // Fish
  public static final RegistryDelegate<CarpItem> CARP = ITEM_REGISTRAR.register("carp", () -> new CarpItem(25));
  public static final RegistryDelegate<RainbowTroutItem> RAINBOW_TROUT = ITEM_REGISTRAR.register("rainbow_trout", () -> new RainbowTroutItem(50));

  static void register(final ItemRegistryEvent event) {
    ITEM_REGISTRAR.registryEvent(event);
  }
}
