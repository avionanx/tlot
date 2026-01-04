package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class GrandGoldenfishItem extends FishItem {
  public GrandGoldenfishItem() {
    super(TlotFish.GRAND_GOLDENFISH);
  }

  @Override
  int getUnitPrice() {
   return 600;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
