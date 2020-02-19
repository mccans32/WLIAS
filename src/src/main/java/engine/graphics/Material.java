package engine.graphics;

import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Material {
  private String path;
  private float width;
  private float height;
  private int textureID;

  /**
   * Instantiates a new Material.
   *
   * @param path the path
   */
  public Material(String path) {
    this.path = path;
  }

  /**
   * Create.
   */
  public void create() {
    try {
      Texture texture = TextureLoader.getTexture(FilenameUtils.getExtension(path),
          Material.class.getResourceAsStream(path), GL11.GL_NEAREST);
      this.height = texture.getHeight();
      this.width = texture.getWidth();
      this.textureID = texture.getTextureID();
    } catch (IOException e) {
      System.err.println("Could not access path: " + path);
    }
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
}