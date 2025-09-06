package lod.thelegendoftides;

import legend.core.platform.Window;
import legend.game.EngineState;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.MenuScreen;
import legend.game.modding.coremod.CoreMod;

import java.util.ArrayList;
import java.util.List;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment.displayHeight_1f8003e4;
import static legend.game.Scus94491BpeSegment.displayWidth_1f8003e0;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishListScreen extends MenuScreen {

  private final List<Fish> fish = new ArrayList<>();

  private UiBox headerBox;
  private UiBox contentBox;

  public final FishingHole fishingHole;

  private float fullWidth;
  private int extraWidth;
  private float ratio;
  public boolean isFishListScreenDisabled;

  public FishListScreen(final FishingHole fishingHole) {
    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.fishingHole = fishingHole;

    for(final FishingHole.FishWeight fishWeight : fishingHole.fish) {
      this.fish.add(fishWeight.fish.get());
    }

    this.headerBox = new UiBox("Fish List Header", (int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
    this.contentBox = new UiBox("Fish List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fish.size() * 16);

    RENDERER.events().onResize(this::onResized);
  }

  @Override
  protected void render() {
    if(this.isFishListScreenDisabled) {
      return;
    }

    for(int i = 0; i < this.fish.size(); i++) {
      final Fish fish = this.fish.get(i);
      final int x = (int)(this.fullWidth - 101 * this.ratio);
      final int y = i * 16 + 40;
      fish.icon.render(x, y, FLAG_DELETE_AFTER_RENDER).z_3c = 10.0f;
      renderText(I18n.translate(this.fish.get(i)), x + 9.0f, y + 1.5f, UI_WHITE);
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
    this.contentBox = new UiBox("Bait List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fish.size() * 16);
  }

  public void unload() {
    this.headerBox.delete();
    this.contentBox.delete();

    RENDERER.events().removeOnResize(this::onResized);
  }
}
