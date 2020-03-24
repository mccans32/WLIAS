package engine.graphics.renderer;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

import engine.Window;
import engine.graphics.Shader;
import engine.graphics.model.Model;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import java.util.ArrayList;
import math.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

/**
 * The type WorldRenderer.
 */
public class WorldRenderer {
  private Shader shader;
  private Window window;
  private Model model;
  private int textureID;

  public WorldRenderer(Window window, Shader shader) {
    this.shader = shader;
    this.window = window;
  }

  /**
   * Render mesh.
   *
   * @param object the mesh
   */
  public void renderObject(GameObject object, Camera camera) {
    initialise();
    glEnable(GL11.GL_CULL_FACE);
    GL11.glCullFace(GL11.GL_BACK);
    this.model = object.getMesh().getModel();
    this.textureID = object.getMesh().getMaterial().getImage().getTextureID();
    bindModel();
    bindTexture();
    shader.bind();
    setUniforms(object, camera);
    drawObject();
    unbindModel();
    shader.unbind();
    terminate();
  }

  /**
   * Render multiple objects with the same model and image.
   *
   * @param objects the objects
   * @param camera  the camera
   */
  public void renderObjects(ArrayList<GameObject> objects, Camera camera) {
    initialise();
    glEnable(GL11.GL_CULL_FACE);
    GL11.glCullFace(GL11.GL_BACK);
    if (objects.size() > 0) {
      this.model = objects.get(0).getMesh().getModel();
      this.textureID = objects.get(0).getMesh().getMaterial().getImage().getTextureID();
      bindModel();
      bindTexture();
      shader.bind();
      setProjectionUniform();
      setViewUniform(camera);
      for (GameObject object : objects) {
        if (object.isContained(camera.getFrustum())) {
          setModelUniform(object);
          setColourOffsetUniform(object);
          drawObject();
        }
      }
      unbindModel();
      shader.unbind();
    }
    terminate();
  }

  private void bindModel() {
    // Bind Mesh VAO
    GL30.glBindVertexArray(model.getVao());
    // Enable Index 0 for Shaders (Position)
    GL30.glEnableVertexAttribArray(0);
    // Enable Index 1 for Shaders (Colour)
    GL30.glEnableVertexAttribArray(1);
    // Enable Index 2 for Shaders (Texture)
    GL30.glEnableVertexAttribArray(2);
    // Bind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, model.getIbo());
  }

  private void bindTexture() {
    // Set Active Texture
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    // Bind the Texture
    GL13.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
  }

  private void setUniforms(GameObject object, Camera camera) {
    setModelUniform(object);
    setViewUniform(camera);
    setProjectionUniform();
    setColourOffsetUniform(object);
  }

  private void setModelUniform(GameObject object) {
    shader.setUniform(
        "model",
        Matrix4f.transform(
            object.getPosition(),
            object.getRotation(),
            object.getScale()));
  }

  private void setViewUniform(Camera camera) {
    shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
  }

  private void setColourOffsetUniform(GameObject object) {
    shader.setUniform("colourOffset", object.getMesh().getMaterial().getColorOffsetRgba());
  }

  private void setProjectionUniform() {
    shader.setUniform("projection", window.getProjectionMatrix());
  }


  private void unbindModel() {
    // Unbind Indices
    GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    GL30.glDisableVertexAttribArray(0);
    GL30.glDisableVertexAttribArray(1);
    GL30.glDisableVertexAttribArray(2);
    // Unbind VAO
    GL30.glBindVertexArray(0);
  }

  private void drawObject() {
    GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, model.getIndices().length, GL11.GL_UNSIGNED_INT,
        0);
  }

  private void initialise() {
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDisable(GL_DEPTH_TEST);
  }

  private void terminate() {
    glDisable(GL_BLEND);
    glEnable(GL_DEPTH_TEST);
  }
}
