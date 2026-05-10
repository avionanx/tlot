package lod.thelegendoftides;

import legend.game.inventory.Equipment;
import legend.game.inventory.Good;
import legend.game.inventory.InventoryEntry;
import legend.game.inventory.ItemIcon;

import java.util.function.Supplier;

public class GoodFish extends Fish {
  private final Supplier<Good> reward;

  public GoodFish(final ItemIcon icon, final float stamina, final float strength, final int xp, final Supplier<Good> reward) {
    super(icon, stamina, strength, xp);
    this.reward = reward;
  }

  @Override
  public Good getReward() {
    return this.reward.get();
  }
}
