package lod.thelegendoftides;

public class TlotLevelHelpers {
  public static int[] TLOT_XP_TO_LEVEL = {20, 60, 150, 300, 500, 800, 1200, 1600, 2000, 2500};

  // LV1 -> Bait window 1.0 > 1.8
  // LV2 -> Progress start 30% -> 35%
  // LV3 -> Loss reduced 10% -> 8.5%
  // LV4 -> Bait window 1.8 -> 3.0
  // LV2 -> Fade removed
  // LV6 -> Progress increase -> 2.0 -> 2.5
  // LV7 -> Progress start 35% -> 40%
  // LV8 -> Loss reduced 8.5% -> 7%
  // LV9 -> Chain increase 3 -> 4
  // LV10 -> Progress increase  2.5 -> 3.0
  
  public static int TLOT_WAITING_WINDOW() {
    int level = 1;
    return level * 60;
  }
}
