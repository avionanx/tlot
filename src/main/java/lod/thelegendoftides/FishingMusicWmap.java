package lod.thelegendoftides;

import legend.core.audio.sequencer.assets.BackgroundMusic;
import legend.game.unpacker.FileData;

import java.util.List;

import static legend.core.GameEngine.AUDIO_THREAD;
import static legend.game.DrgnFiles.loadDrgnDirSync;
import static legend.game.sound.Audio.playMusicPackage;
import static legend.game.sound.Audio.unloadSoundFile;

public class FishingMusicWmap extends FishingMusic {
  public FishingMusicWmap(final int musicIndex) {
    super(musicIndex);
  }

  @Override
  public void playMusic() {
    final int fileIndex = 5850 + this.musicIndex * 5;

    if(AUDIO_THREAD.getSongId() * 5 + 5815 == fileIndex) {
      return;
    }

    unloadSoundFile(8);
    loadDrgnDirSync(0, fileIndex, files -> this.musicFilesLoadedCallback(files, fileIndex, true));
  }

  @Override
  protected void musicFilesLoadedCallback(final List<FileData> files, final int fileIndex, final boolean startSequence) {
    playMusicPackage(new BackgroundMusic(files, fileIndex), startSequence);
  }
}
