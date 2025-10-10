package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class GrandGoldenfishItem extends FishItem {
  public GrandGoldenfishItem(final int price) {
    super(TlotFish.GRAND_GOLDENFISH, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
