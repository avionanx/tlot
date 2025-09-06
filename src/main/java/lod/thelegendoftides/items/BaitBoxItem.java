package lod.thelegendoftides.items;

import legend.game.inventory.Item;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import lod.thelegendoftides.Tlot;

public class BaitBoxItem extends Item {
  public BaitBoxItem() {
    super(ItemIcon.SACK, 150);
  }

  @Override
  public boolean hasDurability(final ItemStack stack) {
    return true;
  }

  @Override
  public int getMaxDurability(final ItemStack stack) {
    return 20;
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return Tlot.isFishEncounter;
  }

  @Override
  public boolean canTarget(final ItemStack stack, final TargetType type) {
    return false;
  }
}
