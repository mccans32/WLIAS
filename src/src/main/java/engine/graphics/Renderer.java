package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * The type Renderer.
 */
public class Renderer {
  private Shader shader;

  public Renderer(Shader shader) {
    this.shader = shader;
  }

  /**
   * Render mesh.
   *
   * @param mesh the mesh
   */
  public void renderMesh(Mesh mesh) {
    bindMesh(mesh);
    drawMesh(mesh);
    unbindMesh(mesh);
  }

  private void bindMesh(Mesh mesh) {
    // Bind Mesh VAO
    GL30.glBindVertexArray(mesh.getVao());
    // Enable Index 0 for Shaders (Position)
    GL30.glEnableVertexAttribArray(0);
    // Enable Index 1 for Shaders (Colour)
    GL30.glEnableVertexAttribArray(1);
    // Enable Index 1 for Shaders (Texture)
    GL30.glEnableVertexAttribArray(2);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
    // Set Active Texture
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    // Bind the Texture
    GL13.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
    //Bind Shader
    shader.bind();
  }

  private void unbindMesh(Mesh mesh) {
    //Unbind Shader
    shader.unbind();
    // Unbind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    GL30.glDisableVertexAttribArray(1);
    GL30.glDisableVertexAttribArray(2);
    // Unbind VAO
    GL30.glBindVertexArray(0);
  }

  private void drawMesh(Mesh mesh) {
    GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
  }
}
