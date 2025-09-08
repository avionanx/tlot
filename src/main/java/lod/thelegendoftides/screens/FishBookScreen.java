package lod.thelegendoftides.screens;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.core.platform.input.InputAction;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.HorizontalAlign;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishBookScreen extends MenuScreen {

  private final FontOptions menuTitleFontOpts;
  private final Texture bookTexture;
  private final MeshObj bookQuad;
  private final MV bookTransforms;

  public FishBookScreen() {
    this.menuTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK);

    this.bookTexture = Texture.png(Path.of("mods", "tlot", "book.png"));
    this.bookQuad = new QuadBuilder(MOD_ID)
      .uvSize(1.0f,1.0f)
      .bpp(Bpp.BITS_24)
      .size(1.0f,1.0f)
      .pos(-0.5f,-0.5f,0.0f)
      .rgb(1.0f, 1.0f, 1.0f)
      .build();

    this.bookTransforms = new MV();
    this.bookTransforms.scaling(180.0f * 1.55f, 180.0f, 1.0f);
    this.bookTransforms.transfer.set(RENDERER.getNativeWidth() / 2.0f, RENDERER.getNativeHeight() / 2.0f, 0.0f);
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
      this.deferAction(this::unload);
    }

    return InputPropagation.HANDLED;
  }

  public void unload() {
    this.getStack().popScreen();
    this.bookTexture.delete();
    this.bookQuad.delete();
  }

  @Override
  protected void render() {
    renderText(I18n.translate(getTranslationKey("book_title")), RENDERER.getNativeWidth() / 2.0f, 25, this.menuTitleFontOpts);

    RENDERER.queueOrthoModel(this.bookQuad, this.bookTransforms, QueuedModelStandard.class)
      .texture(this.bookTexture)
      .useTextureAlpha();
  }
}
