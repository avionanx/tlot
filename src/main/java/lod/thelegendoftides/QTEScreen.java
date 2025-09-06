package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;

import java.nio.file.Path;

import static legend.core.GameEngine.RENDERER;
import static legend.lodmod.LodMod.INPUT_ACTION_BTTL_ATTACK;
import static lod.thelegendoftides.Tlot.MOD_ID;

public class QTEScreen extends MenuScreen {

    private float stamina = 100.0f;

    private final float VELOCITY = 1.0f / 60.0f;
    private final float goodProgressSize = 0.1f;
    private float progress = 0.0f;
    private final MeshObj bgQuad;
    private final Texture bgTexture;

    private boolean canProgress = true;
    public QTEScreen() {
        this.bgQuad = new QuadBuilder(MOD_ID)
                .uvSize(1.0f,1.0f)
                .bpp(Bpp.BITS_24)
                .size(1.0f,1.0f)
                .rgb(1.0f, 1.0f, 1.0f)
                .pos(-0.5f, -0.5f, 0.0f)
                .build();
        this.bgTexture = Texture.png(Path.of("mods", "tlot", "bg", "pixel.png"));
    }

    @Override
    protected InputPropagation inputActionPressed(InputAction action, boolean repeat) {
        if(action != INPUT_ACTION_BTTL_ATTACK.get() && !repeat) { return InputPropagation.PROPAGATE; }
        this.canProgress = !canProgress;
        return InputPropagation.HANDLED;
    }

    @Override
    protected void render() {
        if(canProgress)
            this.progress = (this.progress + this.VELOCITY) % 2.0f;
        final MV transforms = new MV();
        transforms.scaling(2.0f, 6.0f, 0.0f);
        transforms.transfer.y = 40.0f;
        transforms.transfer.x = 184.0f + 60.0f * (float)Math.sin(Math.PI * this.progress);
        transforms.transfer.z = 0.0f;
        System.out.println(this.progress);
        RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.bgTexture);
        transforms.scaling(122.0f, 2.0f, 0.0f);
        transforms.transfer.y = 40.0f;
        transforms.transfer.x = 184.0f;
        transforms.transfer.z = 1.0f;
        RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.bgTexture);
        transforms.scaling(122.0f * goodProgressSize, 2.0f, 0.0f);
        transforms.transfer.y = 40.0f;
        transforms.transfer.x = 184.0f;
        transforms.transfer.z = 0.5f;
        RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.bgTexture).colour(0.0f, 1.0f, 0.0f);
        transforms.scaling(122.0f * goodProgressSize * 3, 2.0f, 0.0f);
        transforms.transfer.y = 40.0f;
        transforms.transfer.x = 184.0f;
        transforms.transfer.z = 0.7f;
        RENDERER.queueOrthoModel(this.bgQuad, transforms, QueuedModelStandard.class).texture(this.bgTexture).colour(1.0f, 1.0f, 0.0f);


    }

    private void unload() {
        this.getStack().popScreen();
        this.bgQuad.delete();
        this.bgTexture.delete();
    }
}
