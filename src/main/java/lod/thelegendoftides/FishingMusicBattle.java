package lod.thelegendoftides;

import legend.core.audio.sequencer.assets.BackgroundMusic;
import legend.game.unpacker.FileData;

import java.util.List;

import static legend.core.GameEngine.AUDIO_THREAD;
import static legend.game.DrgnFiles.loadDrgnDir;
import static legend.game.sound.Audio._800bd0f0;
import static legend.game.sound.Audio.musicLoaded_800bd782;
import static legend.game.sound.Audio.unloadSoundFile;

public class FishingMusicBattle extends FishingMusic {
  public FishingMusicBattle(int musicIndex) {
    super(musicIndex);
  }

  @Override
  public void playMusic() {
    final int fileIndex = 5850 + this.musicIndex * 5;
    unloadSoundFile(8);
    loadDrgnDir(0, fileIndex, files -> this.musicFilesLoadedCallback(files, fileIndex, true));
  }

  @Override
  protected void musicFilesLoadedCallback(final List<FileData> files, final int fileIndex, final boolean startSequence) {
    final BackgroundMusic bgm = new BackgroundMusic(files, fileIndex);
    bgm.setVolume(40 / 128.0f);
    AUDIO_THREAD.loadBackgroundMusic(bgm);
    _800bd0f0 = 2;
    AUDIO_THREAD.startSequence();
    musicLoaded_800bd782 = true;
  }
}
