package lod.thelegendoftides.configs;

import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigStorageLocation;
import legend.game.scripting.Param;
import legend.game.scripting.ScriptReadable;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static legend.core.GameEngine.CONFIG;

public class EventFlagsConfig extends ConfigEntry<int[]> implements ScriptReadable {
  public EventFlagsConfig() {
    super(
      new int[32],
      ConfigStorageLocation.SAVE,
      ConfigCategory.OTHER,
      value -> ByteBuffer.allocate(Integer.BYTES * (int)Arrays.stream(value).count()).array(),
      bytes -> ByteBuffer.wrap(bytes).asIntBuffer().array()
    );
  }

  @Override
  public void read(final int index, final Param out) {
    CONFIG.getConfig(this)[0] = 3;

    out.set(CONFIG.getConfig(this)[index]);
  }
}
