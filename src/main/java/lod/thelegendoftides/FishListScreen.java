package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.game.combat.ui.UiBox;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.MenuScreen;
import legend.game.submap.SMap;
import legend.game.types.Translucency;

import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static lod.thelegendoftides.Main.MOD_ID;
import static lod.thelegendoftides.Main.isOnFishingPrimitive;
import static legend.game.Scus94491BpeSegment_8002.renderText;
public class FishListScreen extends MenuScreen {

    private final FishMeta meta;
    private final ArrayList<Texture> fishSprites = new ArrayList<>();
    private final ArrayList<String> fishNames = new ArrayList<>();
    private final MeshObj bgQuad;

    private final UiBox headerBox;
    private final UiBox contentBox;

    private final FishLocationData locationData;
    final MV bgTransforms;
    public boolean isFishListScreenDisabled = true;

    public FishListScreen(final FishMeta meta, final FishLocationData locationData) {
        this.meta = meta;

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

        this.bgTransforms = new MV();
        this.bgTransforms.scaling(120.0f, 36.0f, 0.0f);
        this.bgTransforms.transfer.set(230.0f, 20.0f, 80.0f);

        this.headerBox = new UiBox("Fish List Header", 230, 18, 120, 14);
        this.contentBox = new UiBox("Fish List Content", 230, 40, 120, this.fishNames.size() * 14);
    }

    @Override
    protected void render() {
        if(!isOnFishingPrimitive(this.locationData) && isFishListScreenDisabled) return;
        for(int i = 0; i < this.fishSprites.size(); i++) {
            final float x = 2.0f + 230.0f;
            final float y = i * 14.0f + 40.0f;
            final MV transforms = new MV();
            transforms.scaling(14.0f);
            transforms.transfer.set(x, y, 0.0f);
            RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.fishSprites.get(i));
            renderText(this.fishNames.get(i), x + 20.0f, y, UI_WHITE);
        }
        this.headerBox.render();
        this.contentBox.render();
        renderText("Fish List", 260.0f, 20.0f, UI_WHITE);
    }

    public void unload() {
        this.fishSprites.forEach(Texture::delete);
        this.headerBox.delete();
        this.contentBox.delete();
    }
}
