package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.Bait;

public interface BaitItem {
  Bait getBait(final ItemStack stack);
  void consumeBait(final ItemStack stack);
}
