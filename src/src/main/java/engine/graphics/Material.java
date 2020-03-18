package engine.graphics;

import java.io.IOException;
import math.Vector3f;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Material {
  private static final String DEFAULT_PATH = "/images/default_texture.png";
  private String path;
  private float width;
  private float imageWidth;
  private float height;
  private float imageHeight;
  private int textureID;
  private Vector3f colorOffset = new Vector3f(1, 1, 1);

  /**
   * Instantiates a new Material.
   *
   * @param path the path
   */
  public Material(String path) {
    this.path = path;
  }

  public static String getDefaultPath() {
    return DEFAULT_PATH;
  }

  public String getPath() {
    return path;
  }

  /**
   * Create.
   */
  public void create() {
    loadTexture(path);
  }

  /**
   * Destroy.
   */
  public void destroy() {
    GL13.glDeleteTextures(textureID);
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public int getTextureID() {
    return textureID;
  }

  public Vector3f getColorOffset() {
    return colorOffset;
  }

  public void setColorOffset(Vector3f colorOffset) {
    this.colorOffset = colorOffset;
  }

  public float getImageWidth() {
    return imageWidth;
  }

  public float getImageHeight() {
    return imageHeight;
  }

  private void loadTexture(String path) {
    try {
      Texture texture = TextureLoader.getTexture(FilenameUtils.getExtension(path),
          Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
      this.imageHeight = texture.getImageHeight();
      this.height = texture.getHeight();
      this.imageWidth = texture.getImageWidth();
      this.width = texture.getWidth();
      this.textureID = texture.getTextureID();
    } catch (IOException e) {
      System.err.println("Could not access path: " + path);
    }
  }

  /**
   * Sets texture.
   *
   * @param path the path
   */
  public void setTexture(String path) {
    // Assign the New Texture Path
    this.path = path;
    // Load in the new Texture
    loadTexture(path);
  }
}