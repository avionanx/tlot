package lod.thelegendoftides.items;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.TlotFish;

public class StardustFishItem extends FishItem {
  public StardustFishItem(final int price) {
    super(TlotFish.STARDUSTFISH, price);
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return false;
  }
}
