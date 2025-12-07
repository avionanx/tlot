package lod.thelegendoftides.screens;

import legend.core.platform.Window;
import legend.core.platform.input.InputAction;
import legend.game.combat.ui.UiBox;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import static legend.core.GameEngine.RENDERER;
import static legend.game.SItem.UI_WHITE_CENTERED;
import static legend.game.Text.renderText;
import static lod.thelegendoftides.Tlot.getExtraWidth;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class TimedMessageScreen extends MenuScreen {
    private UiBox backgroundBox;
    private final String message;
    private int extraWidth;
    private int framesRemaining;

    public TimedMessageScreen(final String messageKey, final int frames) {
        this.message = I18n.translate(getTranslationKey(messageKey));
        this.framesRemaining = frames;

        this.extraWidth = (int)getExtraWidth();
        this.backgroundBox = new UiBox("Message BG", 8 - this.extraWidth / 2, 20, 304 + this.extraWidth, 10);

        RENDERER.window().events().onResize(this::onResized);
    }

    private void onResized(final Window window, final int x, final int y) {
        this.backgroundBox.delete();

        this.extraWidth = (int)getExtraWidth();

        this.backgroundBox = new UiBox("Message BG", 8 - this.extraWidth / 2, 20, 304 + this.extraWidth, 10);
    }

    private void unloadMessage() {
        this.getStack().popScreen();
        this.backgroundBox.delete();
        RENDERER.window().events().removeOnResize(this::onResized);
    }

    @Override
    protected InputPropagation inputActionPressed(@NotNull InputAction action, boolean repeat) {
        return InputPropagation.HANDLED;
    }

    @Override
    protected void render() {
        if(this.framesRemaining-- > 0) {
            renderText(this.message, 160.0f, 20.0f, UI_WHITE_CENTERED);
            this.backgroundBox.render();
        } else {
            this.deferAction(this::unloadMessage);
        }
    }
}
