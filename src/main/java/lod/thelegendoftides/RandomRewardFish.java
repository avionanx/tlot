package lod.thelegendoftides;

import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;

import java.util.Random;
import java.util.function.Supplier;

public class RandomRewardFish extends Fish {
  private static final Random RAND = new Random();

  private final Supplier<ItemStack>[] rewardPool;

  @SafeVarargs
  public RandomRewardFish(final ItemIcon icon, final float stamina, final float strength, final Supplier<ItemStack>... rewardPool) {
    super(icon, stamina, strength);
    this.rewardPool = rewardPool;
  }

  @Override
  public ItemStack getReward() {
    return this.rewardPool[RAND.nextInt(this.rewardPool.length)].get();
  }
}
