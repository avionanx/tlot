package lod.thelegendoftides;

import legend.core.MathHelper;
import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
import legend.core.opengl.Obj;
import legend.core.opengl.QuadBuilder;
import legend.core.platform.input.InputAction;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.Translucency;
import org.joml.Math;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;

import static legend.core.GameEngine.GPU;
import static legend.core.GameEngine.RENDERER;
import static legend.game.combat.SEffe.renderButtonPressHudElement1;
import static legend.lodmod.LodMod.INPUT_ACTION_BTTL_ATTACK;

public class AdditionOverlayScreen extends MenuScreen {
  private final ArrayList<HitStruct> actionList = new ArrayList<>();
  private boolean isAwaitingPress = false;
  private final MV transforms = new MV();
  public Obj reticleBorderShadow;

  private final int FRAMES_UNTIL_SUCCESS = 15;
  private int FRAMES = 0;
  private int numFramesToRenderInnerSquare = 0;
  private AdditionLastHitSuccessStatus lastHitStatus = AdditionLastHitSuccessStatus.WAITING;
  private final Runnable onAdditionSuccess;
  private final Runnable onAdditionFail;

  public AdditionOverlayScreen(final Runnable onAdditionSuccess, final Runnable onAdditionFail) {
    this.reticleBorderShadow = new QuadBuilder("Reticle background")
      .translucency(Translucency.B_MINUS_F)
      .monochrome(0, 0.0f)
      .monochrome(1, 0.0f)
      .monochrome(2, 1.0f)
      .monochrome(3, 1.0f)
      .pos(-1.0f, -0.5f, 0.0f)
      .size(1.0f, 1.0f)
      .build();
    this.onAdditionSuccess = onAdditionSuccess;
    this.onAdditionFail = onAdditionFail;
  }

  @Override
  protected void render() {
    this.tick();
    this.renderAdditionBorders();
    this.renderButtons();
    this.renderAdditionInnerSquare();
  }

  private void renderButtons() {
    if(this.numFramesToRenderInnerSquare != 0 && this.lastHitStatus != AdditionLastHitSuccessStatus.EARLY) {
      renderButtonPressHudElement1(0x24, 120, 51, Translucency.B_PLUS_F, 0x80);
      renderButtonPressHudElement1(33, 115, 48, Translucency.B_PLUS_F, 0x80);
      renderButtonPressHudElement1(0x25, 115, 50, Translucency.B_PLUS_F, 0x80);
    } else {
      renderButtonPressHudElement1(0x24, 120, 43, Translucency.B_PLUS_F, 0x80);
      renderButtonPressHudElement1(35, 115, 48, Translucency.B_PLUS_F, 0x80);
    }
  }

  private void renderAdditionBorders() {
    for(final HitStruct hit : this.actionList) {
      BorderStruct innerMostRotatingBorder = null;
      for(final BorderStruct border : hit.borders()) {
        if(border.visible && border.framesUntilRender < this.FRAMES) {
          this.transforms
            .scaling(border.size, border.size, 1.0f)
            .rotateZ(border.angle);
          this.transforms.transfer.set(GPU.getOffsetX(), GPU.getOffsetY() + 30.0f, 120.0f);
          RENDERER.queueOrthoModel(RENDERER.lineBoxBPlusF, this.transforms, QueuedModelStandard.class)
            .colour(border.colour);
          innerMostRotatingBorder = border;
        }
      }

      if(innerMostRotatingBorder != null) {
        this.renderAdditionBorderShadow(hit.shadowColour(), innerMostRotatingBorder.angle, innerMostRotatingBorder.size);
        this.renderAdditionBorderShadow(hit.shadowColour(), innerMostRotatingBorder.angle + MathHelper.HALF_PI, innerMostRotatingBorder.size);
        this.renderAdditionBorderShadow(hit.shadowColour(), innerMostRotatingBorder.angle + MathHelper.PI, innerMostRotatingBorder.size);
        this.renderAdditionBorderShadow(hit.shadowColour(), innerMostRotatingBorder.angle + MathHelper.PI + MathHelper.HALF_PI, innerMostRotatingBorder.size);
      }
    }
  }

  // Renders inner square of additions, in retail they shoved this in border renderer
  private void renderAdditionInnerSquare() {
    if(this.numFramesToRenderInnerSquare == 0) {
      for(int i = 0; i < 3; i++) {
        float size = (20 - i * 2) * 1.5f;
        this.transforms.scaling(size, size, 1.0f);
        this.transforms.transfer.set(GPU.getOffsetX(), GPU.getOffsetY() + 30.0f, 120.0f);
        Vector3f colour = i == 1 ? new Vector3f(0.28f, 0.37f, 1.0f) : new Vector3f(0.19f, 0.19f, 0.19f);
        RENDERER.queueOrthoModel(RENDERER.lineBoxBPlusF, this.transforms, QueuedModelStandard.class)
          .colour(colour);
      }
    } else {
      this.numFramesToRenderInnerSquare--;
      for(int i = 0; i < 2; i++) {
        this.transforms.scaling(30.0f - i * 8.0f, 30.0f - i * 8.0f, 1.0f);
        this.transforms.transfer.set(GPU.getOffsetX(), GPU.getOffsetY() + 30.0f, 120.0f);

        switch(this.lastHitStatus) {
          case EARLY -> {
            RENDERER.queueOrthoModel(RENDERER.centredQuadBPlusF, this.transforms, QueuedModelStandard.class)
              .colour(0.19f, 0.19f, 0.19f);
          }
          case LATE -> {
            RENDERER.queueOrthoModel(RENDERER.centredQuadBPlusF, this.transforms, QueuedModelStandard.class)
              .colour(0.28f, 0.37f, 1.0f);
          }
          case SUCCESS -> {
            RENDERER.queueOrthoModel(RENDERER.centredQuadBPlusF, this.transforms, QueuedModelStandard.class)
              .monochrome(1.0f);
          }
          case WRONG -> {
            RENDERER.queueOrthoModel(RENDERER.centredQuadBPlusF, this.transforms, QueuedModelStandard.class)
              .colour(0.55f, 0.17f, 0.11f);
          }
        }
      }
      if(this.numFramesToRenderInnerSquare == 0) {
        this.lastHitStatus = AdditionLastHitSuccessStatus.WAITING;
      }
    }
  }

