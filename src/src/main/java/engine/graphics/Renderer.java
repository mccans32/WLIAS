package engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * The type Renderer.
 */
public class Renderer {
  /**
   * Render mesh.
   *
   * @param mesh the mesh
   */
  public void renderMesh(Mesh mesh) {
    // Bind Mesh VAO
    GL30.glBindVertexArray(mesh.getVao());
    GL30.glEnableVertexAttribArray(0);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
    GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
    // Unbind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    // Unbind VAO
    GL30.glBindVertexArray(0);
  }
}
