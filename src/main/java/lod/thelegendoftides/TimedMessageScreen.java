package lod.thelegendoftides;

import legend.core.platform.input.InputAction;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import static legend.game.SItem.UI_WHITE;
import static legend.game.Scus94491BpeSegment_8002.renderText;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class TimedMessageScreen extends MenuScreen {
    private final String message;
    private int framesRemaining;

    public TimedMessageScreen(final String messageKey, final int frames) {
        this.message = I18n.translate(getTranslationKey(messageKey));
        this.framesRemaining = frames;
    }

    private void unloadMessage() {
        this.getStack().popScreen();
    }

    @Override
    protected InputPropagation inputActionPressed(@NotNull InputAction action, boolean repeat) {
        return InputPropagation.HANDLED;
    }

    @Override
    protected void render() {
        if(framesRemaining-- > 0) {
            renderText(this.message, 184.0f - this.message.length() * 8, 20.0f, UI_WHITE);
        } else {
            this.deferAction(this::unloadMessage);
        }
    }
}
