package lod.thelegendoftides;

import legend.game.inventory.ItemStack;
import lod.thelegendoftides.icons.FishIcon;

import java.util.function.Supplier;

public class RegularFish extends Fish {
  private final Supplier<ItemStack> reward;

  public RegularFish(final FishIcon icon, final float stamina, final float strength, final Supplier<ItemStack> reward) {
    super(icon, stamina, strength);
    this.reward = reward;
  }

  @Override
  public ItemStack getReward() {
    return this.reward.get();
  }
}
