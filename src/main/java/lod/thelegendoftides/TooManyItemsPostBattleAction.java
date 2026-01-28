package lod.thelegendoftides;

import legend.game.EngineState;
import legend.game.combat.Battle;
import legend.game.combat.postbattleactions.PostBattleAction;
import legend.game.inventory.WhichMenu;
import legend.game.inventory.screens.TooManyItemsScreen;
import legend.game.scripting.RunningScript;

import static legend.game.Graphics.renderMode;
import static legend.game.Graphics.resizeDisplay;
import static legend.game.Menus.whichMenu_800bdc38;
import static legend.game.SItem.menuStack;

public class TooManyItemsPostBattleAction extends PostBattleAction<TooManyItemsPostBattleActionInstance, TooManyItemsPostBattleAction> {
  @Override
  protected int getTotalDuration(final Battle battle, final TooManyItemsPostBattleActionInstance inst) {
    return 15;
  }

  @Override
  protected int getFadeDuration(final Battle battle, final TooManyItemsPostBattleActionInstance inst) {
    return 30;
  }

  @Override
  protected void onCameraFadeoutStart(final Battle battle, final TooManyItemsPostBattleActionInstance inst) {

  }

  @Override
  protected void onCameraFadeoutFinish(final Battle battle, final TooManyItemsPostBattleActionInstance inst) {

  }

  @Override
  protected void performAction(final Battle battle, final TooManyItemsPostBattleActionInstance inst) {
    resizeDisplay(384, 240);
    renderMode = EngineState.RenderMode.LEGACY;
    whichMenu_800bdc38 = WhichMenu.RENDER_NEW_MENU;
    menuStack.pushScreen(new TooManyItemsScreen());
  }

  public TooManyItemsPostBattleActionInstance inst() {
    return new TooManyItemsPostBattleActionInstance(this);
  }

  @Override
  public TooManyItemsPostBattleActionInstance inst(final RunningScript<?> script) {
    return this.inst();
  }
}
