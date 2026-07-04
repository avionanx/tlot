package lod.thelegendoftides.screens;

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
import lod.thelegendoftides.TlotFish;
import lod.thelegendoftides.TlotFishingHolePrerequisites;
import org.jetbrains.annotations.NotNull;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.DEFAULT_FONT;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.renderMenuCentredText;
import static legend.game.Text.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_DOWN;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_LEFT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_RIGHT;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_UP;
import static legend.game.sound.Audio.playMenuSound;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.FISHING_HOLE_REGISTRY;
import static lod.thelegendoftides.Tlot.FISH_BAIT_WEIGHT_REGISTRY;
import static lod.thelegendoftides.Tlot.FISH_REGISTRY;

import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;

public class FishRecordsScreen extends MenuScreen {
  private final Runnable notifyDeallocation;
  private boolean areDetailsActive;
  private boolean isSelecting;
  private int currentSelectionIndex;
  private final List<Fish> registryIds = new ArrayList<>();
  private final Set<RegistryId> seen;
  private int currentPage;
  private final FontOptions pageFontOptsLeft = new FontOptions().horizontalAlign(HorizontalAlign.LEFT).colour(TextColour.BROWN).size(0.5f);
  private final FontOptions pageFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.BROWN).size(0.5f);
  private final FontOptions fishTitleFontOptsBig = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.BROWN).size(0.8f);
  private final FontOptions fishDescriptionFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.LEFT).colour(TextColour.BROWN).size(0.5f);
  private final FontOptions fishTitleFontOpts = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.BROWN).size(0.5f);
  private final FontOptions fishTitleFontOptsCurrentSelection = new FontOptions().horizontalAlign(HorizontalAlign.CENTRE).colour(TextColour.RED).size(0.6f);

  public FishRecordsScreen(final Runnable notifyDeallocation, final Boolean isFishScreen) {
    this.notifyDeallocation = notifyDeallocation;
    this.seen = CONFIG.getConfig(Tlot.SEEN_FISH_CONFIG.get());

    for(final RegistryId id : FISH_REGISTRY) {
      final Fish fish = FISH_REGISTRY.getEntry(id).get();

      if(fish.isHidden ^ isFishScreen && fish != TlotFish.COMMON_TRASH.get()) {
        this.registryIds.add(fish);
      }
    }
  }

  protected void unload() {
    this.getStack().popScreen();
    this.notifyDeallocation.run();
  }

  @Override
  protected void render() {
    this.renderPage(this.currentPage * 2, RENDERER.getNativeWidth() / 4.0f * RENDERER.getNativeAspectRatio() + 5);

    // if(this.registryIds.size() - (this.currentPage * 2 + 1) * 4 > 0) {
      this.renderPage(this.currentPage * 2 + 1, RENDERER.getNativeWidth() / 2.0f * RENDERER.getNativeAspectRatio() - 3);
    // }
  }

  private void renderPage(final int pageIndex, final float x) {
    if(this.areDetailsActive && ((pageIndex & 0b1) != this.currentSelectionIndex / 4)) {
      final int registryIdIndex = (pageIndex & ~0b1) * 4 + this.currentSelectionIndex;
      final Fish fish = this.registryIds.get(registryIdIndex);
      this.renderFishDetails(fish, x);
      return;
    }

    for(int pageEntryIndex = 0; pageEntryIndex < 4; pageEntryIndex++) {
      final int registryIdIndex = pageIndex * 4 + pageEntryIndex;
      // OOB Check
      if(this.registryIds.size() <= registryIdIndex) {
        break;
      }
      final Fish fish = this.registryIds.get(registryIdIndex);
      final float entryX = this.getRecordEntryX(registryIdIndex % 8);
      final float entryY = this.getRecordEntryY(registryIdIndex % 8);

      this.renderFishName(fish, entryX, entryY, registryIdIndex);
      this.renderFishIcon(fish, entryX, entryY);
    }
    // this.renderFishInfo(fish, x);

    // Do not render page number of blank page
    if(this.registryIds.size() - pageIndex * 4 > 0) {
      renderText(String.valueOf(pageIndex + 1), x, 180, this.pageFontOpts);
    }
  }

  private void renderFishName(final Fish fish, final float x, final float y, final int registryIdIndex) {
    final String name = this.seen.contains(fish.getRegistryId()) ? I18n.translate(fish) : I18n.translate("thelegendoftides.fish_obfuscated");
    if((this.isSelecting || this.areDetailsActive) && registryIdIndex == this.currentSelectionIndex + this.currentPage * 8) {
      renderText(name, x, y + 30.0f, this.fishTitleFontOptsCurrentSelection);
    } else {
      renderText(name, x, y + 30.0f, this.fishTitleFontOpts);
    }
  }

  private void renderFishIcon(final Fish fish, final float x, final float y) {
    final Renderable58 icon = fish.icon.render((int)x, (int)y, FLAG_DELETE_AFTER_RENDER);
    icon.z_3c = 2.5f;
    icon.widthScale = 2.5f;
    icon.heightScale_38 = 2.5f;

    if(!this.seen.contains(fish.getRegistryId())) {
      icon.colour.zero();
    }
  }

  private void renderFishDetails(final Fish fish, final float x) {
    renderText(I18n.translate(fish), x, 60, this.fishTitleFontOptsBig);

    final Renderable58 icon = fish.icon.render((int)x, 90, FLAG_DELETE_AFTER_RENDER);
    icon.z_3c = 2.5f;
    icon.widthScale = 3.0f;
    icon.heightScale_38 = 3.0f;

    renderText(I18n.translate(fish.getTranslationKey("description")), x - 50, 130, this.fishDescriptionFontOpts);
  }

  private float getRecordEntryX(final int visualIndex) {
    float xPosition = RENDERER.getNativeWidth() / 4.0f + 10.0f;
    if((visualIndex & 1) == 1) {
      xPosition += 48.0f;
    }
    if(visualIndex > 3) {
      xPosition += RENDERER.getNativeWidth() / 4.0f + 25.0f;
    }
    return xPosition;
  }

  private float getRecordEntryY(final int visualIndex) {
    if(visualIndex % 4 < 2) {
      return 70.0f;
    } else {
      return 135.0f;
    }
  }

  @Override
  protected InputPropagation inputActionPressed(@NotNull final InputAction action, final boolean repeat) {
    if(this.areDetailsActive) {
      if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
        playMenuSound(3);
        this.areDetailsActive = false;
        this.isSelecting = true;
      }
    } else if(this.isSelecting) {
      if(action == INPUT_ACTION_MENU_CONFIRM.get() && !repeat && this.seen.contains(this.registryIds.get(this.currentSelectionIndex + this.currentPage * 8).getRegistryId())) {
        playMenuSound(2);
        this.areDetailsActive = true;
      } else if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
        playMenuSound(3);
        this.isSelecting = false;
        this.currentSelectionIndex = 0;
      } else if(action == INPUT_ACTION_MENU_RIGHT.get()) {
        final int newSelectionIndex = switch(this.currentSelectionIndex) {
          case 1 -> 4;
          case 3 -> 6;
          case 5 -> 0;
          case 7 -> 2;
          default -> this.currentSelectionIndex + 1;
        };
        if(newSelectionIndex + this.currentPage * 8 < this.registryIds.size()) {
          playMenuSound(1);
          this.currentSelectionIndex = newSelectionIndex;
        }
      } else if(action == INPUT_ACTION_MENU_LEFT.get()) {
        final int newSelectionIndex = switch(this.currentSelectionIndex) {
          case 4 -> 1;
          case 6 -> 3;
          case 0 -> 5;
          case 2 -> 7;
          default -> this.currentSelectionIndex - 1;
        };
        if(newSelectionIndex + this.currentPage * 8 < this.registryIds.size()) {
          playMenuSound(1);
          this.currentSelectionIndex = newSelectionIndex;
        }
      } else if(action == INPUT_ACTION_MENU_DOWN.get() || action == INPUT_ACTION_MENU_UP.get()) {
        final int newSelectionIndex = this.currentSelectionIndex ^ (1 << 1);
        if(newSelectionIndex + this.currentPage * 8 < this.registryIds.size()) {
          playMenuSound(1);
          this.currentSelectionIndex = newSelectionIndex;
        }
    }
    } else {
      if(action == INPUT_ACTION_MENU_CONFIRM.get() && !repeat) {
        playMenuSound(2);
        this.isSelecting = true;
      } else if(action == INPUT_ACTION_MENU_BACK.get() && !repeat) {
        playMenuSound(3);
        this.deferAction(this::unload);
      } else if(action == INPUT_ACTION_MENU_RIGHT.get()) {
        playMenuSound(1);
        this.currentPage = Math.clamp(this.currentPage + 1, 0, Math.floorDiv(this.registryIds.size() - 1, 8));
      } else if(action == INPUT_ACTION_MENU_LEFT.get()) {
        playMenuSound(1);
        this.currentPage = Math.clamp(this.currentPage - 1, 0, Math.floorDiv(this.registryIds.size() - 1, 8));
      }
    }

    // These aren't really needed anymore, cuz 4 fish per page
    /*
    else if(action == INPUT_ACTION_MENU_UP.get()) {
      this.currentPage = Math.clamp(this.currentPage + 6, 0, this.registryIds.size() - 1);
    } else if(action == INPUT_ACTION_MENU_DOWN.get()) {
      this.currentPage = Math.clamp(this.currentPage - 6, 0, this.registryIds.size() - 1);
    }
    */
    return InputPropagation.HANDLED;
  }

  // Want to show book BG
  @Override
  protected boolean propagateRender() {
    return true;
  }
}
