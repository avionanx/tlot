package lod.thelegendoftides;

import legend.game.inventory.ItemIcon;
import legend.game.inventory.ItemStack;
import legend.lodmod.LodItems;
import lod.thelegendoftides.icons.FishIcon;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

public final class TlotFish {
  private TlotFish() { }

  private static final Registrar<Fish, RegisterFishEvent> REGISTRAR = new Registrar<>(Tlot.FISH_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<Fish> CARP = REGISTRAR.register("carp", () -> new RegularFish(new FishIcon(0), 30.0f, 1.0f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> RAINBOW_TROUT = REGISTRAR.register("rainbow_trout", () -> new RegularFish(new FishIcon(1), 30.0f, 1.5f, () -> new ItemStack(TlotItems.RAINBOW_TROUT.get())));

  public static final RegistryDelegate<Fish> COMMON_TRASH = REGISTRAR.register("common_trash", () -> new RandomRewardFish(ItemIcon.SACK, 30.0f, 1.5f, () -> new ItemStack(LodItems.HEALING_POTION.get()), () -> new ItemStack(LodItems.CHARM_POTION.get())));

  static void register(final RegisterFishEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
