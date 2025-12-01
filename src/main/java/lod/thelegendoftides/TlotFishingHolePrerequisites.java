package lod.thelegendoftides;

import org.legendofdragoon.modloader.registries.Registrar;
import org.legendofdragoon.modloader.registries.RegistryDelegate;

import java.util.function.Supplier;

public class TlotFishingHolePrerequisites {
  public TlotFishingHolePrerequisites() { }

  public static final Registrar<FishingHolePrerequisites, RegisterFishingHolePrerequisitiesEvent> REGISTRAR = new Registrar<>(Tlot.FISHING_HOLE_PREREQUISITES_REGISTRY, Tlot.MOD_ID);

  public static final RegistryDelegate<FishingHolePrerequisites> NONE = REGISTRAR.register("none", () -> new FishingHolePrerequisites(new Supplier[]{}));
  // Azeel Gladiator
  public static final RegistryDelegate<FishingHolePrerequisites> UNDERSEA_CAVERN_DEPTHS = REGISTRAR.register("undersea_cavern_depths", () -> new FishingHolePrerequisites(new Supplier[]{
    TlotFishingHolePrerequisites::hasObtainedTracker
  }));

  // Utility methods
  private static Boolean hasObtainedTracker() {
    return !TlotFish.MESSAGE_BOTTLE.get().canBeCaught();
  }

  static void register(final RegisterFishingHolePrerequisitiesEvent event) {
    REGISTRAR.registryEvent(event);
  }
}
