package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class GoldenfishItem extends FishItem {
  public GoldenfishItem() {
    super(TlotFish.GOLDENFISH);
  }

  @Override
  int getUnitPrice() {
   return 375;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
