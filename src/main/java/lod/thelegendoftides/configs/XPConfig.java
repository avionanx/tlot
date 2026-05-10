package lod.thelegendoftides.configs;

import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigStorageLocation;
import legend.game.scripting.Param;
import legend.game.scripting.ScriptReadable;

import java.nio.ByteBuffer;

import static legend.core.GameEngine.CONFIG;

public class XPConfig extends ConfigEntry<Integer> {
  public XPConfig() {
    super(
      0,
      ConfigStorageLocation.SAVE,
      ConfigCategory.OTHER,
      value -> ByteBuffer.allocate(Long.BYTES).putInt(value).array(),
      bytes -> ByteBuffer.wrap(bytes).getInt()
    );
  }
}
