package lod.thelegendoftides;

import legend.core.MathHelper;
import legend.core.QueuedModelStandard;
import legend.core.Transformations;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.MV;
import legend.core.gte.ModelPart10;
import legend.core.opengl.Obj;
import legend.core.opengl.QuadBuilder;
import legend.core.platform.input.InputAction;
import legend.game.combat.Battle;
import legend.game.combat.bent.PlayerBattleEntity;
import legend.game.combat.types.AdditionHits80;
import legend.game.inventory.screens.InputPropagation;
import legend.game.inventory.screens.MenuScreen;
import legend.game.types.TmdAnimationFile;
import legend.game.types.Translucency;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static legend.core.GameEngine.GPU;
import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment.battlePreloadedEntities_1f8003f4;
import static legend.game.Scus94491BpeSegment.loadDrgnDir;
import static legend.game.Scus94491BpeSegment_8004.additionCounts_8004f5c0;
import static legend.game.Scus94491BpeSegment_8006.battleState_8006e398;
import static legend.game.Scus94491BpeSegment_800b.gameState_800babc8;
import static legend.game.combat.SBtld.loadAdditions;
import static legend.game.combat.SEffe.renderButtonPressHudElement1;
import static lod.thelegendoftides.Main.TIDES_INPUT_ACTION;
import static lod.thelegendoftides.Main.TIDES_INPUT_FISH;

public class AdditionOverlayScreen extends MenuScreen {
  private final Battle battle;
  private final PlayerBattleEntity player;
  private final Random rand = new Random();

  private final ArrayList<HitStruct> actionList = new ArrayList<>();
  private boolean isAwaitingPress = false;
  private final MV transforms = new MV();
  public Obj reticleBorderShadow;

  private final int FRAMES_UNTIL_SUCCESS = 15;
  private int FRAMES = 0;
  private int numFramesToRenderInnerSquare = 0;
  private AdditionLastHitSuccessStatus lastHitStatus = AdditionLastHitSuccessStatus.WAITING;

  private int loadingAnimIndex = -1;
  private AdditionHits80 activeAddition;
  private int additionTicks;

  private final float[] stringStartX = new float[32];
  private final float[] stringStartY = new float[32];
  private final float[] stringEndX = new float[32];
  private final float[] stringEndY = new float[32];

  public AdditionOverlayScreen(Battle battle, PlayerBattleEntity player) {
    this.battle = battle;
    this.player = player;
    this.reticleBorderShadow = new QuadBuilder("Reticle background")
      .translucency(Translucency.B_MINUS_F)
      .monochrome(0, 0.0f)
      .monochrome(1, 0.0f)
      .monochrome(2, 1.0f)
      .monochrome(3, 1.0f)
      .pos(-1.0f, -0.5f, 0.0f)
      .size(1.0f, 1.0f)
      .build();

    for(int i = 0; i < this.stringStartY.length; i++) {
      this.stringStartX[i] = i;
    }
  }

  private void addHit() {
    final int charId = this.player.charId_272;
    final int additionCount = additionCounts_8004f5c0[charId];
    final int randomAddition = this.rand.nextInt(additionCount);
    final int fileIndex = 4031 + charId * 8 + randomAddition;

    final int oldAddition = gameState_800babc8.charData_32c[charId].selectedAddition_19;
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = randomAddition;
    loadAdditions();
    gameState_800babc8.charData_32c[charId].selectedAddition_19 = oldAddition;

    this.activeAddition = battlePreloadedEntities_1f8003f4.additionHits_38[0];
    int hitCount = 0;

    for(int i = 0; i < this.activeAddition.hits_00.length; i++) {
      if(this.activeAddition.hits_00[i].flags_00 != 0) {
        hitCount++;
      }
    }

    this.loadingAnimIndex = 16 + this.rand.nextInt(hitCount);
    this.additionTicks = this.activeAddition.hits_00[this.loadingAnimIndex - 16].totalFrames_01;

    loadDrgnDir(0, fileIndex, files -> {
      this.player.combatant_144.mrg_04 = null;
      this.battle.attackAnimationsLoaded(files, this.player.combatant_144, false, this.player.combatant_144.charSlot_19c);
      // Finish asset loading - some animations need to be decompressed
      this.battle.FUN_800c9e10(this.player.combatant_144, this.loadingAnimIndex);
    });
  }

