package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class SilverfishItem extends FishItem {
  public SilverfishItem() {
    super(TlotFish.SILVERFISH);
  }

  @Override
  int getUnitPrice() {
   return 108;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
