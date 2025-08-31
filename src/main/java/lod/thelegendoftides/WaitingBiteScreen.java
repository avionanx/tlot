package lod.thelegendoftides;

import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;

import static legend.game.Scus94491BpeSegment.playSound;
import static legend.game.modding.coremod.CoreMod.INPUT_ACTION_MENU_CONFIRM;

public class WaitingBiteScreen extends MenuScreen {

    private final Runnable QTESuccessCallback;
    private final Runnable QTEFailCallback;
    private int frames;

    public WaitingBiteScreen(final Runnable QTESuccessCallback, final Runnable QTEFailCallback) {
        this.frames = 100 + (int)(Math.random() * 40);
        this.QTESuccessCallback = QTESuccessCallback;
        this.QTEFailCallback = QTEFailCallback;
    }

    @Override
    protected void render() {
        if(frames-- > 0) {
            if(frames == 60) {
                playSound(0x0, 0x25, 0, 0);
            }
        } else if (frames == 0) {
            playSound(0x0, 0x28, 0, 0);
            this.deferAction(this::failed);
        }
    }

    @Override
    protected InputPropagation inputActionPressed(InputAction action, boolean repeat) {
        if(action != INPUT_ACTION_MENU_CONFIRM.get() && !repeat) { return InputPropagation.PROPAGATE; }
        if(frames <= 60) {
            playSound(0x0, 0x26, 0, 0);
            this.deferAction(this::succeeded);
        } else {
            playSound(0x0, 0x28, 0, 0);
            this.deferAction(this::failed);
        }

        return InputPropagation.HANDLED;
    }

    private void succeeded() {
        this.getStack().popScreen();
        this.QTESuccessCallback.run();
    }

    private void failed() {
        this.getStack().popScreen();
        this.QTEFailCallback.run();
    }
}
