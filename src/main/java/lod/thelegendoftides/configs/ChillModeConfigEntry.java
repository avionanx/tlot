package lod.thelegendoftides.configs;

import legend.game.saves.BoolConfigEntry;
import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigStorageLocation;

public class ChillModeConfigEntry extends BoolConfigEntry {
  public ChillModeConfigEntry()  {
    super(false, ConfigStorageLocation.GLOBAL, ConfigCategory.GAMEPLAY);
  }

  @Override
  public boolean hasHelp() {
    return true;
  }
}
