package engine.graphics;

import engine.graphics.image.Image;
import math.Vector3f;
import math.Vector4f;

public class Material {
  private Vector4f colorOffset = new Vector4f(1, 1, 1, 1);
  private Image image = new Image();

  public Material(Image image) {
    this.image = image;
  }

  public Material(Image image, Vector3f colorOffset) {
    this(image, new Vector4f(colorOffset, 1));
  }

  public Material(Image image, Vector4f colorOffset) {
    this(image);
    this.colorOffset = colorOffset;
  }

  public Material() {
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }


  public Vector4f getColorOffsetRgba() {
    return colorOffset;
  }

  public Vector3f getColorOffsetRgb() {
    return new Vector3f(colorOffset.getX(), colorOffset.getY(), colorOffset.getZ());
  }

  public void setColorOffset(Vector3f colorOffset) {
    this.colorOffset = new Vector4f(colorOffset.getX(), colorOffset.getY(), colorOffset.getZ(), 1);
  }

  public void setColorOffset(Vector4f colorOffset) {
    this.colorOffset = colorOffset;
  }

  public void create() {
    image.load();
  }

  public void destroy() {
    image.destroy();
  }
}