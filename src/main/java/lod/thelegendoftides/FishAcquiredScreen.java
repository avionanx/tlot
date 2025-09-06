package lod.thelegendoftides;

import legend.core.platform.Window;
import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.Renderable58;
import org.jetbrains.annotations.NotNull;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {

  private int extraWidth;
  private UiBox contentBox;
  private final Fish fish;
  private final Runnable setCaughtScreenUnloaded;

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.extraWidth = (int)getExtraWidth();
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;

    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);

    RENDERER.window().events().onResize(this::onResized);
  }

  @Override
  protected void render() {
    this.contentBox.render();

    final Renderable58 renderable = this.fish.icon.render(160, 110, FLAG_DELETE_AFTER_RENDER);
    renderable.z_3c = 10.0f;
    renderable.widthScale = 6.0f;
    renderable.heightScale_38 = 6.0f;

    renderText((I18n.translate(getTranslationKey("acquired_fish"))), 112.5f, 60, UI_WHITE);
    renderText((I18n.translate(this.fish)), 145.0f, 165, UI_WHITE);
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(action == INPUT_ACTION_MENU_CONFIRM.get() && !repeat) {
      this.deferAction(this::unload);
    }

    return InputPropagation.HANDLED;
  }

  private void onResized(final Window window, final int x, final int y) {
    this.extraWidth = (int)getExtraWidth();

    this.contentBox.delete();

    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);
  }

  public void unload() {
    this.getStack().popScreen();
    this.contentBox.delete();
    RENDERER.window().events().removeOnResize(this::onResized);
    this.setCaughtScreenUnloaded.run();
  }
}
