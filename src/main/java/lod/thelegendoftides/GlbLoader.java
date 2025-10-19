package lod.thelegendoftides;

import legend.core.gpu.Bpp;
import legend.core.opengl.Obj;
import legend.core.opengl.PolyBuilder;
import legend.core.opengl.Texture;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIColor4D;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AITexture;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL11C.GL_TRIANGLES;
import static org.lwjgl.stb.STBImage.stbi_failure_reason;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load_from_memory;
import static org.lwjgl.system.MemoryStack.stackPush;

public class GlbLoader {
  private final PolyBuilder builder;
  public Texture texture;
  
  public GlbLoader(final String name, final Path file) {
    this.builder = new PolyBuilder(name, GL_TRIANGLES);

    final Path path = file.toAbsolutePath();
    try(final AIScene scene = Assimp.aiImportFile(path.toString(), 0)) {
      if(scene.mNumTextures() != 0) {
        final AITexture AItexture = AITexture.create(scene.mTextures().get());
        final ByteBuffer imageBuffer = AItexture.pcDataCompressed();
        try(final MemoryStack stack = stackPush()) {
          final IntBuffer w = stack.mallocInt(1);
          final IntBuffer h = stack.mallocInt(1);
          final IntBuffer comp = stack.mallocInt(1);

          final ByteBuffer data = stbi_load_from_memory(imageBuffer, w, h, comp, 3);
          if(data == null) {
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
          }
          
          this.texture = Texture.create(textureBuilder -> {
            textureBuilder.data(data, w.get(0), h.get(0));
          });
          
          stbi_image_free(data);
          this.builder.bpp(Bpp.BITS_24);
        }
      }
      
      for(int meshIndex = 0; meshIndex < scene.mNumMeshes(); meshIndex++) {
        try(final AIMesh mesh = AIMesh.create(scene.mMeshes().get(meshIndex))) {
          final AIFace.Buffer faces = mesh.mFaces();
          final AIVector3D.Buffer vertices = mesh.mVertices();
          final AIVector3D.Buffer normals = mesh.mNormals();
          final AIColor4D.Buffer colours = mesh.mColors(0);
          final AIVector3D.Buffer uvs = mesh.mTextureCoords(0);
          
          while(faces.hasRemaining()) {
            final AIFace face = faces.get();

            for(int i = 0; i < face.mNumIndices(); i++) {
              final int vertexIndex = face.mIndices().get(i);
              final AIVector3D vertex = vertices.get(vertexIndex);
              final AIVector3D normal = normals.get(vertexIndex);
              final AIColor4D colour = colours.get(vertexIndex);
              final AIVector3D uv = uvs.get(vertexIndex);
              
              this.builder.addVertex(vertex.x(), vertex.y(), vertex.z());
              this.builder.normal(normal.x(), normal.y(), normal.z());
              if(this.texture == null) {
                this.builder.rgb(colour.r() / 2, colour.g() / 2, colour.b() / 2);
              } else {
                this.builder.rgb(2.0f, 2.0f, 2.0f);
              }
              this.builder.uv(uv.x(), 1.0f - uv.y());
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
