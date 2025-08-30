package lod.thelegendoftides;

import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.inventory.screens.MenuStack;

import static legend.game.Scus94491BpeSegment.playSound;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.Scus94491BpeSegment_800b.scriptStatePtrArr_800bc1c0;
import static lod.thelegendoftides.Main.TIDES_INPUT_ACTION;

public class WaitingScreen extends MenuScreen {

    private int frames;
    public WaitingScreen(final FishMeta meta, final Fish fish) {
        this.frames = 600 + (int)(Math.random() * 600);
    }

    @Override
    protected void render() {
        if(frames-- > 0) {
            if(frames == 60) {
                playSound(0x8, 0xf, 0, 0);
            }
        } else {
            this.deferAction(this::unload);
        }
    }

    @Override
    protected InputPropagation inputActionPressed(InputAction action, boolean repeat) {
        if(action != TIDES_INPUT_ACTION.get() && !repeat) { return InputPropagation.PROPAGATE; }
        if(frames <= 60) {
            playSound(0x8, 0x10, 0, 0);
            this.deferAction(this::startReeling);
        } else {
            this.deferAction(this::unload);
        }

        return InputPropagation.HANDLED;
    }

    @Override
    protected boolean propagateRender() {
        return true;
    }

    private void startReeling() {
        final MenuStack stack = this.getStack();
        stack.popScreen();
        stack.pushScreen(new QTEScreen());
    }

    private void unload() {
        this.getStack().popScreen();
        // Give control back to dart
        scriptStatePtrArr_800bc1c0[10].resume();
        gameState_800babc8.indicatorsDisabled_4e3 = false;
    }
}
