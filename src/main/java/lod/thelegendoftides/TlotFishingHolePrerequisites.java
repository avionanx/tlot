package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import java.util.function.Supplier;

import static legend.core.GameEngine.CONFIG;
import static lod.thelegendoftides.Tlot.TLOT_FLAGS_OTHER;

public class TlotFishingHolePrerequisites {
  public TlotFishingHolePrerequisites() { }

  public static final Registrar<FishingHolePrerequisites, RegisterFishingHolePrerequisitiesEvent> REGISTRAR = new Registrar<>(Tlot.FISHING_HOLE_PREREQUISITES_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<FishingHolePrerequisites> NONE = REGISTRAR.register("none", () -> new FishingHolePrerequisites(new Supplier[]{}));
  // Azeel Gladiator
  public static final RegistryDelegate<FishingHolePrerequisites> UNDERSEA_CAVERN = REGISTRAR.register("undersea_cavern", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::isAtUnderseaCavern
  }));
  public static final RegistryDelegate<FishingHolePrerequisites> PRAIRIE = REGISTRAR.register("prairie", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::isAtPrairie
  }));
  public static final RegistryDelegate<FishingHolePrerequisites> SHRINE = REGISTRAR.register("shrine", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::isAtShrine
  }));
  public static final RegistryDelegate<FishingHolePrerequisites> AGLIS = REGISTRAR.register("aglis", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::isAtAglis
  }));
  public static final RegistryDelegate<FishingHolePrerequisites> BALE = REGISTRAR.register("bale", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::isAtBale
  }));

  // Utility methods
  private static Boolean isAtUnderseaCavern() {
    return (0x1 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x1;
  }
  private static Boolean isAtPrairie() {
    return (0x2 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x2;
  }
  private static Boolean isAtShrine() {
    return (0x4 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x4;
  }
  private static Boolean isAtAglis() {
    return (0x8 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x8;
  }
  private static Boolean isAtBale() {
    return (0x10 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x10;
  }

  private static Boolean wasAtUnderseaCavern() {
    return (0x1000 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x1000;
  }
  private static Boolean wasAtPrairie() {
    return (0x2000 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x2000;
  }
  private static Boolean wasAtShrine() {
    return (0x4000 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x4000;
  }
  private static Boolean wasAtAglis() {
    return (0x8000 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x8000;
  }
  private static Boolean wasAtBale() {
    return (0x10000 & CONFIG.getConfig(TLOT_FLAGS_OTHER.get())) == 0x10000;
  }
  static void register(final RegisterFishingHolePrerequisitiesEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
