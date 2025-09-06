package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.core.platform.Window;
import legend.game.EngineState;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.MenuScreen;
import legend.game.modding.coremod.CoreMod;

import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment.displayHeight_1f8003e4;
import static legend.game.Scus94491BpeSegment.displayWidth_1f8003e0;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static lod.thelegendoftides.Tlot.MOD_ID;
import static lod.thelegendoftides.Tlot.getExtraWidth;

public class FishListScreen extends MenuScreen {

  private final ArrayList<Texture> fishSprites = new ArrayList<>();
  private final ArrayList<String> fishNames = new ArrayList<>();
  private final MeshObj bgQuad;

  private UiBox headerBox;
  private UiBox contentBox;

  public final FishingHole fishingHole;

  private float fullWidth;
  private int extraWidth;
  private float ratio;
  public boolean isFishListScreenDisabled = false;

  public FishListScreen(final FishingHole fishingHole) {
    this.extraWidth = (int)getExtraWidth();
    this.updateDimensions();

    this.fishingHole = fishingHole;

    for(final FishingHole.FishWeight fishWeight : fishingHole.fish) {
      final Path texturePath = Path.of("mods", "tlot", "fish", fishWeight.fish.get().icon);
      this.fishSprites.add(Texture.png(texturePath));
      this.fishNames.add(I18n.translate(fishWeight.fish.get()));
    }

    this.bgQuad = new QuadBuilder(MOD_ID)
      .uvSize(1.0f, 1.0f)
      .bpp(Bpp.BITS_24)
      .size(1.0f, 1.0f)
      .rgb(1.0f, 1.0f, 1.0f)
      .build();

    this.headerBox = new UiBox("Fish List Header", (int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
    this.contentBox = new UiBox("Fish List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fishNames.size() * 14);

    RENDERER.events().onResize(this::onResized);
  }

  @Override
  protected void render() {
    if(this.isFishListScreenDisabled) {
      return;
    }

    final MV transforms = new MV();
    for(int i = 0; i < this.fishSprites.size(); i++) {
      final float x = this.fullWidth - 110.0f * this.ratio;
      final float y = i * 14.0f + 40.0f;
      transforms.scaling(14.0f);
      transforms.transfer.set(x, y, 0.0f);
      RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.fishSprites.get(i));
      renderText(this.fishNames.get(i), x + 20.0f, y + 1, UI_WHITE);
    }

    this.headerBox.render();
    this.contentBox.render();
    renderText("Fish List", this.fullWidth - 95.0f * this.ratio, 20.0f, UI_WHITE);
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
    this.contentBox = new UiBox("Bait List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fishNames.size() * 14);
  }

  public void unload() {
    this.fishSprites.forEach(Texture::delete);
    this.headerBox.delete();
    this.contentBox.delete();

    RENDERER.events().removeOnResize(this::onResized);
  }
}
