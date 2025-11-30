package lod.thelegendoftides.configs;

import legend.core.memory.types.IntRef;
import legend.game.saves.ConfigCategory;
import legend.game.saves.ConfigEntry;
import legend.game.saves.ConfigStorageLocation;
import legend.game.unpacker.ExpandableFileData;
import legend.game.unpacker.FileData;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.HashSet;
import java.util.Set;

public class SeenFishConfig extends ConfigEntry<Set<RegistryId>> {
  public SeenFishConfig() {
    super(new HashSet<>(), ConfigStorageLocation.SAVE, ConfigCategory.OTHER, SeenFishConfig::serialize, SeenFishConfig::deserialize);
  }

  private static byte[] serialize(final Set<RegistryId> ids) {
    final FileData data = new ExpandableFileData(ids.size() * 15);
    final IntRef offset = new IntRef();

    data.writeVarInt(offset, ids.size());

    for(final RegistryId id : ids) {
      data.writeRegistryId(offset, id);
    }

    return data.getBytes();
  }

  private static Set<RegistryId> deserialize(final byte[] in) {
    final Set<RegistryId> ids = new HashSet<>();
    final FileData data = new FileData(in);
    final IntRef offset = new IntRef();

    final int count = data.readVarInt(offset);

    for(int i = 0; i < count; i++) {
      ids.add(data.readRegistryId(offset));
    }

    return ids;
  }
}
