package math;

// A Matrix4f is visualised as a 2D Array, However the GPU requires the vector to be in 1D
// Matrix4f is read in by Column Major Order so is set by (col, row);
public class Matrix4f {

  private static final int SIZE = 4;
  private float[] elements = new float[SIZE * SIZE];

  public Matrix4f() {
  }

  /**
   * Instantiates a new Matrix 4 f.
   *
   * @param elements the elements
   */
  public Matrix4f(float[] elements) {
    if (elements.length != SIZE * SIZE) {
      throw new IllegalArgumentException(
          String.format("Passed Elements must be of size %d", SIZE * SIZE));
    }

  }

  /**
   * Identity matrix 4 f.
   *
   * @return the matrix 4 f
   */
  public static Matrix4f identity() {
    Matrix4f result = new Matrix4f();
    result.set(0, 0, 1);
    result.set(1, 1, 1);
    result.set(2, 2, 1);
    result.set(3, 3, 1);
    return result;
  }

  /**
   * Projection matrix 4 f.
   *
   * @param fov    the fov
   * @param aspect the aspect
   * @param near   the near
   * @param far    the far
   * @return the matrix 4 f
   */
  public static Matrix4f projection(float fov, float aspect, float near, float far) {
    Matrix4f result = Matrix4f.identity();

    float tanFov = (float) Math.tan(Math.toRadians(fov / 2));
    float range = far - near;

    result.set(0, 0, 1.0f / (aspect * tanFov));
    result.set(1, 1, 1.0f / tanFov);
    result.set(2, 2, -((far + near) / range));
    result.set(2, 3, -1.0f);
    result.set(3, 2, -((2 * far * near) / range));
    result.set(3, 3, 0.0f);

    return result;
  }

  public static Matrix4f orthographic(float left, float right, float bottom, float top, float near,
      float far) {
    Matrix4f result = Matrix4f.identity();

    float tx = - (right + left) / (right - left);
    float ty = - (top + bottom) / (top - bottom);
    float tz = - (far + near) / (far - near);

    result.set(0, 0, 2 / (right - left));
    result.set(1, 1, 2 / (top - bottom));
    result.set(2, 2, -2 / (far - near));
    result.set(3, 0, tx);
    result.set(3, 1, ty);
    result.set(3, 2, tz);
    result.set(3, 3, 1);

    return result;
  }

  /**
   * View matrix 4 f.
   *
   * @param position the position
   * @param rotation the rotation
   * @return the matrix 4 f
   */
  public static Matrix4f view(Vector3f position, Vector3f rotation) {
    Vector3f negative = new Vector3f(-position.getX(), -position.getY(), -position.getZ());
    Matrix4f translationMatrix = Matrix4f.translate(negative);
    Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
    Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
    Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));

    Matrix4f rotationMatrix = Matrix4f.multiply(
        rotZMatrix, Matrix4f.multiply(
            rotYMatrix,
            rotXMatrix));

    return Matrix4f.multiply(translationMatrix, rotationMatrix);
  }

  /**
   * Multiply matrix 4 f.
   *
   * @param matrix the matrix
   * @param other  the other
   * @return the matrix 4 f
   */
  public static Matrix4f multiply(Matrix4f matrix, Matrix4f other) {
    Matrix4f result = Matrix4f.identity();

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        result.set(i, j, matrix.get(i, 0) * other.get(0, j)
            + matrix.get(i, 1) * other.get(1, j)
            + matrix.get(i, 2) * other.get(2, j)
            + matrix.get(i, 3) * other.get(3, j));
      }
    }

    return result;
  }

  /**
   * Translate matrix 4 f.
   *
   * @param translation the translate
   * @return the matrix 4 f
   */
  public static Matrix4f translate(Vector3f translation) {
    Matrix4f result = Matrix4f.identity();
    result.set(3, 0, translation.getX());
    result.set(3, 1, translation.getY());
    result.set(3, 2, translation.getZ());
    return result;
  }

  public static Matrix4f translate(Vector2f translation) {
    return translate(new Vector3f(translation.getX(), translation.getY(), 0f));
  }

  /**
   * Rotate matrix 4 f.
   *
   * @param angle the angle
   * @param axis  the axis
   * @return the matrix 4 f
   */
  public static Matrix4f rotate(float angle, Vector3f axis) {
    Matrix4f result = Matrix4f.identity();

    float cos = (float) Math.cos(Math.toRadians(angle));
    float sin = (float) Math.sin(Math.toRadians(angle));
    float inversion = 1 - cos;

    result.set(0, 0, cos + axis.getX() * axis.getX() * inversion);
    result.set(0, 1, axis.getX() * axis.getY() * inversion - axis.getZ() * sin);
    result.set(0, 2, axis.getX() * axis.getZ() * inversion + axis.getY() * sin);
    result.set(1, 0, axis.getY() * axis.getX() * inversion + axis.getZ() * sin);
    result.set(1, 1, cos + axis.getY() * axis.getY() * inversion);
    result.set(1, 2, axis.getY() * axis.getZ() * inversion - axis.getX() * sin);
    result.set(2, 0, axis.getZ() * axis.getX() * inversion - axis.getY() * sin);
    result.set(2, 1, axis.getZ() * axis.getY() * inversion + axis.getX() * sin);
    result.set(2, 2, cos + axis.getZ() * axis.getZ() * inversion);

    return result;
  }

  public static Matrix4f rotate(float angle, Vector2f axis) {
    return rotate(angle, new Vector3f(axis.getX(), axis.getY(), 0f));
  }

  /**
   * Scale matrix 4 f.
   *
   * @param scalar the scalar
   * @return the matrix 4 f
   */
  public static Matrix4f scale(Vector3f scalar) {
    Matrix4f result = Matrix4f.identity();

    result.set(0, 0, scalar.getX());
    result.set(1, 1, scalar.getY());
    result.set(2, 2, scalar.getZ());

    return result;
  }

  public static Matrix4f scale(Vector2f scalar) {
    return scale(new Vector3f(scalar.getX(), scalar.getY(), 1f));
  }

  /**
   * Transform matrix 4 f.
   *
   * @param position the position
   * @param rotation the rotation
   * @param scale    the scale
   * @return the matrix 4 f
   */
  public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
    Matrix4f translationMatrix = Matrix4f.translate(position);
    Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1, 0, 0));
    Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0, 1, 0));
    Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0, 0, 1));
    Matrix4f scaleMatrix = Matrix4f.scale(scale);

    Matrix4f rotationMatrix = Matrix4f.multiply(
        rotXMatrix,
        Matrix4f.multiply(
            rotYMatrix,
            rotZMatrix));

    return Matrix4f.multiply(
        translationMatrix,
        Matrix4f.multiply(rotationMatrix, scaleMatrix));
  }

  /**
   * Transform matrix 4 f.
   *
   * @param position the position
   * @param rotation the rotation
   * @param scale    the scale
   * @return the matrix 4 f
   */
  public static Matrix4f transform(Vector2f position, Vector2f rotation, Vector2f scale) {
    return transform(
        new Vector3f(position.getX(), position.getY(), 0f),
        new Vector3f(rotation.getX(), position.getY(), 0f),
        new Vector3f(scale.getX(), scale.getY(), 1f));
  }

  public static int getSize() {
    return SIZE;
  }

  public static int calculateIndex(int col, int row) {
    return row * SIZE + col;
  }

  public float get(int col, int row) {
    return elements[calculateIndex(col, row)];
  }

  public void set(int col, int row, float value) {
    elements[calculateIndex(col, row)] = value;
  }

  public float[] getAll() {
    return elements.clone();
  }

}
