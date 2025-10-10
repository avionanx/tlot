package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class GoldenfishItem extends FishItem {
  public GoldenfishItem(final int price) {
    super(TlotFish.GOLDENFISH, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
