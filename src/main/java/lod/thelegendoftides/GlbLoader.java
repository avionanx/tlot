package lod.thelegendoftides;

import legend.core.opengl.Obj;
import legend.core.opengl.PolyBuilder;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import java.nio.file.Path;

import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;

public class GlbLoader {
  private final PolyBuilder builder;

  public GlbLoader(final String name, final Path file) {
    this.builder = new PolyBuilder(name, GL_TRIANGLES);

    final Path path = file.toAbsolutePath();
    try(final AIScene scene = Assimp.aiImportFile(path.toString(), 0)) {
      for(int meshIndex = 0; meshIndex < scene.mNumMeshes(); meshIndex++) {
        try(final AIMesh mesh = AIMesh.create(scene.mMeshes().get(meshIndex))) {
          final AIFace.Buffer faces = mesh.mFaces();
          final AIVector3D.Buffer vertices = mesh.mVertices();
          final AIVector3D.Buffer normals = mesh.mNormals();
          final AIColor4D.Buffer colours = mesh.mColors(0);

          while(faces.hasRemaining()) {
            final AIFace face = faces.get();

            for(int i = 0; i < face.mNumIndices(); i++) {
              final int vertexIndex = face.mIndices().get(i);
              final AIVector3D vertex = vertices.get(vertexIndex);
              final AIVector3D normal = normals.get(vertexIndex);
              final AIColor4D colour = colours.get(vertexIndex);
              this.builder.addVertex(vertex.x(), vertex.y(), vertex.z());
              this.builder.normal(normal.x(), normal.y(), normal.z());
              this.builder.rgb(colour.r() / 2, colour.g() / 2, colour.b() / 2);
            }
          }
        }
      }
    } catch(final Throwable t) {
      System.err.println("Failed to load " + file);
      t.printStackTrace(System.err);
    }
  }

  public Obj build() {
    return this.builder.build();
  }
}
