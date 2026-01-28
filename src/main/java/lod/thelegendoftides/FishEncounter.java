package lod.thelegendoftides;

import legend.game.combat.Battle;
import legend.game.combat.encounters.Encounter;
import legend.lodmod.LodPostBattleActions;

import static legend.game.Audio.playVictoryMusic;

public class FishEncounter extends Encounter {
  public FishEncounter(final int musicIndex, final int postCombatSubmapCut, final int postCombatSubmapScene, final Monster... monsters) {
    super(musicIndex, 0, 0, 0, 0, 0, 0, 0, postCombatSubmapCut, postCombatSubmapScene, monsters);
  }

  @Override
  public void onBattleWon(final Battle battle) {
    battle.postBattleAction_800bc974 = LodPostBattleActions.MERCHANT.get().inst();
    playVictoryMusic();
  }
}
