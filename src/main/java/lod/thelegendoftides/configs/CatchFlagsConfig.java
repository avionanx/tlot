package lod.thelegendoftides.configs;

import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigStorageLocation;

import java.nio.ByteBuffer;

public class CatchFlagsConfig extends ConfigEntry<Long> {
  public CatchFlagsConfig() {
    super(
      0L,
      ConfigStorageLocation.SAVE,
      ConfigCategory.OTHER,
      value -> ByteBuffer.allocate(Long.BYTES).putLong(value).array(),
      bytes -> ByteBuffer.wrap(bytes).getLong()
    );
  }
}
