package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.game.Scus94491BpeSegment_8002;
import legend.game.inventory.screens.FontOptions;
import legend.game.inventory.screens.MenuScreen;
import legend.game.submap.SMap;
import legend.game.types.Translucency;

import java.nio.file.Path;
import java.util.ArrayList;

import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8004.currentEngineState_8004dd04;
import static lod.thelegendoftides.Main.MOD_ID;
import static lod.thelegendoftides.Main.isOnFishingPrimitive;

public class FishListScreen extends MenuScreen {

    private final FishMeta meta;
    private final ArrayList<Texture> fishSprites = new ArrayList<>();
    private final ArrayList<String> fishNames = new ArrayList<>();
    private final MeshObj bgQuad;

    private final FishLocationData locationData;
    final MV bgTransforms;
    final Texture bgTexture;

    public boolean battleTransitionFinished = true;

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
        this.bgTexture = Texture.png(Path.of("mods", "tlot", "bg", "pixel.png"));
    }
    @Override
    protected void render() {
        if(!isOnFishingPrimitive(this.locationData) && currentEngineState_8004dd04 instanceof SMap || !battleTransitionFinished) return;
        for(int i = 0; i < this.fishSprites.size(); i++) {
            final float x = 2.0f + 230.0f;
            final float y = i * 16.0f + 22.0f;
            final MV transforms = new MV();
            transforms.scaling(16.0f);
            transforms.transfer.set(x, y, 0.0f);
            RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.fishSprites.get(i));
            Scus94491BpeSegment_8002.renderText(this.fishNames.get(i), x + 20.0f, y + 2.0f, new FontOptions());
        }
        RENDERER.queueOrthoModel(this.bgQuad, this.bgTransforms, QueuedModelStandard.class).texture(this.bgTexture).colour(0.0f, 0.0f, 0.0f).alpha(0.66f).translucency(Translucency.HALF_B_PLUS_HALF_F);
    }
    public void unload() {
        this.fishSprites.forEach(Texture::delete);
    }
}