  @Override
  protected void render() {
    if(this.loadingAnimIndex != -1) {
      final TmdAnimationFile asset = battleState_8006e398.getAnimationGlobalAsset(this.player.combatant_144, this.loadingAnimIndex);

      if(asset != null) {
        asset.loadIntoModel(this.player.model_148);
        this.loadingAnimIndex = -1;

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
    } else {
      this.additionTicks--;

      if(this.additionTicks == 0) {
        this.player.model_148.animationState_9c = 2; // pause
        this.addHit();
      }
    }

    this.tick();
    this.renderAdditionBorders();
    this.renderButtons();
    this.renderAdditionInnerSquare();
    this.renderString();
  }

  private void renderString() {
    final float segmentLength = 0.1f;
    final float gravity = 0.1f;

    for(int i = 1; i < this.stringStartX.length - 1; i++) {
      final float dx1 = stringStartX[i - 1] - stringStartX[i];
      final float dy1 = stringStartY[i - 1] - stringStartY[i];
      final float mag1 = (float)java.lang.Math.hypot(dx1, dy1);
      final float extension1 = mag1 - segmentLength;

      final float dx2 = stringStartX[i < this.stringStartX.length - 1 ? i + 1 : this.stringStartX.length - 1] - stringStartX[i];
      final float dy2 = stringStartY[i < this.stringStartX.length - 1 ? i + 1 : this.stringStartX.length - 1] - stringStartY[i];
      final float mag2 = (float)java.lang.Math.hypot(dx2, dy2);
      final float extension2 = mag2 - segmentLength;

      final float xv = dx1 / mag1 * extension1 + dx2 / mag2 * extension2;
      final float yv = dy1 / mag1 * extension1 + dy2 / mag2 * extension2 + gravity;

      stringEndX[i] = stringStartX[i] + xv * 0.5f;
      stringEndY[i] = stringStartY[i] + yv * 0.5f;
    }

    System.arraycopy(this.stringEndX, 0, this.stringStartX, 0, this.stringEndX.length);
    System.arraycopy(this.stringEndY, 0, this.stringStartY, 0, this.stringEndY.length);

    final int weaponPart = this.player.getWeaponModelPart();
    final int weaponVertex = this.player.getWeaponTrailVertexComponent();
    final ModelPart10 sword = this.player.model_148.modelParts_00[weaponPart];
    final GsCOORDINATE2 weaponCoord2 = sword.coord2_04;
    final Vector3f worldspacePos = sword.tmd_08.vert_top_00[weaponVertex];
    final Vector2f viewspacePos = new Vector2f();

    Transformations.toScreenspace(worldspacePos, weaponCoord2, viewspacePos);

    // Set start and end points
    this.stringStartX[0] = viewspacePos.x + GPU.getOffsetX();
    this.stringStartY[0] = viewspacePos.y + GPU.getOffsetY();
    this.stringStartX[31] = 240;
    this.stringStartY[31] = 200;

    final Matrix4f transforms = new Matrix4f();
    final Vector2f start = new Vector2f();
    final Vector2f end = new Vector2f();

    for(int i = 0; i < this.stringStartX.length - 1; i++) {
      start.set(this.stringStartX[i], this.stringStartY[i]);
      end.set(this.stringStartX[i + 1], this.stringStartY[i + 1]);
      RENDERER.queueLine(transforms, 10.0f, start, end);
    }
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
        System.out.println("Late");
        this.isAwaitingPress = false;
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

  public void unload() {
    this.reticleBorderShadow.delete();
  }

  @Override
  protected InputPropagation inputActionPressed(InputAction action, boolean repeat) {
    if(action == TIDES_INPUT_ACTION.get()) {
      if(this.isAwaitingPress) {
        this.isAwaitingPress = false;
        this.lastHitStatus = AdditionLastHitSuccessStatus.SUCCESS;
        this.numFramesToRenderInnerSquare = 2;
        System.out.println("Success");
      } else if(!this.actionList.isEmpty()) {
        this.lastHitStatus = AdditionLastHitSuccessStatus.EARLY;
        this.numFramesToRenderInnerSquare = 2;
        this.actionList.removeFirst();
        System.out.println("Early");
      }
      return InputPropagation.HANDLED;
    } else if(action == TIDES_INPUT_FISH.get()) {
      this.addHit();
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
