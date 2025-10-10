package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class SilverfishItem extends FishItem {
  public SilverfishItem(final int price) {
    super(TlotFish.SILVERFISH, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
