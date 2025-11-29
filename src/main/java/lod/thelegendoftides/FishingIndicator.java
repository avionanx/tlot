package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
import legend.core.opengl.MeshObj;
import legend.core.opengl.Texture;
import legend.game.types.Translucency;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static legend.core.GameEngine.GPU;
import static legend.core.GameEngine.GTE;
import static legend.core.GameEngine.RENDERER;
import static legend.game.combat.SEffe.transformWorldspaceToScreenspace;

public class FishingIndicator {
  private final MV transforms;

  public FishingIndicator(final Vector3f indicatorPosition) {
    this.transforms = new MV();

    final Vector2f screenSpaceTransforms = new Vector2f();
    transformWorldspaceToScreenspace(indicatorPosition, screenSpaceTransforms);

    this.transforms.scaling(24.0f);
    this.transforms.transfer.set(screenSpaceTransforms.x, screenSpaceTransforms.y, 0.0f);
  }

  public void render(final MeshObj quad, final Texture texture) {
    RENDERER.queueOrthoModel(quad, this.transforms, QueuedModelStandard.class)
      .screenspaceOffset(GPU.getOffsetX() + GTE.getScreenOffsetX(), GPU.getOffsetY() + GTE.getScreenOffsetY())
      .texture(texture)
      .translucency(Translucency.HALF_B_PLUS_HALF_F);
    this.transforms.rotateZ(0.01f);
  }
}
