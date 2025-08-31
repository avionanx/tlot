package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gpu.Bpp;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.QuadBuilder;
import legend.core.opengl.Texture;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.inventory.screens.controls.Button;
import legend.game.types.Translucency;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_BACK;
import static lod.thelegendoftides.Main.MOD_ID;
public class BaitSelectionScreen extends MenuScreen {
    private final MeshObj bgQuad;
    private final Texture bgTexture;
    private final MV bgTransforms;
    private FishMeta meta;
    private final List<Button> menuButtons = new ArrayList<>();
    public BaitSelectionScreen(final FishMeta meta, final BiConsumer<String, Runnable> callback) {
        this.meta = meta;
        for(var pair : this.meta.getBaitData().entrySet()) {
            this.addButton(pair.getValue().displayName(), () -> {
                playMenuSound(2);
                this.deferAction(() -> callback.accept(pair.getKey(), this::unload));
            });
        }
        this.setFocus(this.menuButtons.getFirst());
        this.addHotkey("Cancel", INPUT_ACTION_MENU_BACK, () -> {
            playMenuSound(3);
            this.deferAction(() -> callback.accept(null, this::unload));
        });
        this.bgQuad = new QuadBuilder(MOD_ID)
                .uvSize(1.0f,1.0f)
                .bpp(Bpp.BITS_24)
                .size(1.0f,1.0f)
                .rgb(1.0f, 1.0f, 1.0f)
                .build();
        this.bgTexture = Texture.png(Path.of("mods", "tlot", "bg", "pixel.png"));
        this.bgTransforms = new MV();
        this.bgTransforms.scaling(100.0f, 80.0f, 0.0f);
        this.bgTransforms.transfer.set(20.0f, 20.0f, 240.0f);
    }

    @Override
    protected void render() {
        RENDERER.queueOrthoModel(this.bgQuad, this.bgTransforms, QueuedModelStandard.class).texture(this.bgTexture).colour(0.0f, 0.0f, 0.0f).alpha(0.66f).translucency(Translucency.HALF_B_PLUS_HALF_F);
    }

    private void unload() {
        this.getStack().popScreen();
        this.bgQuad.delete();
        this.bgTexture.delete();
    }

    private void addButton(final String text, final Runnable onClick) {
        final int index = this.menuButtons.size();
        final Button button = this.addControl(new Button(text));
        button.setPos(30, 20 + index * 20);
        button.setWidth(80);
        button.onHoverIn(() -> {
            playMenuSound(1);
            this.setFocus(button);
        });
        button.setTextColour(TextColour.GREY);
        button.onLostFocus(() -> button.setTextColour(TextColour.GREY));
        button.onGotFocus(() -> button.setTextColour(TextColour.WHITE));

        button.onPressed(onClick::run);
        this.menuButtons.add(button);
        button.onKeyPress((key, scancode, mods, repeat) -> {
            switch(key) {
                case S -> {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index + i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                }
                case W -> {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index - i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                }
            }

            return null;
        });
    }

    // Want to show fish list while this screen is active
    @Override
    protected boolean propagateRender() {
        return true;
    }
}
