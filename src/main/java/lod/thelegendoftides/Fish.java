package lod.thelegendoftides;

import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.RegistryEntry;

public class Fish extends RegistryEntry {
  public final FishIcon icon;
  /** How quickly this fish pulls away */
  public final float strength;

  public Fish(final FishIcon icon, final float strength) {
    this.icon = icon;
    this.strength = strength;
  }
}
