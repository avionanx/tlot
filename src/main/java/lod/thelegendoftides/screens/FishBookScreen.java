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
import legend.game.types.Renderable58;
import lod.thelegendoftides.Fish;
import org.jetbrains.annotations.NotNull;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.RENDERER;
import static legend.core.GameEngine.SCRIPTS;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_DOWN;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_LEFT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_RIGHT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_UP;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.FISH_REGISTRY;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishBookScreen extends MenuScreen {

  private final FontOptions menuTitleFontOpts;
  private final FontOptions pageFontOpts;
  private final FontOptions fishTitleFontOpts;
  private final Texture bookTexture;
  private final MeshObj bookQuad;
  private final MV bookTransforms;

  private int currentPage = 0; // zero indexed pages in my book?! well, and 1
  private final ArrayList<RegistryId> registryIds = new ArrayList<>();


  public FishBookScreen() {
    this.menuTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK);
    this.pageFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK).size(0.8f);
    this.fishTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK).size(0.75f);

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
    this.bookTransforms.transfer.set(RENDERER.getNativeWidth() / 2.0f, RENDERER.getNativeHeight() / 2.0f, 60.0f);

    for(final RegistryId id : FISH_REGISTRY) {
      if(FISH_REGISTRY.getEntry(id).get().isHidden) continue;

      this.registryIds.add(id);
    }

  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
      this.deferAction(this::unload);
    } else if(action == INPUT_ACTION_MENU_RIGHT.get()) {
      this.currentPage = Math.clamp(this.currentPage + 2, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_LEFT.get()) {
      this.currentPage = Math.clamp(this.currentPage - 2, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_UP.get()) {
      this.currentPage = Math.clamp(this.currentPage + 6, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_DOWN.get()) {
      this.currentPage = Math.clamp(this.currentPage - 6, 0, this.registryIds.size() - 1);
    }

    return InputPropagation.HANDLED;
  }

  public void unload() {
    this.getStack().popScreen();
    this.bookTexture.delete();
    this.bookQuad.delete();

    SCRIPTS.resume();
  }

  @Override
  protected void render() {
    renderText(I18n.translate(getTranslationKey("book_title")), RENDERER.getNativeWidth() / 2.0f, 25, this.menuTitleFontOpts);

    renderText(String.valueOf(this.currentPage + 1), RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio(), 180, this.pageFontOpts);

    final Fish fish = FISH_REGISTRY.getEntry(this.registryIds.get(this.currentPage)).get();
    renderText(I18n.translate(fish), RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio(), 60, this.fishTitleFontOpts);

    final Renderable58 icon1 = fish.icon.render((int)(RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio()), 120, FLAG_DELETE_AFTER_RENDER);
    icon1.z_3c = 10.0f;
    icon1.widthScale = 4.0f;
    icon1.heightScale_38 = 4.0f;

    if(this.registryIds.size() - this.currentPage != 1) {
      final Fish fish2 = FISH_REGISTRY.getEntry(this.registryIds.get(this.currentPage + 1)).get();
      renderText(I18n.translate(fish2), RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio(), 60, this.fishTitleFontOpts);

      final Renderable58 icon2 = fish2.icon.render((int)(RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio()), 120, FLAG_DELETE_AFTER_RENDER);
      icon2.z_3c = 10.0f;
      icon2.widthScale = 4.0f;
      icon2.heightScale_38 = 4.0f;

      renderText(String.valueOf(this.currentPage + 2), RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio(), 180, this.pageFontOpts);
    }
    RENDERER.queueOrthoModel(this.bookQuad, this.bookTransforms, QueuedModelStandard.class)
      .texture(this.bookTexture)
      .useTextureAlpha();
  }
}
