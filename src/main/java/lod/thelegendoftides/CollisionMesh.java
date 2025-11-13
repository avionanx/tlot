package lod.thelegendoftides;

import legend.core.IoHelper;
import legend.core.gte.GsCOORDINATE2;
import legend.core.gte.MV;
import legend.core.gte.ModelPart10;
import legend.game.tmd.TmdObjTable1c;
import org.joml.Vector3f;

import static legend.game.Graphics.GsGetLw;


public class CollisionMesh {
  private final GsCOORDINATE2 coord2;
  private final Triangle[] triangles;

  public CollisionMesh(final ModelPart10 part) {
    this.coord2 = part.coord2_04;
    final TmdObjTable1c objTable = part.tmd_08;

    int count = 0;

    for(int primitiveIndex = 0; primitiveIndex < objTable.primitives_10.length; primitiveIndex++) {
      final TmdObjTable1c.Primitive primitive = objTable.primitives_10[primitiveIndex];
      final int command = primitive.header() & 0xff04_0000;
      final int primitiveId = command >>> 24;
      final boolean quad = (primitiveId & 0b1000) != 0;
      final int triangleCount = quad ? 2 : 1;

      count += primitive.data().length * triangleCount;
    }

    this.triangles = new Triangle[count];
    final Vector3f[] vertices = new Vector3f[4];
    int triangleIndex = 0;

    for(int primitiveIndex = 0; primitiveIndex < objTable.primitives_10.length; primitiveIndex++) {
      final TmdObjTable1c.Primitive primitive = objTable.primitives_10[primitiveIndex];

      final int command = primitive.header() & 0xff04_0000;
      final int primitiveId = command >>> 24;

      final boolean shaded = (command & 0x4_0000) != 0;
      final boolean gourad = (primitiveId & 0b1_0000) != 0;
      final boolean quad = (primitiveId & 0b1000) != 0;
      final boolean textured = (primitiveId & 0b100) != 0;
      final boolean lit = (primitiveId & 0b1) == 0;

      final int vertexCount = quad ? 4 : 3;

      for(int i = 0; i < primitive.data().length; i++) {
        final byte[] data = primitive.data()[i];
        int primitivesOffset = 0;

        if(textured) {
          primitivesOffset += vertexCount * 4;
        }

        if(shaded || !lit) {
          primitivesOffset += vertexCount * 4;
        } else if(!textured) {
          primitivesOffset += 4;
        }

        for(int tmdVertexIndex = 0; tmdVertexIndex < vertexCount; tmdVertexIndex++) {
          if(lit && (gourad || tmdVertexIndex == 0)) {
            primitivesOffset += 2;
          }

          final int vertexIndex = IoHelper.readUShort(data, primitivesOffset);
          primitivesOffset += 2;

          vertices[tmdVertexIndex] = objTable.vert_top_00[vertexIndex];
        }

        this.triangles[triangleIndex++] = new Triangle(vertices[0], vertices[1], vertices[2]);

        if(quad) {
          this.triangles[triangleIndex++] = new Triangle(vertices[1], vertices[2], vertices[3]);
        }
      }
    }
  }

  private final MV transforms = new MV();
  private final Vector3f v0 = new Vector3f();
  private final Vector3f v1 = new Vector3f();
  private final Vector3f v2 = new Vector3f();

  public boolean checkCollision(final Vector3f origin, final Vector3f direction) {
    GsGetLw(this.coord2, this.transforms);

    for(final Triangle triangle : this.triangles) {
//      Transformations.toScreenspace(vertices[0], transforms, v0);
//      Transformations.toScreenspace(vertices[1], transforms, v1);
//      Transformations.toScreenspace(vertices[2], transforms, v2);

      triangle.v0.mul(this.transforms, this.v0);
      triangle.v1.mul(this.transforms, this.v1);
      triangle.v2.mul(this.transforms, this.v2);

      if(FishMath.rayTriangleIntersect(origin, direction, this.v0, this.v1, this.v2, 0.0f)) {
//        RENDERER.queueLine(new Matrix4f(), 10, v0, v1).screenspaceOffset(GPU.getOffsetX(), GPU.getOffsetY()).translucency(Translucency.B_PLUS_F).colour(0.25f, 0.25f, 0.25f);
//        RENDERER.queueLine(new Matrix4f(), 10, v1, v2).screenspaceOffset(GPU.getOffsetX(), GPU.getOffsetY()).translucency(Translucency.B_PLUS_F).colour(0.25f, 0.25f, 0.25f);
//        RENDERER.queueLine(new Matrix4f(), 10, v2, v0).screenspaceOffset(GPU.getOffsetX(), GPU.getOffsetY()).translucency(Translucency.B_PLUS_F).colour(0.25f, 0.25f, 0.25f);

//        final Vector3f bobberWorldPos = new Vector3f();
//        final Vector2f bobberViewPos = new Vector2f();
//        Transformations.toScreenspace(bobberWorldPos, this.fishingRod.bobberCoord2, bobberViewPos);

//        RENDERER.queueLine(new Matrix4f(), 10, bobberViewPos, bobberViewPos.add(0, -10.0f, new Vector2f())).screenspaceOffset(GPU.getOffsetX(), GPU.getOffsetY()).colour(1, 0, 1);
        return true;
      }
    }

    return false;
  }

  private static class Triangle {
    public final Vector3f v0;
    public final Vector3f v1;
    public final Vector3f v2;

    private Triangle(final Vector3f v0, final Vector3f v1, final Vector3f v2) {
      this.v0 = v0;
      this.v1 = v1;
      this.v2 = v2;
    }
  }
}
