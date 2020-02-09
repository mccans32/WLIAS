package math;

public class Vector2f {
  protected float vectorX;
  protected float vectorY;

  public Vector2f(float x, float y) {
    this.vectorX = x;
    this.vectorY = y;
  }

  public void set(float x, float y) {
    this.vectorX = x;
    this.vectorY = y;
  }

  public float getX() {
    return this.vectorX;
  }

  public void setX(float x) {
    this.vectorX = x;
  }

  public float getY() {
    return this.vectorY;
  }

  public void setY(float y) {
    this.vectorY = y;
  }
}