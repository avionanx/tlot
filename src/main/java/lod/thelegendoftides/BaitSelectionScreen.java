package lod.thelegendoftides;

import legend.game.EngineState;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.TextColour;
import legend.game.inventory.screens.controls.Button;
import legend.game.modding.coremod.CoreMod;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static legend.core.GameEngine.CONFIG;
import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment.displayHeight_1f8003e4;
import static legend.game.Scus94491BpeSegment.displayWidth_1f8003e0;
import static legend.game.Scus94491BpeSegment_8002.playMenuSound;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.*;
import static lod.thelegendoftides.Main.getExtraWidth;
import static lod.thelegendoftides.Main.getTranslationKey;

public class BaitSelectionScreen extends MenuScreen {
    private FishMeta meta;
    private final List<Button> menuButtons = new ArrayList<>();
    private final UiBox headerBox;
    private final UiBox contentBox;
    private final UiBox hotkeyBox;

    private int extraWidth;
    public BaitSelectionScreen(final FishMeta meta, final BiConsumer<String, Runnable> callback) {
        this.meta = meta;
        this.extraWidth = (int)getExtraWidth();

        for(var pair : this.meta.getBaitData().entrySet()) {
            this.addButton(pair.getValue().displayName(), () -> {
                playMenuSound(2);
                this.deferAction(() -> callback.accept(pair.getKey(), this::unload));
            });
        }

        this.headerBox = new UiBox("Bait List Header", 20 - this.extraWidth / 2, 18, 100, 14);
        this.contentBox = new UiBox("Bait List Content", 20 - this.extraWidth / 2, 40, 100, 80);
        this.hotkeyBox = new UiBox("Hotkey BG", 8 - this.extraWidth / 2, 226, 304 + extraWidth, 10);

        this.setFocus(this.menuButtons.getFirst());
        this.addHotkey(I18n.translate(getTranslationKey("hotkey_bait_cancel")), INPUT_ACTION_MENU_BACK, () -> {
            playMenuSound(3);
            this.deferAction(() -> callback.accept(null, this::unload));
        });
    }

    @Override
    protected void render() {
        this.headerBox.render();
        this.contentBox.render();
        this.hotkeyBox.render();
        renderText("Bait List", 40 - this.extraWidth / 2, 20, UI_WHITE);
    }

    private void unload() {
        this.getStack().popScreen();
        this.headerBox.delete();
        this.contentBox.delete();
        this.hotkeyBox.delete();
    }

    private void addButton(final String text, final Runnable onClick) {
        final int index = this.menuButtons.size();
        final Button button = this.addControl(new Button(text));

        button.setPos(30 - this.extraWidth / 2, 40 + index * 14);
        button.setZ(1);
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
        button.onInputActionPressed((action, repeat) -> {
                if(action == INPUT_ACTION_MENU_DOWN.get()) {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index + i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                    return InputPropagation.HANDLED;
                }
                else if(action == INPUT_ACTION_MENU_UP.get()) {
                    for(int i = 1; i < this.menuButtons.size(); i++) {
                        final Button otherButton = this.menuButtons.get(Math.floorMod(index - i, this.menuButtons.size()));

                        if(!otherButton.isDisabled() && otherButton.isVisible()) {
                            playMenuSound(1);
                            this.setFocus(otherButton);
                            break;
                        }
                    }
                    return InputPropagation.HANDLED;
                }
            return InputPropagation.PROPAGATE;
        });
    }

    // Want to show fish list while this screen is active
    @Override
    protected boolean propagateRender() {
        return true;
    }
}
