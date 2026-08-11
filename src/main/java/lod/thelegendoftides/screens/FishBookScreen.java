package lod.thelegendoftides.screens;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.lang.I18nText;
import legend.core.lang.TextComponent;
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
import legend.game.inventory.screens.controls.Button;
import lod.thelegendoftides.Fish;
import lod.thelegendoftides.TlotLevelHelpers;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.core.GameEngine.SCRIPTS;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Text.renderText;
import static legend.game.Text.textZ_800bdf00;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_LEFT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_RIGHT;
import static legend.game.sound.Audio.playMenuSound;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.TLOT_NUM_FISH_CAUGHT;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishBookScreen extends MenuScreen {

  private final FontOptions menuTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK);
  private final FontOptions recordFontOptsLeft = new FontOptions().horizontalAlign(HorizontalAlign.LEFT).colour(TextColour.BLACK).size(0.75f);
  private final FontOptions recordFontOptsRight = new FontOptions().horizontalAlign(HorizontalAlign.RIGHT).colour(TextColour.BLACK).size(0.75f);
  private final Texture bookTexture;
  private final MV bookTransforms;

  private final List<Fish> registryIds = new ArrayList<>();


  private int extraWidth;
  private final MeshObj bookQuad;
  private boolean childScreenAllocated = false;
  private final List<Button> menuButtons = new ArrayList<>();

  public FishBookScreen() {
    this.bookTexture = Texture.png("Book", Path.of("mods", "tlot", "book.png"));
    this.bookQuad = new QuadBuilder(MOD_ID)
      .uvSize(1.0f,1.0f)
      .bpp(Bpp.BITS_24)
      .size(1.0f,1.0f)
      .pos(-0.5f,-0.5f,0.0f)
      .rgb(1.0f, 1.0f, 1.0f)
      .build();

    this.bookTransforms = new MV();
    this.bookTransforms.scaling(180.0f * 1.55f, 180.0f, 1.0f);
    this.bookTransforms.transfer.set(RENDERER.getNativeWidth() / 2.0f, RENDERER.getNativeHeight() / 2.0f, 11.0f);

    final Button fishRecordsButton = this.addButton(new I18nText("thelegendoftides.fish"), () -> {
      this.childScreenAllocated = true;
      this.getStack().pushScreen(new FishRecordsScreen(() -> this.childScreenAllocated = false, true));
    });
    fishRecordsButton.onGotFocus(() -> fishRecordsButton.setTextColour(TextColour.WHITE));

    final Button treasureRecordsButton = this.addButton(new I18nText("thelegendoftides.treasures"), () -> {
      this.childScreenAllocated = true;
      this.getStack().pushScreen(new FishRecordsScreen(() -> this.childScreenAllocated = false, false));
    });
    treasureRecordsButton.onGotFocus(() -> treasureRecordsButton.setTextColour(TextColour.WHITE));


    this.setFocus(this.menuButtons.getFirst());
    /*
      for(final RegistryId id : FISH_REGISTRY) {
        final Fish fish = FISH_REGISTRY.getEntry(id).get();

        if(!fish.isHidden) {
          this.registryIds.add(fish);
        }
      }
     */
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
      this.deferAction(this::unload);
    }/* else if(action == INPUT_ACTION_MENU_RIGHT.get()) {
      this.currentPage = Math.clamp(this.currentPage + 2, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_LEFT.get()) {
      this.currentPage = Math.clamp(this.currentPage - 2, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_UP.get()) {
      this.currentPage = Math.clamp(this.currentPage + 6, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_DOWN.get()) {
      this.currentPage = Math.clamp(this.currentPage - 6, 0, this.registryIds.size() - 1);
    }*/
    return super.inputActionPressed(action, repeat);
  }

  public void unload() {
    this.getStack().popScreen();
    this.bookQuad.delete();
    this.bookTexture.delete();
    playMenuSound(3);
    gameState_800babc8.indicatorsDisabled_4e3 = false;
    SCRIPTS.resume();
  }

  @Override
  protected void render() {
    final int oldZ = textZ_800bdf00;
    textZ_800bdf00 = 2;
    this.renderBackground();
    renderText(I18n.translate(getTranslationKey("book_title")), RENDERER.getNativeWidth() / 2.0f, 25, this.menuTitleFontOpts);
    if(this.childScreenAllocated) return;

    renderText(I18n.translate(getTranslationKey("total_fish_caught")), RENDERER.getNativeWidth() / 2.0f - 100, 55, this.recordFontOptsLeft);
    renderText(CONFIG.getConfig(TLOT_NUM_FISH_CAUGHT.get()).toString(), RENDERER.getNativeWidth() / 2.0f - 10, 55, this.recordFontOptsRight);
    // this.renderPage(this.currentPage, RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio() + 5);

    // if(this.registryIds.size() - this.currentPage != 1) {
    //   this.renderPage(this.currentPage + 1, RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio() - 3);
    // }
    //XP and levels and stuff
    final MV xpStuffMV = new MV();
    xpStuffMV.scaling(90.0f, 4.0f, 1.0f);
    xpStuffMV.transfer.set(RENDERER.getNativeWidth() / 2.0f - 100.0f, 180.0f, 11.0f);

    // TODO render max level
    RENDERER.queueOrthoModel(RENDERER.opaqueQuad, xpStuffMV, QueuedModelStandard.class).colour(0.0f, 0.0f, 0.0f);
    xpStuffMV.scaling(88.0f * TlotLevelHelpers.TLOT_GET_LEVEL_PROGRESS(), 2.0f, 1.0f);
    xpStuffMV.transfer.set(RENDERER.getNativeWidth() / 2.0f - 99.0f, 181.0f, 11.0f);
    RENDERER.queueOrthoModel(RENDERER.opaqueQuad, xpStuffMV, QueuedModelStandard.class).colour(0.4f, 0.5f, 0.8f);

    final int currentLevel = TlotLevelHelpers.TLOT_GET_LEVEL();
    final int maxLevel = TlotLevelHelpers.TLOT_GET_MAX_LEVEL();

    if(currentLevel == maxLevel) {
      renderText(I18n.translate(getTranslationKey("level_max"), maxLevel), RENDERER.getNativeWidth() / 2.0f - 10, 170, this.recordFontOptsRight);
    } else {
      renderText(I18n.translate(getTranslationKey("level"), currentLevel), RENDERER.getNativeWidth() / 2.0f - 100, 170, this.recordFontOptsLeft);
    }

    textZ_800bdf00 = oldZ;
  }

  private Button addButton(final TextComponent text, final Runnable onClick) {
    final int index = this.menuButtons.size();
    final Button button = this.addControl(new Button(text));

    button.setPos(85 + index * 115 - this.extraWidth / 2, 205);
    button.setZ(1);
    button.setWidth(80);
    button.onHoverIn(() -> {
      playMenuSound(1);
      this.setFocus(button);
    });
    button.setTextColour(TextColour.GREY);
    button.onLostFocus(() -> button.setTextColour(TextColour.GREY));

    button.onPressed(onClick::run);
    this.menuButtons.add(button);
    button.onInputActionPressed((action, repeat) -> {
      if(action == INPUT_ACTION_MENU_RIGHT.get()) {
        for(int i = 1; i < this.menuButtons.size(); i++) {
          final Button otherButton = this.menuButtons.get(Math.floorMod(index + i, this.menuButtons.size()));

          if(!otherButton.isDisabled() && otherButton.isVisible()) {
            playMenuSound(1);
            this.setFocus(otherButton);
            break;
          }
        }
        return InputPropagation.HANDLED;
      } else if(action == INPUT_ACTION_MENU_LEFT.get()) {
        for(int i = 1; i < this.menuButtons.size(); i++) {
          final Button otherButton = this.menuButtons.get(Math.floorMod(index - i, this.menuButtons.size()));

          if(!otherButton.isDisabled() && otherButton.isVisible()) {
            playMenuSound(1);
            this.setFocus(otherButton);
            break;
          }
        }
        return InputPropagation.HANDLED;
      }
      return InputPropagation.PROPAGATE;
    });

    return button;
  }

  private void renderBackground() {
    RENDERER.queueOrthoModel(this.bookQuad, this.bookTransforms, QueuedModelStandard.class)
      .texture(this.bookTexture)
      .useTextureAlpha();
  }

  @Override
  protected boolean propagateRender() {
    return true;
  }
}
