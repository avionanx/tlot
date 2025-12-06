package lod.thelegendoftides;

import legend.game.inventory.InventoryEntry;
import legend.game.inventory.ItemIcon;
import org.legendofdragoon.modloader.registries.RegistryEntry;

import static legend.core.GameEngine.CONFIG;
import static lod.thelegendoftides.Tlot.CATCH_FLAGS_CONFIG;

public abstract class Fish extends RegistryEntry {
  public final ItemIcon icon;
  /** How long you have to fight this fish */
  public final float stamina;
  /** How quickly this fish pulls away */
  public final float strength;
  /** If legendary, bit index of CATCH_FLAGS, otherwise -1 */
  public int legendaryIndex;
  /** Whether fish is shown in fishlistscreen or book */
  public boolean isHidden;

  public Fish(final ItemIcon icon, final float stamina, final float strength) {
    this.icon = icon;
    this.stamina = stamina;
    this.strength = strength;
    this.legendaryIndex = -1;
  }

  public Fish setHidden() {
    this.isHidden = true;
    return this;
  }

  public Fish setLegendary(final int index) {
    this.legendaryIndex = 1 << index;
    return this;
  }

  public abstract InventoryEntry getReward();

  public boolean canBeCaught() {
    return this.legendaryIndex < 0 || (this.legendaryIndex & (1L << CONFIG.getConfig(CATCH_FLAGS_CONFIG.get()))) == 0;
  }
}
