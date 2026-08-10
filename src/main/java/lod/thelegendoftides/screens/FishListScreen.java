package lod.thelegendoftides.screens;

import legend.core.platform.Window;
import legend.game.EngineState;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.modding.coremod.CoreMod;
import legend.game.types.Renderable58;
import legend.game.ui.UiBox;
import lod.thelegendoftides.Bait;
import lod.thelegendoftides.Fish;
import lod.thelegendoftides.FishingHole;
import lod.thelegendoftides.Tlot;
import lod.thelegendoftides.TlotFish;
import lod.thelegendoftides.TlotFishBaitWeights;
import lod.thelegendoftides.TlotGoods;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.util.Set;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.REGISTRIES;
import static legend.core.GameEngine.RENDERER;
import static legend.game.Graphics.displayHeight_1f8003e4;
import static legend.game.Graphics.displayWidth_1f8003e0;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Text.renderText;
import static legend.game.types.Renderable58.FLAG_DELETE_AFTER_RENDER;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class FishListScreen extends MenuScreen {
  public static final FontOptions UI_GOOD_BAIT = new FontOptions().colour(TextColour.YELLOW);

  private final UiBox headerBox;
  private final UiBox contentBox;

  public final FishingHole fishingHole;

  private final Set<RegistryId> seen;

  private float fullWidth;
  private int extraWidth;
  private float ratio;
  public boolean isFishListScreenDisabled;

  private Bait selectedBait;
  private final int visibleFishCount;

  public FishListScreen(final FishingHole fishingHole) {
    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.fishingHole = fishingHole;

    this.seen = CONFIG.getConfig(Tlot.SEEN_FISH_CONFIG.get());
    this.visibleFishCount = Math.toIntExact(this.fishingHole.fish.stream().filter( weight -> weight.fish.get().canBeCaught() && weight.fish.get() != TlotFish.COMMON_TRASH.get() && (weight.fish.get().isHidden ? gameState_800babc8.goods_19c.has(TlotGoods.TREASURE_GOONER_3000.get()) : true)).count());
    this.headerBox = new UiBox((int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
    this.contentBox = new UiBox((int)(this.fullWidth - 110 * this.ratio), 40, 120, this.visibleFishCount * 16);

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
    int renderableIndex = 0;
    for(int i = 0; i < this.fishingHole.fish.size(); i++) {
      final FishingHole.FishWeight fishWeight = this.fishingHole.fish.get(i);

      if(fishWeight.visibility == FishingHole.FishVisibility.HIDDEN) {
        continue;
      }

      final Fish fish = fishWeight.fish.get();

      if(!fish.canBeCaught() || fish == TlotFish.COMMON_TRASH.get() || (fish.isHidden && !gameState_800babc8.goods_19c.has(TlotGoods.TREASURE_GOONER_3000.get()))) {
        continue;
      }

      final int x = (int)(this.fullWidth - 101 * this.ratio);
      final int y = renderableIndex * 16 + 40;

      final Renderable58 icon = fish.icon.render(x, y, FLAG_DELETE_AFTER_RENDER);
      icon.z_3c = 10.0f;

      final boolean seen = this.seen.contains(fish.getRegistryId());
      final String name;

      if(seen || fishWeight.visibility == FishingHole.FishVisibility.VISIBLE) {
        name = I18n.translate(fish);
      } else {
        name = I18n.translate(getTranslationKey("fish_obfuscated"));
        icon.colour.zero();
      }

      if(TlotFishBaitWeights.getBaitWeightForFish(fish, this.selectedBait) > 10) {
        renderText(name, x + 9.0f, y + 1.5f, UI_GOOD_BAIT);
      } else {
        renderText(name, x + 9.0f, y + 1.5f, UI_WHITE);
      }

      renderableIndex++;
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
    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.headerBox.setPos((int)(this.fullWidth - 110 * this.ratio), 18);
    this.contentBox.setPos((int)(this.fullWidth - 110 * this.ratio), 40);
  }

  public void unload() {
    RENDERER.events().removeOnResize(this::onResized);
  }
}
