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
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.MenuScreen;
import legend.game.modding.coremod.CoreMod;
import legend.game.submap.SMap;
import legend.game.types.Translucency;

import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment.displayHeight_1f8003e4;
import static legend.game.Scus94491BpeSegment.displayWidth_1f8003e0;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static lod.thelegendoftides.Main.*;

public class FishListScreen extends MenuScreen {

    private final FishMeta meta;
    private final ArrayList<Texture> fishSprites = new ArrayList<>();
    private final ArrayList<String> fishNames = new ArrayList<>();
    private final MeshObj bgQuad;

    private UiBox headerBox;
    private UiBox contentBox;

    private final FishLocationData locationData;

    private float fullWidth;
    private int extraWidth;
    private float ratio;
    public boolean isFishListScreenDisabled = true;

    public FishListScreen(final FishMeta meta, final FishLocationData locationData) {
        this.meta = meta;
        this.extraWidth = (int)getExtraWidth();
        this.updateDimensions();

        this.locationData = locationData;

        for(var fishWeightPair : locationData.fishWeightRegistries()) {
            final Path texturePath = Path.of("mods", "tlot", "fish", "%s.png".formatted(fishWeightPair.first()));
            this.fishSprites.add(Texture.png(texturePath));
            this.fishNames.add(this.meta.getFish(fishWeightPair.first()).displayName());
        }

        this.bgQuad = new QuadBuilder(MOD_ID)
                .uvSize(1.0f,1.0f)
                .bpp(Bpp.BITS_24)
                .size(1.0f,1.0f)
                .rgb(1.0f, 1.0f, 1.0f)
                .build();

        this.headerBox = new UiBox("Fish List Header", (int)(this.fullWidth - 110 * this.ratio), 18, 120, 14);
        this.contentBox = new UiBox("Fish List Content", (int)(this.fullWidth - 110 * this.ratio), 40, 120, this.fishNames.size() * 14);

        RENDERER.events().onResize(this::onResized);
    }

    @Override
    protected void render() {
        if(!isOnFishingPrimitive(this.locationData) && isFishListScreenDisabled) return;
        for(int i = 0; i < this.fishSprites.size(); i++) {
            final float x = this.fullWidth - 110.0f * this.ratio;
            final float y = i * 14.0f + 40.0f;
            final MV transforms = new MV();
            transforms.scaling(14.0f);
            transforms.transfer.set(x, y, 0.0f);
            RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.fishSprites.get(i));
            renderText(this.fishNames.get(i), x + 20.0f, y, UI_WHITE);
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

    public void onResized(Window window, int a, int b) {
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
