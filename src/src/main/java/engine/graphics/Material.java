package engine.graphics;

import engine.graphics.image.Image;
import math.Vector3f;

public class Material {
  private Vector3f colorOffset = new Vector3f(1, 1, 1);
  private Image image = new Image();

  public Material(Image image) {
    this.image = image;
  }

  public Material(Image image, Vector3f colorOffset) {
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

  public Vector3f getColorOffset() {
    return colorOffset;
  }

  public void setColorOffset(Vector3f colorOffset) {
    this.colorOffset = colorOffset;
  }

  public void create() {
    image.load();
  }

  public void destroy() {
    image.destroy();
  }
}