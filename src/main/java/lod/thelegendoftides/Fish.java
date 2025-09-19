package lod.thelegendoftides;

import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import org.legendofdragoon.modloader.registries.RegistryEntry;

import static legend.core.GameEngine.CONFIG;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS;

public abstract class Fish extends RegistryEntry {
  public final ItemIcon icon;
  /** How long you have to fight this fish */
  public final float stamina;
  /** How quickly this fish pulls away */
  public final float strength;
  /** If legendary, bit index of CATCH_FLAGS, otherwise -1*/
  public final int legendaryIndex;

  public Fish(final ItemIcon icon, final float stamina, final float strength) {
    this(icon, stamina, strength, -1);
  }

  public Fish(final ItemIcon icon, final float stamina, final float strength, final int legendaryIndex) {
    this.icon = icon;
    this.stamina = stamina;
    this.strength = strength;
    this.legendaryIndex = legendaryIndex;
  }

  public abstract ItemStack getReward();

  public boolean canBeCaught() {
    return this.legendaryIndex < 0 || (this.legendaryIndex & (1L << CONFIG.getConfig(CATCH_FLAGS.get()))) == 0;
  }
}
