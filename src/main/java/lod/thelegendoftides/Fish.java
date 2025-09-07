package lod.thelegendoftides;

import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import org.legendofdragoon.modloader.registries.RegistryEntry;

public abstract class Fish extends RegistryEntry {
  public final ItemIcon icon;
  /** How long you have to fight this fish */
  public final float stamina;
  /** How quickly this fish pulls away */
  public final float strength;

  public Fish(final ItemIcon icon, final float stamina, final float strength) {
    this.icon = icon;
    this.stamina = stamina;
    this.strength = strength;
  }

  public abstract ItemStack getReward();
}
