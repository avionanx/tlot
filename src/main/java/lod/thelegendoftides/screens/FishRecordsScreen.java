package lod.thelegendoftides.screens;

import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;

public class FishRecordsScreen extends MenuScreen {
  private final Runnable notifyDeallocation;

  public FishRecordsScreen(Runnable notifyDeallocation) {
    this.notifyDeallocation = notifyDeallocation;
  }

  protected void unload() {
    this.getStack().popScreen();
    this.notifyDeallocation.run();
  }

  @Override
  protected void render() {

  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
      this.deferAction(this::unload);
    }
    return InputPropagation.HANDLED;
  }

  // Want to show book BG
  @Override
  protected boolean propagateRender() {
    return true;
  }
}
