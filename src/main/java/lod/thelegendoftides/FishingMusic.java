package lod.thelegendoftides;

import legend.game.unpacker.FileData;
import org.legendofdragoon.modloader.registries.RegistryEntry;

import java.util.List;

public abstract class FishingMusic extends RegistryEntry {
  protected final int musicIndex;

  public FishingMusic(final int musicIndex) {
    this.musicIndex = musicIndex;
  }

  public abstract void playMusic();


  protected abstract void musicFilesLoadedCallback(final List<FileData> files, final int fileIndex, final boolean startSequence);
}
