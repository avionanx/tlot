package lod.thelegendoftides.items;

import legend.game.inventory.Item;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import lod.thelegendoftides.Bait;
import lod.thelegendoftides.Tlot;

import java.util.function.Supplier;

public class BaitBoxItem extends Item implements BaitItem {
  private final Supplier<Bait> bait;
  private final int uses;
  private final int unitPrice;

  public BaitBoxItem(final Supplier<Bait> bait, final int uses, final int unitPrice) {
    super(ItemIcon.BAG, 0);
    this.bait = bait;
    this.uses = uses;
    this.unitPrice = unitPrice;
  }

  @Override
  public Bait getBait(final ItemStack stack) {
    return this.bait.get();
  }

  @Override
  public void consumeBait(final ItemStack stack) {
    stack.damage(1);
  }

  @Override
  public boolean hasDurability(final ItemStack stack) {
    return true;
  }

  @Override
  public int getMaxDurability(final ItemStack stack) {
    return this.uses;
  }

  @Override
  public boolean canBeUsed(final ItemStack stack, final UsageLocation location) {
    return Tlot.isFishEncounter;
  }

  @Override
  public boolean canTarget(final ItemStack stack, final TargetType type) {
    return false;
  }

  @Override
  public int getSellPrice(final ItemStack stack) {
   return this.unitPrice * stack.getCurrentDurability();
  }

  @Override
  public int getBuyPrice(final ItemStack stack) {
   return this.unitPrice * stack.getCurrentDurability();
  }
}
