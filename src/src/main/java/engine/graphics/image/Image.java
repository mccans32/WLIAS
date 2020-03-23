package engine.graphics.image;

import engine.graphics.Material;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Image {
  private static final float ANISOTROPIC_WEIGHT = 4f;
  private String texturePath = "/images/default_texture.png";
  private Texture texture;
  private float height;
  private float imageHeight;
  private float width;
  private float imageWidth;
  private int textureID;

  public Image(String texturePath) {
    this.texturePath = texturePath;
  }

  public Image() {
  }

  public String getTexturePath() {
    return texturePath;
  }

  public Texture getTexture() {
    return texture;
  }

  public float getHeight() {
    return height;
  }

  public float getImageHeight() {
    return imageHeight;
  }

  public float getWidth() {
    return width;
  }

  public float getImageWidth() {
    return imageWidth;
  }

  public int getTextureID() {
    return textureID;
  }

  /**
   * Load the texture.
   */
  public void load() {
    try {
      Texture texture = TextureLoader.getTexture(FilenameUtils.getExtension(texturePath),
          Material.class.getResourceAsStream(texturePath), GL11.GL_NEAREST);
      applyMipMap();
      applyAnisotropicFiltering();
      this.texture = texture;
      this.imageHeight = texture.getImageHeight();
      this.height = texture.getHeight();
      this.imageWidth = texture.getImageWidth();
      this.width = texture.getWidth();
      this.textureID = texture.getTextureID();
    } catch (IOException e) {
      System.err.println("Could not access path: " + texturePath);
    }
  }

  public void destroy() {
    GL13.glDeleteTextures(textureID);
  }

  private void applyMipMap() {
    GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
        GL11.GL_LINEAR_MIPMAP_LINEAR);
  }

  private void applyAnisotropicFiltering() {
    // check to see if we can apply anisotropic filtering
    if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
      // Compare our Anisotropic Weight against the Max Supported Weight by the system
      float supportedWeight =
          GL11.glGetFloat(EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
      float weight = Math.min(ANISOTROPIC_WEIGHT, supportedWeight);
      GL11.glTexParameterf(GL11.GL_TEXTURE_2D,
          EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, weight);
    } else {
      System.out.println("Could not apply Anisotropic Filtering");
    }
  }
}
