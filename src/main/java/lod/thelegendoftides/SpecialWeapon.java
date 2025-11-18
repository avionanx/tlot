package lod.thelegendoftides;

import legend.core.QueuedModelStandard;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.MV;
import legend.core.opengl.Obj;
import legend.core.opengl.Texture;
import legend.game.types.Model124;
import org.joml.Vector3f;
import org.legendofdragoon.modloader.registries.RegistryId;

import java.nio.file.Path;

import static legend.core.GameEngine.GTE;
import static legend.core.GameEngine.RENDERER;
import static legend.core.GameEngine.SCRIPTS;
import static legend.game.Graphics.GsGetLw;
import static legend.game.Graphics.GsSetLightMatrix;
import static legend.game.Graphics.lightColourMatrix_800c3508;
import static legend.game.Graphics.lightDirectionMatrix_800c34e8;
import static legend.game.combat.bent.BattleEntity27c.FLAG_HIDE;

public class SpecialWeapon {
  private GsCOORDINATE2 parent;
  private final Obj model;
  private final int scriptStateIndex;
  private Texture texture;
  private Model124 bentModel;
  private final Vector3f dragoonRotation = new Vector3f();
  public boolean canRender = true;

  public SpecialWeapon(final RegistryId id, final GsCOORDINATE2 parent, final Model124 bentModel, final int charSlot) {
    this.parent = parent;
    this.scriptStateIndex = 6 + charSlot;
    this.bentModel = bentModel;
    
    final GlbLoader loader = new GlbLoader(id.entryId(), Path.of("mods", "tlot", "models", id.entryId() + ".glb"));
    this.model = loader.build();
    this.texture = loader.texture;
  }

  public void unload() {
    this.model.delete();
    this.texture.delete();
  }

  public void setParent(final GsCOORDINATE2 parent, final Model124 bentModel) {
    this.parent = parent;
    this.bentModel = bentModel;
  }
  
  public void withDragoonRotation(final Vector3f newRotation) {
    this.dragoonRotation.set(newRotation);
  }
  
  public void render() {
    if(!this.canRender || (SCRIPTS.getState(this.scriptStateIndex).getStor(0x7) & FLAG_HIDE) != 0) {
      return;
    }
    
    this.parent.flg = 0;

    final MV lw = new MV();
    GsGetLw(this.parent, lw);
    GsSetLightMatrix(lw);
    lw
      .scale(800.0f)
      .rotateX(this.dragoonRotation.x)
      .rotateY(this.dragoonRotation.y)
      .rotateZ(this.dragoonRotation.z)
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
