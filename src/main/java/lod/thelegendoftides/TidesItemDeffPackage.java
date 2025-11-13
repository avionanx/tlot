package lod.thelegendoftides;

import legend.game.combat.Battle;
import legend.game.combat.deff.DeffPackage;
import legend.game.unpacker.Loader;

import static legend.game.EngineStates.currentEngineState_8004dd04;

public class TidesItemDeffPackage extends DeffPackage {

  public TidesItemDeffPackage() {

  }

  @Override
  public void load() {
    ((Battle)currentEngineState_8004dd04).loadDeff(
      Loader.resolve("../mods/tlot/items/%s/textures/".formatted(this.getRegistryId().entryId())),
      Loader.resolve("../mods/tlot/items/%s/scripts/".formatted(this.getRegistryId().entryId())));
  }
}
