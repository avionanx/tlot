package lod.thelegendoftides;

import legend.game.combat.deff.DeffPackage;
import legend.game.combat.deff.RegisterDeffsEvent;
import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import static legend.core.GameEngine.REGISTRIES;
import static lod.thelegendoftides.Tlot.MOD_ID;

public final class TlotDeffs {
  private TlotDeffs() { }

  private static final Registrar<DeffPackage, RegisterDeffsEvent> REGISTRAR = new Registrar<>(REGISTRIES.deff, MOD_ID);

  public static final RegistryDelegate<DeffPackage> ROCKHEAD_PUFFERFISH = REGISTRAR.register("rockhead_pufferfish",TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> KOI = REGISTRAR.register("koi", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> SWORDFISH = REGISTRAR.register("swordfish", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> STURGEON = REGISTRAR.register("sturgeon", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> PRICKLEBACK = REGISTRAR.register("prickleback", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> CARP = REGISTRAR.register("carp", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> SILVER_CARP = REGISTRAR.register("silver_carp", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> GRAND_CARP = REGISTRAR.register("grand_carp", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> MIRAGE_CARP = REGISTRAR.register("mirage_carp", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> RAINBOW_TROUT = REGISTRAR.register("rainbow_trout", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> GRAND_RAINBOW_TROUT = REGISTRAR.register("grand_rainbow_trout", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> MIRAGE_TROUT = REGISTRAR.register("mirage_trout", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> AZEEL_GLADIATOR = REGISTRAR.register("azeel_gladiator", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> AZEEL_TRACKER = REGISTRAR.register("azeel_tracker", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> GOOSE = REGISTRAR.register("goose", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> LAST_KRAKEN_JR = REGISTRAR.register("last_kraken_jr", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> MURKRAY = REGISTRAR.register("murkray", TidesItemDeffPackage::new);
  public static final RegistryDelegate<DeffPackage> SHADESEEKER = REGISTRAR.register("shadeseeker", TidesItemDeffPackage::new);

  static void register(final RegisterDeffsEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
