package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gte.MV;
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

    this.transforms.scaling(12.0f);
    this.transforms.transfer.set(screenSpaceTransforms.x, screenSpaceTransforms.y, 0.0f);
  }

  public void render() {
    RENDERER.queueOrthoModel(RENDERER.centredQuadOpaque, this.transforms, QueuedModelStandard.class)
      .screenspaceOffset(GPU.getOffsetX() + GTE.getScreenOffsetX(), GPU.getOffsetY() + GTE.getScreenOffsetY())
      .colour(1.0f, 1.0f, 1.0f)
      .translucency(Translucency.HALF_B_PLUS_HALF_F)
      .alpha(0.5f);
  }
}
