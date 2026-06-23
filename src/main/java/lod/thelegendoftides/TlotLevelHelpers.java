package lod.thelegendoftides;

import java.util.Arrays;

import static legend.core.GameEngine.CONFIG;
import static lod.thelegendoftides.Tlot.TLOT_XP;

public class TlotLevelHelpers {
  public static int[] TLOT_XP_TO_LEVEL = {0, 20, 60, 150, 300, 500, 800, 1200, 1600, 2000, 2500, 3200};

  // LV1 Initial
  // LV2 -> Bait window 1.5 > 2.25
  // LV3 -> Progress start 30% -> 35%
  // LV4 -> Loss reduced 12% -> 10.5%
  // LV5 -> Bait window 2.25 -> 3.0
  // LV6 -> Fade removed
  // LV7 -> Progress increase -> 2.0 -> 2.5
  // LV8 -> Progress start 35% -> 40%
  // LV9 -> Loss reduced 10.5% -> 9%
  // LV10 -> Chain increase 3 -> 4
  // LV11 -> Progress increase  2.5 -> 3.0
  // LV12 -> Loss reduced 9% -> 7.5%

  public static int TLOT_WAITING_WINDOW() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 5) return 30;
    else if(level >= 2) return 45;
    else return 60;
  }

  public static float TLOT_GET_PERCENTAGE_LOSS() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 12) return 0.075f;
    else if(level >= 9) return 0.09f;
    else if(level >= 4) return 0.105f;
    else return 0.12f;
  }

  public static float TLOT_GET_FADE() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 6) return 0.0f;
    else return 1.0f;
  }

  public static float TLOT_GET_INITIAL_PROGRESS() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 8) return 0.4f;
    else if(level >= 3) return 0.35f;
    else return 0.3f;
  }

  public static float TLOT_GET_PROGRESS_INCREMENT() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 11) return 3.0f;
    else if(level >= 7) return 2.5f;
    else return 2.0f;
  }

  public static int TLOT_GET_MAX_CHAINS() {
    final int level = TLOT_GET_LEVEL();
    if(level >= 10) return 4;
    else return 3;
  }

  public static int TLOT_GET_LEVEL() {
    return (int)Arrays.stream(TLOT_XP_TO_LEVEL).filter(xp -> CONFIG.getConfig(TLOT_XP.get()) >= xp).count();
  }

  public static int TLOT_GET_MAX_LEVEL() {
    return TLOT_XP_TO_LEVEL.length;
  }

  public static float TLOT_GET_LEVEL_PROGRESS() {
    final int level = TLOT_GET_LEVEL();
    if(level == TLOT_GET_MAX_LEVEL()) return 1.0f;

    return (float)(CONFIG.getConfig(TLOT_XP.get()) - TLOT_XP_TO_LEVEL[level - 1]) / (TLOT_XP_TO_LEVEL[level] - TLOT_XP_TO_LEVEL[level - 1]);
  }
}
