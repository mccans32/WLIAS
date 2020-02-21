package engine.graphics;

import engine.objects.GameObject;
import java.sql.Array;
import java.util.Arrays;
import math.Matrix4f;
import math.Vector2f;
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
   * @param object the mesh
   */
  public void renderObject(GameObject object) {
    bindObject(object);
    drawObject(object);
    unbindObject();
  }

  private void bindObject(GameObject object) {
    // Bind Mesh VAO
    GL30.glBindVertexArray(object.getMesh().getVao());
    // Enable Index 0 for Shaders (Position)
    GL30.glEnableVertexAttribArray(0);
    // Enable Index 1 for Shaders (Colour)
    GL30.glEnableVertexAttribArray(1);
    // Enable Index 1 for Shaders (Texture)
    GL30.glEnableVertexAttribArray(2);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIbo());
    // Set Active Texture
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    // Bind the Texture
    GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
    //Bind Shader
    shader.bind();
    // Set Model Uniform
    System.out.println(Arrays.toString(Matrix4f.rotate(0, new Vector2f(0,0)).getAll()));
    shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
  }

  private void unbindObject() {
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

  private void drawObject(GameObject object) {
    GL11.glDrawElements(
        GL11.GL_TRIANGLE_STRIP,
        object.getMesh().getIndices().length,
        GL11.GL_UNSIGNED_INT,
        0);
  }
}
