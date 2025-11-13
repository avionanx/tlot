package lod.thelegendoftides.screens;

import legend.core.platform.Window;
import legend.game.EngineState;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.modding.coremod.CoreMod;
import legend.game.submap.SMap;
import legend.game.types.Renderable58;
import lod.thelegendoftides.Bait;
import lod.thelegendoftides.Fish;
import lod.thelegendoftides.FishingHole;
import lod.thelegendoftides.TlotFishBaitWeights;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.EngineStates.currentEngineState_8004dd04;
import static legend.game.Graphics.displayHeight_1f8003e4;
import static legend.game.Graphics.displayWidth_1f8003e0;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Text.renderText;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishListScreen extends MenuScreen {
  public static final FontOptions UI_GREY = new FontOptions().colour(TextColour.GREY);

  private UiBox headerBox;
  private UiBox contentBox;

  public final FishingHole fishingHole;

  private float fullWidth;
  private int extraWidth;
  private float ratio;
  public boolean isFishListScreenDisabled;

  private Bait selectedBait;

  public FishListScreen(final FishingHole fishingHole) {
    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.fishingHole = fishingHole;

    this.headerBox = new UiBox("Fish List Header", (int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
    this.contentBox = new UiBox("Fish List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, fishingHole.fish.size() * 16);

    RENDERER.events().onResize(this::onResized);
  }

  public void setSelectedBait(final Bait bait) {
    this.selectedBait = bait;
  }

  @Override
  protected void render() {
    if(this.isFishListScreenDisabled) {
      return;
    }

    for(int i = 0; i < this.fishingHole.fish.size(); i++) {
      final FishingHole.FishWeight fishWeight = this.fishingHole.fish.get(i);

      if(fishWeight.visibility == FishingHole.FishVisibility.HIDDEN) {
        continue;
      }

      final Fish fish = fishWeight.fish.get();
      if(fish.isHidden) continue;

      final int x = (int)(this.fullWidth - 101 * this.ratio);
      final int y = i * 16 + 40;

      final Renderable58 icon = fish.icon.render(x, y, FLAG_DELETE_AFTER_RENDER);
      icon.z_3c = 10.0f;

      final String name;

      if(fishWeight.visibility == FishingHole.FishVisibility.VISIBLE) {
        name = I18n.translate(fish);
      } else {
        name = I18n.translate(getTranslationKey("fish_obfuscated"));
      }

      if(currentEngineState_8004dd04 instanceof SMap || TlotFishBaitWeights.getBaitWeightForFish(fish, this.selectedBait) != 0) {
        renderText(name, x + 9.0f, y + 1.5f, UI_WHITE);
      } else {
        renderText(name, x + 9.0f, y + 1.5f, UI_GREY);
        icon.colour.set(0.5f);
      }
    }

    this.headerBox.render();
    this.contentBox.render();
    renderText((I18n.translate(getTranslationKey("fish_list"))), this.fullWidth - 95.0f * this.ratio, 20.0f, UI_WHITE);
  }

  private void updateDimensions() {
    final boolean widescreen = RENDERER.getRenderMode() == EngineState.RenderMode.PERSPECTIVE && CONFIG.getConfig(CoreMod.ALLOW_WIDESCREEN_CONFIG.get());
    if(widescreen) {
      this.ratio = (float)RENDERER.getRenderWidth() / RENDERER.getRenderHeight();
      this.fullWidth = Math.max(RENDERER.getNativeWidth(), this.ratio * displayHeight_1f8003e4);
    } else {
      this.ratio = 1;
      this.fullWidth = displayWidth_1f8003e0 - 50;
    }
  }

  public void onResized(final Window window, final int x, final int y) {
    this.headerBox.delete();
    this.contentBox.delete();

    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.headerBox = new UiBox("Bait List Header", (int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
    this.contentBox = new UiBox("Bait List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fishingHole.fish.size() * 16);
  }

  public void unload() {
    this.headerBox.delete();
    this.contentBox.delete();

    RENDERER.events().removeOnResize(this::onResized);
  }
}
