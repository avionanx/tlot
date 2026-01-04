package lod.thelegendoftides.items;

import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import legend.lodmod.items.BattleItem;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class FishItem extends BattleItem {
  public final Supplier<Fish> fish;

  public FishItem(final Supplier<Fish> fish) {
    super(null, 0);
    this.fish = fish;
  }

  @Override
  public ItemIcon getIcon(@NotNull final ItemStack stack) {
    return this.fish.get().icon;
  }

  @Override
  public int getMaxStackSize(@NotNull ItemStack stack) {
    return 5;
  }

  @Override
  public int getSellPrice(final ItemStack stack) {
   return this.getUnitPrice() * stack.getSize();
  }

  abstract int getUnitPrice();

  @Override
  public boolean canBeUsed(@NotNull final ItemStack stack, @NotNull final UsageLocation location) {
    return true;
  }

  @Override
  public boolean canTarget(@NotNull final ItemStack stack, @NotNull final TargetType type) {
    return false;
  }
}
