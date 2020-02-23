package math;

import java.util.Objects;

public class Vector2f {
  protected float vectorX;
  protected float vectorY;

  public Vector2f(float x, float y) {
    this.vectorX = x;
    this.vectorY = y;
  }

  /**
   * Add vector 2 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 2 f
   */
  public static Vector2f add(Vector2f vector1, Vector2f vector2) {
    return new Vector2f(
        vector1.getX() + vector2.getX(),
        vector1.getY() + vector2.getY());
  }

  /**
   * Add vector 2 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 2 f
   */
  public static Vector2f add(Vector2f vector1, float num) {
    return new Vector2f(
        vector1.getX() + num,
        vector1.getY() + num);
  }

  public void add(Vector2f vector) {
    this.vectorX += vector.getX();
    this.vectorY += vector.getY();
  }

  public void add(float val) {
    this.vectorX += val;
    this.vectorY += val;
  }

  /**
   * Subtract vector 2 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 2 f
   */
  public static Vector2f subtract(Vector2f vector1, Vector2f vector2) {
    return new Vector2f(
        vector1.getX() - vector2.getX(),
        vector1.getY() - vector2.getY());
  }

  /**
   * Subtract vector 2 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 2 f
   */
  public static Vector2f subtract(Vector2f vector1, float num) {
    return new Vector2f(
        vector1.getX() - num,
        vector1.getY() - num);
  }

  public void subtract(Vector2f vector) {
    this.vectorX -= vector.getX();
    this.vectorY -= vector.getY();
  }

  public void subtract(float val) {
    this.vectorX -= val;
    this.vectorY -= val;
  }

  /**
   * Multiply vector 2 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 2 f
   */
  public static Vector2f multiply(Vector2f vector1, Vector2f vector2) {
    return new Vector2f(
        vector1.getX() * vector2.getX(),
        vector1.getY() * vector2.getY());
  }

  /**
   * Multiply vector 2 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 2 f
   */
  public static Vector2f multiply(Vector2f vector1, float num) {
    return new Vector2f(
        vector1.getX() * num,
        vector1.getY() * num);
  }

  public void multiply(Vector2f vector) {
    this.vectorX *= vector.getX();
    this.vectorY *= vector.getY();
  }

  public void multiply(float val) {
    this.vectorX *= val;
    this.vectorY *= val;
  }

  /**
   * Divide vector 2 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 2 f
   */
  public static Vector2f divide(Vector2f vector1, Vector2f vector2) {
    return new Vector2f(
        vector1.getX() / vector2.getX(),
        vector1.getY() / vector2.getY());
  }

  /**
   * Divide vector 2 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 2 f
   */
  public static Vector2f divide(Vector2f vector1, float num) {
    return new Vector2f(
        vector1.getX() / num,
        vector1.getY() / num);
  }

  public void divide(Vector2f vector) {
    this.vectorX /= vector.getX();
    this.vectorY /= vector.getY();
  }

  public void divide(float val) {
    this.vectorX /= val;
    this.vectorY /= val;
  }

  public static float length(Vector2f vector) {
    return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
  }

  public static Vector2f normalise(Vector2f vector) {
    float len = Vector2f.length(vector);
    return Vector2f.divide(vector, new Vector2f(len, len));
  }

  public static float dot(Vector2f vector1, Vector2f vector2) {
    return vector1.getX() * vector2.getX()
        + vector1.getY() * vector2.getY();
  }

  public void set(Vector2f vector) {
    this.vectorX = vector.getX();
    this.vectorY = vector.getY();
  }

  public void set(float value) {
    this.vectorX = value;
    this.vectorY = value;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Vector2f vector2f = (Vector2f) o;
    return Float.compare(vector2f.vectorX, vectorX) == 0
        && Float.compare(vector2f.vectorY, vectorY) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(vectorX, vectorY);
  }
}