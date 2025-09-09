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

  public static final RegistryDelegate<Fish> CARP = REGISTRAR.register("carp", () -> new RegularFish(new FishIcon(0), 30.0f, 0.2f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> SILVER_CARP = REGISTRAR.register("silver_carp", () -> new RegularFish(new FishIcon(0), 30.0f, 0.3f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> GRAND_CARP = REGISTRAR.register("grand_carp", () -> new RegularFish(new FishIcon(0), 40.0f, 0.4f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> BLACK_BASS = REGISTRAR.register("black_bass", () -> new RegularFish(new FishIcon(0), 30.0f, 1.0f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> GRAND_BASS = REGISTRAR.register("grand_bass", () -> new RegularFish(new FishIcon(0), 30.0f, 1.0f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> RAINBOW_TROUT = REGISTRAR.register("rainbow_trout", () -> new RegularFish(new FishIcon(1), 30.0f, 0.3f, () -> new ItemStack(TlotItems.RAINBOW_TROUT.get())));
  public static final RegistryDelegate<Fish> GRAND_RAINBOW_TROUT = REGISTRAR.register("grand_rainbow_trout", () -> new RegularFish(new FishIcon(1), 40.0f, 0.5f, () -> new ItemStack(TlotItems.RAINBOW_TROUT.get())));
  public static final RegistryDelegate<Fish> ROCKHEAD_PUFFERFISH = REGISTRAR.register("rockhead_pufferfish", () -> new RegularFish(new FishIcon(0), 30.0f, 0.2f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> PRICKLEBACK = REGISTRAR.register("prickleback", () -> new RegularFish(new FishIcon(0), 40.0f, 0.2f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> SWORDFISH = REGISTRAR.register("swordfish", () -> new RegularFish(new FishIcon(0), 40.0f, 0.5f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> STURGEON = REGISTRAR.register("sturgeon", () -> new RegularFish(new FishIcon(0), 40.0f, 0.5f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> SILVERFISH = REGISTRAR.register("silverfish", () -> new RegularFish(new FishIcon(0), 30.0f, 0.2f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> GOLDENFISH = REGISTRAR.register("goldenfish", () -> new RegularFish(new FishIcon(0), 36.0f, 0.25f, () -> new ItemStack(TlotItems.CARP.get())));
  public static final RegistryDelegate<Fish> GRAND_GOLDENFISH = REGISTRAR.register("grand_goldenfish", () -> new RegularFish(new FishIcon(0), 42.0f, 0.35f, () -> new ItemStack(TlotItems.CARP.get())));

  public static final RegistryDelegate<Fish> COMMON_TRASH = REGISTRAR.register("common_trash", () -> new RandomRewardFish(ItemIcon.SACK, 30.0f, 1.5f, () -> new ItemStack(LodItems.HEALING_POTION.get()), () -> new ItemStack(LodItems.CHARM_POTION.get())));

  static void register(final RegisterFishEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
