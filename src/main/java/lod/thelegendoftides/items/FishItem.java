package lod.thelegendoftides.items;

import legend.game.inventory.Item;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import legend.lodmod.items.BattleItem;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FishItem extends BattleItem {
  public final Supplier<Fish> fish;

  public FishItem(final Supplier<Fish> fish, final int price) {
    super(null, price);
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
  public boolean canBeUsed(@NotNull final ItemStack stack, @NotNull final UsageLocation location) {
    return true;
  }

  @Override
  public boolean canTarget(@NotNull final ItemStack stack, @NotNull final TargetType type) {
    return false;
  }
}
