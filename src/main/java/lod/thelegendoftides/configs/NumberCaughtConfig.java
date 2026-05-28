package lod.thelegendoftides.configs;

import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigStorageLocation;
import legend.game.scripting.Param;
import legend.game.scripting.ScriptReadable;

import java.nio.ByteBuffer;

import static legend.core.GameEngine.CONFIG;

public class NumberCaughtConfig extends ConfigEntry<Integer> implements ScriptReadable {
  public NumberCaughtConfig() {
    super(
      0,
      ConfigStorageLocation.SAVE,
      ConfigCategory.OTHER,
      value -> ByteBuffer.allocate(Long.BYTES).putInt(value).array(),
      bytes -> ByteBuffer.wrap(bytes).getInt()
    );
  }

  @Override
  public void read(final int index, final Param out) {
    out.set(CONFIG.getConfig(this));
  }
}
