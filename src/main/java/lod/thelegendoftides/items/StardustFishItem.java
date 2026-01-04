package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class StardustFishItem extends FishItem {
  public StardustFishItem() {
    super(TlotFish.STARDUSTFISH);
  }

  @Override
  int getUnitPrice() {
   return 4000;
  }

 @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
