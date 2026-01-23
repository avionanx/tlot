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
import lod.thelegendoftides.FishBaitWeight;
import lod.thelegendoftides.FishingHole;
import lod.thelegendoftides.Tlot;
import lod.thelegendoftides.TlotFishingHolePrerequisites;
import org.jetbrains.annotations.NotNull;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.DEFAULT_FONT;
import static legend.core.GameEngine.RENDERER;
import static legend.core.GameEngine.SCRIPTS;
import static legend.game.SItem.renderMenuCentredText;
import static legend.game.Text.renderText;
import static legend.game.Text.textZ_800bdf00;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_DOWN;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_LEFT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_RIGHT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_UP;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.FISHING_HOLE_REGISTRY;
import static lod.thelegendoftides.Tlot.FISH_BAIT_WEIGHT_REGISTRY;
import static lod.thelegendoftides.Tlot.FISH_REGISTRY;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishBookScreen extends MenuScreen {

  private final FontOptions menuTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.WHITE).shadowColour(TextColour.BLACK);
  private final FontOptions fishTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.BROWN).size(0.75f);
  private final FontOptions pageFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.BROWN).size(0.5f);
  private final FontOptions pageFontOptsLeft = new FontOptions().horizontalAlign(HorizontalAlign.LEFT).colour(TextColour.BROWN).size(0.5f);
  private final Texture bookTexture;
  private final MeshObj bookQuad;
  private final MV bookTransforms;

  private int currentPage; // zero indexed pages in my book?! well, and 1
  private final List<Fish> registryIds = new ArrayList<>();
  private final Set<RegistryId> seen;

  public FishBookScreen() {
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
    this.bookTransforms.transfer.set(RENDERER.getNativeWidth() / 2.0f, RENDERER.getNativeHeight() / 2.0f, 11.0f);

    this.seen = CONFIG.getConfig(Tlot.SEEN_FISH_CONFIG.get());

    for(final RegistryId id : FISH_REGISTRY) {
      final Fish fish = FISH_REGISTRY.getEntry(id).get();

      if(!fish.isHidden) {
        this.registryIds.add(fish);
      }
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

    this.renderPage(this.currentPage, RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio() + 5);

    if(this.registryIds.size() - this.currentPage != 1) {
      this.renderPage(this.currentPage + 1, RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio() - 3);
    }

    RENDERER.queueOrthoModel(this.bookQuad, this.bookTransforms, QueuedModelStandard.class)
      .texture(this.bookTexture)
      .useTextureAlpha();
  }

  private void renderPage(final int pageIndex, final float x) {
    final Fish fish = this.registryIds.get(pageIndex);

    this.renderFishName(fish, x);
    this.renderFishIcon(fish, x);
    this.renderFishInfo(fish, x);

    final int oldZ = textZ_800bdf00;
    textZ_800bdf00 = 2;
    renderText(String.valueOf(pageIndex + 1), x, 180, this.pageFontOpts);
    textZ_800bdf00 = oldZ;
  }

  private void renderFishName(final Fish fish, final float x) {
    final String name = this.seen.contains(fish.getRegistryId()) ? I18n.translate(fish) : I18n.translate("thelegendoftides.fish_obfuscated");
    final int oldZ = textZ_800bdf00;
    textZ_800bdf00 = 2;
    renderText(name, x, 56, this.fishTitleFontOpts);
    textZ_800bdf00 = oldZ;
  }

  private void renderFishIcon(final Fish fish, final float x) {
    final Renderable58 icon = fish.icon.render((int)x, 85, FLAG_DELETE_AFTER_RENDER);
    icon.z_3c = 2.5f;
    icon.widthScale = 3.5f;
    icon.heightScale_38 = 3.5f;

    if(!this.seen.contains(fish.getRegistryId())) {
      icon.colour.zero();
    }
  }

  private void renderFishInfo(final Fish fish, final float x) {
    final boolean seen = this.seen.contains(fish.getRegistryId());

    final String text;
    if(seen) {
      text = I18n.translate(fish.getTranslationKey("description"));
    } else {
      final String hint = I18n.translate(fish.getTranslationKey("hint"));

      if(!hint.isBlank()) {
        text = hint;
      } else {
        text = I18n.translate("thelegendoftides.fish_obfuscated");
      }
    }

    final int oldZ = textZ_800bdf00;
    textZ_800bdf00 = 2;

    renderText(text, x, 124, this.pageFontOpts);

    if(seen) {
      final List<String> locations = new ArrayList<>();
      for(final RegistryId holeId : FISHING_HOLE_REGISTRY) {
        final FishingHole hole = FISHING_HOLE_REGISTRY.getEntry(holeId).get();

        // Don't display azeel info
        if(hole.prerequisities != TlotFishingHolePrerequisites.NONE) continue;

        for(int fishIndex = 0; fishIndex < hole.fish.size(); fishIndex++) {
          if(hole.fish.get(fishIndex).fish.get() == fish) {
            locations.add(I18n.translate(hole));
            break;
          }
        }
      }

      final List<String> baits = new ArrayList<>();
      for(final RegistryId holeId : FISH_BAIT_WEIGHT_REGISTRY) {
        final FishBaitWeight bait = FISH_BAIT_WEIGHT_REGISTRY.getEntry(holeId).get();

        if(bait.fish.get() == fish) {
          baits.add(I18n.translate(bait.bait.get()));
          break;
        }
      }

      final float locationHeight = renderMenuCentredText(DEFAULT_FONT, I18n.translate("thelegendoftides.locations", String.join(", ", locations)), x, 133, 106, this.pageFontOptsLeft);

      if(!baits.isEmpty()) {
        renderMenuCentredText(DEFAULT_FONT, I18n.translate("thelegendoftides.baits", String.join(", ", baits)), x, 136 + locationHeight, 106, this.pageFontOptsLeft);
      }
    }

    textZ_800bdf00 = oldZ;
  }
}
