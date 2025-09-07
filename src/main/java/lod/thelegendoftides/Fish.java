package lod.thelegendoftides;

import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.RegistryEntry;

public class Fish extends RegistryEntry {
  public final FishIcon icon;
  /** How long you have to fight this fish */
  public final float stamina;
  /** How quickly this fish pulls away */
  public final float strength;

  public Fish(final FishIcon icon, final float stamina, final float strength) {
    this.icon = icon;
    this.stamina = stamina;
    this.strength = strength;
  }
}