  private void tick() {
    final Iterator<HitStruct> hitStructIterator = this.actionList.iterator();
    while(hitStructIterator.hasNext()) {
      final HitStruct hit = hitStructIterator.next();
      if(hit.frameBeginTime() + this.FRAMES_UNTIL_SUCCESS == this.FRAMES) {
        this.isAwaitingPress = true;
      } else if(hit.frameBeginTime() + this.FRAMES_UNTIL_SUCCESS + hit.numSuccessFrames() == this.FRAMES && this.isAwaitingPress) {
        this.lastHitStatus = AdditionLastHitSuccessStatus.LATE;
        this.numFramesToRenderInnerSquare = 2;
        this.isAwaitingPress = false;
        this.onAdditionFail.run();
        continue;
      } else if(hit.frameBeginTime() + this.FRAMES_UNTIL_SUCCESS + hit.numSuccessFrames() + 2 < this.FRAMES) {
        hitStructIterator.remove();
        continue;
      }
      for(final BorderStruct border : hit.borders()) {
        if(border.framesUntilRender < this.FRAMES) {
          border.visible = true;
        }
        if(border.visible) {
          this.fadeAdditionBorders(border, 0.125f);
        }
      }
    }
    FRAMES++;
  }

  //TODO this needs to use timing from selected addition hit
  public void addHit() {
    final int frameBeginTime = this.FRAMES;
    final HitStruct hit = new HitStruct(0.06f, frameBeginTime, 2, new ArrayList<>(14));

    for(int i = 0; i < 14; i++) {
      final float size = (2 + i) * 15.0f;
      final float angle = Math.toRadians(i * 11.25f);
      final int framesUntilRender = hit.frameBeginTime() + (hit.numSuccessFrames() - 1) / 2 + 14 - i;
      BorderStruct border = new BorderStruct(size, angle, new Vector3f(0.28f, 0.37f, 1.0f), false, framesUntilRender);
      hit.borders().addFirst(border);
    }

    this.actionList.add(hit);
  }

  private void renderAdditionBorderShadow(final float shadowColour, final float angle, final float size) {
    // Would you believe me if I said I knew what I was doing when I wrote any of this?
    final float offset = size - 1;
    final float sin0 = MathHelper.sin(angle);
    final float cos0 = MathHelper.cosFromSin(sin0, angle);
    final float x0 = cos0 * offset / 2.0f;
    final float y0 = sin0 * offset / 2.0f;

    this.transforms.transfer.set(x0 + GPU.getOffsetX(), y0 + GPU.getOffsetY() + 30.0f, 124.0f);
    this.transforms
      .scaling(10.0f, size, 1.0f)
      .rotateLocalZ(angle);

    RENDERER.queueOrthoModel(this.reticleBorderShadow, this.transforms, QueuedModelStandard.class)
      .monochrome(shadowColour);
  }

  //TODO
  public void unload() {
    this.reticleBorderShadow.delete();
  }

  @Override
  protected InputPropagation inputActionPressed(InputAction action, boolean repeat) {
    if(action == INPUT_ACTION_BTTL_ATTACK.get()) {
      if(this.isAwaitingPress) {
        this.isAwaitingPress = false;
        this.lastHitStatus = AdditionLastHitSuccessStatus.SUCCESS;
        this.numFramesToRenderInnerSquare = 2;
        this.onAdditionSuccess.run();
      } else if(!this.actionList.isEmpty()) {
        this.lastHitStatus = AdditionLastHitSuccessStatus.EARLY;
        this.numFramesToRenderInnerSquare = 2;
        this.actionList.removeFirst();
        this.onAdditionFail.run();
      }
      return InputPropagation.HANDLED;
    }

    return InputPropagation.PROPAGATE;
  }

  private void fadeAdditionBorders(final BorderStruct border, final float fadeStep) {
    int numberOfNegativeComponents = 0;
    float newColour = border.colour.x - fadeStep;
    final float newR;
    if(newColour > 0) {
      newR = newColour;
    } else {
      newR = 0;
      numberOfNegativeComponents++;
    }

    //LAB_801067b0
    newColour = border.colour.y - fadeStep;
    final float newG;
    if(newColour > 0) {
      newG = newColour;
    } else {
      newG = 0;
      numberOfNegativeComponents++;
    }

    //LAB_801067c4
    newColour = border.colour.z - fadeStep;
    final float newB;
    if(newColour > 0) {
      newB = newColour;
    } else {
      newB = 0;
      numberOfNegativeComponents++;
    }

    //LAB_801067d8
    border.colour.set(newR, newG, newB);
    if(numberOfNegativeComponents == 3) {
      border.visible = false;
    }
  }
}
