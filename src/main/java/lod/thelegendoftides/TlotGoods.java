package lod.thelegendoftides;

import legend.game.inventory.Good;
import legend.game.inventory.GoodsRegistryEvent;
import legend.game.inventory.ItemIcon;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import static legend.core.GameEngine.REGISTRIES;

public final class TlotGoods {
  private TlotGoods() { }

  private static final Registrar<Good, GoodsRegistryEvent> REGISTRAR = new Registrar<>(REGISTRIES.goods, Tlot.MOD_ID);

  public static final RegistryDelegate<Good> DRAGONI = REGISTRAR.register("dragoni", () -> new Good(1000, ItemIcon.YELLOW_POTION));
  public static final RegistryDelegate<Good> TREASURE_GOONER_3000 = REGISTRAR.register("treasure_gooner_3000", () -> new Good(1200, ItemIcon.BELL));

  static void register(final GoodsRegistryEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
