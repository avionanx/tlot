package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.core.platform.Window;
import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishAcquiredScreen extends MenuScreen {

  private int extraWidth;
  private UiBox contentBox;
  private final Fish fish;
  private final MeshObj bgQuad;
  private final Texture fishTexture;
  private final MV textureTransforms;
  private final Runnable setCaughtScreenUnloaded;

  public FishAcquiredScreen(final Fish fish, final Runnable setCaughtScreenUnloaded) {
    this.fish = fish;
    this.extraWidth = (int)getExtraWidth();
    this.setCaughtScreenUnloaded = setCaughtScreenUnloaded;

    final Path texturePath = Path.of("mods", "tlot", "fish", this.fish.icon);
    this.fishTexture = Texture.png(texturePath);

    this.bgQuad = new QuadBuilder(MOD_ID)
      .uvSize(1.0f, 1.0f)
      .bpp(Bpp.BITS_24)
      .size(1.0f, 1.0f)
      .rgb(1.0f, 1.0f, 1.0f)
      .pos(-0.5f, -0.5f, 0.0f)
      .build();
    this.textureTransforms = new MV();
    this.textureTransforms.scaling(80.0f);
    this.textureTransforms.transfer.set(160.0f, 120.0f, 0.0f);

    this.contentBox = new UiBox("Fish Capture Screen BG", 80, 60, 160, 120);

    RENDERER.window().events().onResize(this::onResized);
  }

  @Override
  protected void render() {
    this.contentBox.render();
    RENDERER.queueOrthoModel(this.bgQuad, this.textureTransforms, QueuedModelStandard.class).texture(this.fishTexture);
    renderText((I18n.translate(getTranslationKey("acquired_fish"))), 112.5f, 60, UI_WHITE);
    renderText((I18n.translate(this.fish)), 145.0f, 165, UI_WHITE);
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull InputAction action, boolean repeat) {
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
    this.fishTexture.delete();
    this.bgQuad.delete();
    RENDERER.window().events().removeOnResize(this::onResized);
    this.setCaughtScreenUnloaded.run();
  }
}
