package lod.thelegendoftides;

import legend.core.platform.input.InputAction;
import legend.game.i18n.I18n;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import org.jetbrains.annotations.NotNull;

import static legend.game.Scus94491BpeSegment_8002.renderText;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;
import static legend.game.SItem.UI_WHITE;
import static lod.thelegendoftides.Tlot.getTranslationKey;

public class MessageScreen extends MenuScreen {
    private final String message;

    public MessageScreen(final String messageKey) {
        this.message = I18n.translate(getTranslationKey(messageKey));
    }

    private void unloadMessage() {
        this.getStack().popScreen();
    }

    @Override
    protected InputPropagation inputActionPressed(@NotNull InputAction action, boolean repeat) {
        if (action == INPUT_ACTION_MENU_CONFIRM.get()) {
            this.unloadMessage();
        }
        return InputPropagation.HANDLED;
    }

    @Override
    protected void render() {
        renderText(this.message, 184.0f - this.message.length() * 8, 20.0f, UI_WHITE);
    }
}
