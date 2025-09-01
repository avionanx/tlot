package lod.thelegendoftides;

import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import static legend.game.Scus94491BpeSegment.playSound;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;

public class WaitingBiteScreen extends MenuScreen {
  private final Runnable onFishNibbling;
  private final Runnable onFishHooked;
  private final Runnable onFishEscaped;
  private int frames;

  public WaitingBiteScreen(final Runnable onFishNibbling, final Runnable onFishHooked, final Runnable onFishEscaped) {
    this.frames = 100 + (int)(Math.random() * 40);
    this.onFishNibbling = onFishNibbling;
    this.onFishHooked = onFishHooked;
    this.onFishEscaped = onFishEscaped;
  }

  @Override
  protected void render() {
    frames--;

    if(frames == 60) {
      playSound(0x0, 0x25, 0, 0);
      this.onFishNibbling.run();
    } else if(frames == 0) {
      playSound(0x0, 0x28, 0, 0);
      this.deferAction(this::failed);
    }
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull InputAction action, boolean repeat) {
    if(action != INPUT_ACTION_MENU_CONFIRM.get() && !repeat) {
      return InputPropagation.PROPAGATE;
    }

    if(frames <= 60) {
      playSound(0x0, 0x26, 0, 0);
      this.deferAction(this::succeeded);
    } else {
      playSound(0x0, 0x28, 0, 0);
      this.deferAction(this::failed);
    }

    return InputPropagation.HANDLED;
  }

  private void succeeded() {
    this.getStack().popScreen();
    this.onFishHooked.run();
  }

  private void failed() {
    this.getStack().popScreen();
    this.onFishEscaped.run();
  }
}
