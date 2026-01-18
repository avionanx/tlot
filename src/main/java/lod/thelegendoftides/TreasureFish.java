package lod.thelegendoftides;

import legend.game.inventory.Equipment;
import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import lod.thelegendoftides.icons.FishIcon;

import java.util.function.Supplier;

public class TreasureFish extends Fish {
  private final Supplier<Equipment> reward;

  public TreasureFish(final ItemIcon icon, final float stamina, final float strength, final Supplier<Equipment> reward) {
    super(icon, stamina, strength);
    this.reward = reward;
  }

  @Override
  public Equipment getReward() {
    return this.reward.get();
  }
}
