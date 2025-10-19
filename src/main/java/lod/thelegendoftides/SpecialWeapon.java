package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.MV;
import legend.core.opengl.Obj;
import legend.core.opengl.Texture;
import legend.game.types.Model124;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.nio.file.Path;

import static legend.core.GameEngine.GTE;
import static legend.core.GameEngine.RENDERER;
import static legend.game.Scus94491BpeSegment_8003.GsGetLw;
import static legend.game.Scus94491BpeSegment_8003.GsSetLightMatrix;
import static legend.game.Scus94491BpeSegment_800c.lightColourMatrix_800c3508;
import static legend.game.Scus94491BpeSegment_800c.lightDirectionMatrix_800c34e8;

public class SpecialWeapon {
  private final GsCOORDINATE2 parent;
  private final Obj model;
  private Texture texture;
  private final Model124 bentModel;

  public SpecialWeapon(final GsCOORDINATE2 parent, final RegistryId id, final Model124 bentModel) {
    this.parent = parent;
    final GlbLoader loader = new GlbLoader(id.entryId(), Path.of("mods", "tlot", "models", id.entryId() + ".glb"));
    this.model = loader.build();
    this.texture = loader.texture;
    this.bentModel = bentModel;
  }

  public void unload() {

  }

  public void render() {
    this.parent.flg = 0;

    final MV lw = new MV();
    GsGetLw(this.parent, lw);
    GsSetLightMatrix(lw);
    lw
      .scale(800.0f)
    ;

    final var queuedModel = RENDERER.queueModel(this.model, lw, QueuedModelStandard.class)
      .depthOffset(bentModel.zOffset_a0)
         .lightDirection(lightDirectionMatrix_800c34e8)
         .lightColour(lightColourMatrix_800c3508)
          .backgroundColour(GTE.backgroundColour)
    ;
    
    if(this.texture != null) {
      queuedModel.texture(this.texture);
    }
  }
}
