package math;

import java.util.Objects;

/**
 * The type Vector 3 f.
 */
public class Vector3f extends Vector2f {
  protected float vectorZ;

  /**
   * Instantiates a new Vector 3 f.
   *
   * @param x the x
   * @param y the y
   * @param z the z
   */
  public Vector3f(float x, float y, float z) {
    super(x, y);
    this.vectorZ = z;
  }

  /**
   * Add vector 3 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 3 f
   */
  public static Vector3f add(Vector3f vector1, Vector3f vector2) {
    return new Vector3f(
        vector1.getX() + vector2.getX(),
        vector1.getY() + vector2.getY(),
        vector1.getZ() + vector2.getZ());
  }

  /**
   * Add vector 3 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 3 f
   */
  public static Vector3f add(Vector3f vector1, float num) {
    return new Vector3f(
        vector1.getX() + num,
        vector1.getY() + num,
        vector1.getZ() + num);
  }

  /**
   * Add.
   *
   * @param vector the vector
   */
  public void add(Vector3f vector) {
    this.vectorX += vector.getX();
    this.vectorY += vector.getY();
    this.vectorZ += vector.getZ();
  }

  /**
   * Add.
   *
   * @param val the val
   */
  public void add(float val) {
    this.vectorX += val;
    this.vectorY += val;
    this.vectorZ += val;
  }

  /**
   * Subtract vector 3 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 3 f
   */
  public static Vector3f subtract(Vector3f vector1, Vector3f vector2) {
    return new Vector3f(
        vector1.getX() - vector2.getX(),
        vector1.getY() - vector2.getY(),
        vector1.getZ() - vector2.getZ());
  }

  /**
   * Subtract vector 3 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 3 f
   */
  public static Vector3f subtract(Vector3f vector1, float num) {
    return new Vector3f(
        vector1.getX() - num,
        vector1.getY() - num,
        vector1.getZ() - num);
  }

  /**
   * Subtract.
   *
   * @param vector the vector
   */
  public void subtract(Vector3f vector) {
    this.vectorX -= vector.getX();
    this.vectorY -= vector.getY();
    this.vectorZ -= vector.getZ();
  }

  /**
   * Subtract.
   *
   * @param val the val
   */
  @Override
  public void subtract(float val) {
    this.vectorX -= val;
    this.vectorY -= val;
    this.vectorZ -= val;
  }

  /**
   * Multiply vector 3 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 3 f
   */
  public static Vector3f multiply(Vector3f vector1, Vector3f vector2) {
    return new Vector3f(
        vector1.getX() * vector2.getX(),
        vector1.getY() * vector2.getY(),
        vector1.getZ() * vector2.getZ());
  }

  /**
   * Multiply vector 3 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 3 f
   */
  public static Vector3f multiply(Vector3f vector1, float num) {
    return new Vector3f(
        vector1.getX() * num,
        vector1.getY() * num,
        vector1.getZ() * num);
  }

  /**
   * Multiply.
   *
   * @param vector the vector
   */
  public void multiply(Vector3f vector) {
    this.vectorX *= vector.getX();
    this.vectorY *= vector.getY();
    this.vectorZ *= vector.getZ();
  }

  /**
   * Multiply.
   *
   * @param val the val
   */
  @Override
  public void multiply(float val) {
    this.vectorX *= val;
    this.vectorY *= val;
    this.vectorZ *= val;
  }

  /**
   * Divide vector 3 f.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the vector 3 f
   */
  public static Vector3f divide(Vector3f vector1, Vector3f vector2) {
    return new Vector3f(
        vector1.getX() / vector2.getX(),
        vector1.getY() / vector2.getY(),
        vector1.getZ() / vector2.getZ());
  }

  /**
   * Divide vector 3 f.
   *
   * @param vector1 the vector 1
   * @param num     the num
   * @return the vector 3 f
   */
  public static Vector3f divide(Vector3f vector1, float num) {
    return new Vector3f(
        vector1.getX() / num,
        vector1.getY() / num,
        vector1.getZ() / num);
  }

  /**
   * Divide.
   *
   * @param vector the vector
   */
  public void divide(Vector3f vector) {
    this.vectorX /= vector.getX();
    this.vectorY /= vector.getY();
    this.vectorZ /= vector.getZ();
  }

  /**
   * Divide.
   *
   * @param val the val
   */
  @Override
  public void divide(float val) {
    this.vectorX /= val;
    this.vectorY /= val;
    this.vectorZ /= val;
  }

  /**
   * Length float.
   *
   * @param vector the vector
   * @return the float
   */
  public static float length(Vector3f vector) {
    return (float) Math.sqrt(
        (vector.getX() * vector.getX())
            + (vector.getY() * vector.getY())
            + (vector.getZ() * vector.getZ()));
  }

  public static Vector3f normalise(Vector3f vector) {
    float len = Vector3f.length(vector);
    return Vector3f.divide(vector, new Vector3f(len, len, len));
  }

  /**
   * Dot float.
   *
   * @param vector1 the vector 1
   * @param vector2 the vector 2
   * @return the float
   */
  public static float dot(Vector3f vector1, Vector3f vector2) {
    return vector1.getX() * vector2.getX()
        + vector1.getY() * vector2.getY()
        + vector1.getZ() * vector2.getZ();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Vector3f vector3f = (Vector3f) o;
    return Float.compare(vector3f.vectorZ, vectorZ) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), vectorZ);
  }

  /**
   * Set.
   *
   * @param vector the vector
   */
  public void set(Vector3f vector) {
    this.vectorX = vector.getX();
    this.vectorY = vector.getY();
    this.vectorZ = vector.getZ();
  }

  /**
   * Set.
   *
   * @param value the value
   */
  public void set(float value) {
    this.vectorX = value;
    this.vectorY = value;
    this.vectorZ = value;
  }

  /**
   * Gets z.
   *
   * @return the z
   */
  public float getZ() {
    return this.vectorZ;
  }

  /**
   * Sets z.
   *
   * @param z the z
   */
  public void setZ(float z) {
    this.vectorZ = z;
  }

  @Override
  public String toString() {
    return "(" + vectorX + ", " + vectorY + ", " + vectorZ + ")";
  }

  public Vector3f copy() {
    return new Vector3f(this.getX(), this.getY(), this.getZ());
  }
}
