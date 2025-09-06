package lod.thelegendoftides.items;

import legend.game.inventory.Item;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class FishItem extends Item {
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
  public boolean canBeUsed(@NotNull final ItemStack stack, @NotNull final UsageLocation location) {
    return false;
  }

  @Override
  public boolean canTarget(@NotNull final ItemStack stack, @NotNull final TargetType type) {
    return false;
  }
}
