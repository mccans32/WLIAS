package engine.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.newdawn.slick.opengl.renderer.SGL.GL_ONE_MINUS_SRC_ALPHA;
import static org.newdawn.slick.opengl.renderer.SGL.GL_SRC_ALPHA;

import engine.Window;
import engine.graphics.Shader;
import engine.objects.gui.HudText;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * The Renderer for rendering {@link HudText} objects.
 */
public class TextRenderer {
  private static final float DEFAULT_POSITION_Z_VALUE = 1f;
  private static final float DEFAULT_SCALE_Z_VALUE = 1f;
  private static Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private Shader shader;
  private Window window;

  public TextRenderer(Window window, Shader shader) {
    this.shader = shader;
    this.window = window;
  }


  /**
   * Render mesh.
   *
   * @param object the object.
   */
  public void renderObject(HudText object) {
    initialise();
    bindObject(object);
    drawObject(object);
    unbindObject();
    terminate();
  }

  private void initialise() {
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  private void terminate() {
    glDisable(GL_BLEND);
  }

  private void bindObject(HudText object) {
    // Bind Mesh VAO
    GL30.glBindVertexArray(object.getMesh().getVao());
    // Enable Index 0 for Shaders (Position)
    GL30.glEnableVertexAttribArray(0);
    // Enable Index 1 for Shaders (Colour)
    GL30.glEnableVertexAttribArray(1);
    // Enable Index 2 for Shaders (Texture)
    GL30.glEnableVertexAttribArray(2);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIbo());
    // Set Active Texture
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    // Bind the Texture
    GL13.glBindTexture(GL11.GL_TEXTURE_2D,
        object.getMesh().getMaterial().getImage().getTextureID());
    //Bind Shader
    shader.bind();
    // Set Uniforms
    setUniforms(object);

  }

  private void setUniforms(HudText object) {
    setModelUniform(object);
    setProjectionUniform();
    setColourOffsetUniform(object);
  }

  private void setModelUniform(HudText object) {
    Vector2f pos = object.getPosition();
    Vector2f scale = object.getScale();
    shader.setUniform(
        "model",

        Matrix4f.transform(
            new Vector3f(pos.getX(), pos.getY(), DEFAULT_POSITION_Z_VALUE),
            DEFAULT_ROTATION,
            new Vector3f(scale.getX(), scale.getY(), DEFAULT_SCALE_Z_VALUE)));
  }

  private void setColourOffsetUniform(HudText object) {
    shader.setUniform("colourOffset", object.getMesh().getMaterial().getColorOffset());
  }

  private void setProjectionUniform() {
    shader.setUniform("projection", window.getOrthographicMatrix());
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

  private void drawObject(HudText object) {
    GL11.glDrawElements(
        GL11.GL_TRIANGLES,
        object.getMesh().getIndices().length,
        GL11.GL_UNSIGNED_INT,
        0);
  }
}