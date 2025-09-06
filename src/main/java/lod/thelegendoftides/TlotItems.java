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

  public static final RegistryDelegate<BaitBoxItem> BAIT_BOX = ITEM_REGISTRAR.register("bait_box", BaitBoxItem::new);

  // Fish
  public static final RegistryDelegate<CarpItem> CARP = ITEM_REGISTRAR.register("carp", () -> new CarpItem(25));
  public static final RegistryDelegate<RainbowTroutItem> RAINBOW_TROUT = ITEM_REGISTRAR.register("rainbow_trout", () -> new RainbowTroutItem(50));

  static void register(final ItemRegistryEvent event) {
    ITEM_REGISTRAR.registryEvent(event);
  }
}
