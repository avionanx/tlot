package lod.thelegendoftides;

import legend.core.MathHelper;
import legend.core.QueuedModelStandard;
import legend.core.Transformations;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.MV;
import legend.core.opengl.Obj;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.file.Path;

import static legend.core.GameEngine.GPU;
import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8003.GsGetLw;

public class FishingRod {
  private final float[] stringX = new float[32];
  private final float[] stringY = new float[32];
  private final float[] stringTempX = new float[32];
  private final float[] stringTempY = new float[32];

  private float segmentLength = 0.1f;
  private float gravity = 0.1f;

  private final Obj rod;
  private final Obj bobber;

  private final GsCOORDINATE2 parent;
  public final GsCOORDINATE2 bobberCoord2 = new GsCOORDINATE2();
  private final MV bobberTransforms = new MV();

  public FishingRod(final GsCOORDINATE2 parent) {
    this.parent = parent;

    // Initialize string
    this.initString();

    // Load rod/bobber models
    this.rod = new GlbLoader("rod", Path.of("rod.glb")).build();
    this.bobber = new GlbLoader("bobber", Path.of("bobber.glb")).build();
  }

  public void delete() {
    this.rod.delete();
    this.bobber.delete();
  }

  public void setSegmentLength(final float segmentLength) {
    this.segmentLength = segmentLength;
  }

  public void setGravity(final float gravity) {
    this.gravity = gravity;
  }

  public void initString() {
    // Seed string X values so the math doesn't NaN
    for(int i = 0; i < this.stringX.length; i++) {
      this.stringX[i] = i;
    }

    // Iterate once to set the start/end points
    this.processString(1);

    // Set the initial points to be a smooth line between the start and end
    final float startX = this.stringX[0];
    final float startY = this.stringY[0];
    final float endX = this.stringX[this.stringX.length - 1];
    final float endY = this.stringY[this.stringY.length - 1];
    final float dx = endX - startX;
    final float dy = endY - startY;
    final float segmentX = dx / this.stringX.length;
    final float segmentY = dy / this.stringY.length;

    for(int i = 1; i < this.stringX.length - 1; i++) {
      this.stringX[i] = this.stringX[i - 1] + segmentX;
      this.stringY[i] = this.stringY[i - 1] + segmentY;
    }
  }

  private void iterateStringPhysics(final Vector2f startPos, final Vector2f endPos) {
    for(int i = 1; i < this.stringX.length - 1; i++) {
      final float dx1 = this.stringX[i - 1] - this.stringX[i];
      final float dy1 = this.stringY[i - 1] - this.stringY[i];
      final float mag1 = (float)java.lang.Math.hypot(dx1, dy1);
      final float extension1 = mag1 - this.segmentLength;

      final float dx2 = this.stringX[i + 1] - this.stringX[i];
      final float dy2 = this.stringY[i + 1] - this.stringY[i];
      final float mag2 = (float)java.lang.Math.hypot(dx2, dy2);
      final float extension2 = mag2 - this.segmentLength;

      final float xv = dx1 / mag1 * extension1 + dx2 / mag2 * extension2;
      final float yv = dy1 / mag1 * extension1 + dy2 / mag2 * extension2 + this.gravity;

      this.stringTempX[i] = this.stringX[i] + xv * 0.5f;
      this.stringTempY[i] = this.stringY[i] + yv * 0.5f;
    }

    System.arraycopy(this.stringTempX, 0, this.stringX, 0, this.stringTempX.length);
    System.arraycopy(this.stringTempY, 0, this.stringY, 0, this.stringTempY.length);

    // Set start and end points
    this.stringX[0] = startPos.x;
    this.stringY[0] = startPos.y;
    this.stringX[31] = endPos.x;
    this.stringY[31] = endPos.y;
  }

  public void processString(final int iterations) {
    final Vector3f rodWorldPos = new Vector3f(1050.0f, 0.0f, 0.0f);
    final Vector2f rodViewPos = new Vector2f();
    Transformations.toScreenspace(rodWorldPos, this.parent, rodViewPos);

    final Vector3f bobberWorldPos = new Vector3f();
    final Vector2f bobberViewPos = new Vector2f();
    Transformations.toScreenspace(bobberWorldPos, this.bobberCoord2, bobberViewPos);

    for(int i = 0; i < iterations; i++) {
      this.iterateStringPhysics(rodViewPos, bobberViewPos);
    }
  }

  public void renderString() {
    final Matrix4f transforms = new Matrix4f();
    final Vector2f start = new Vector2f();
    final Vector2f end = new Vector2f();

    for(int i = 0; i < this.stringX.length - 1; i++) {
      start.set(this.stringX[i], this.stringY[i]);
      end.set(this.stringX[i + 1], this.stringY[i + 1]);
      RENDERER.queueLine(transforms, 200.0f, start, end)
        .screenspaceOffset(GPU.getOffsetX(), GPU.getOffsetY());
    }
  }

  public void renderRod(final float zOffset) {
    this.parent.flg = 0;

    final MV lw = new MV();
    GsGetLw(this.parent, lw);
//    GsSetLightMatrix(lw);
    lw
      .rotateY(-MathHelper.HALF_PI)
      .scale(800.0f)
    ;

    RENDERER.queueModel(this.rod, lw, QueuedModelStandard.class)
      .depthOffset(zOffset)
//      .lightDirection(lightDirectionMatrix_800c34e8)
//      .lightColour(lightColourMatrix_800c3508)
//      .backgroundColour(GTE.backgroundColour)
    ;
  }

  public void renderBobber() {
    this.bobberCoord2.flg = 0;
    GsGetLw(this.bobberCoord2, this.bobberTransforms);

    this.bobberTransforms
      .scale(800.0f)
    ;

    RENDERER.queueModel(this.bobber, this.bobberTransforms, QueuedModelStandard.class);
  }
}
