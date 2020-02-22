package math;

/**
 * The type Vector 3 f.
 */
public class Vector3f extends Vector2f {
  private float vectorZ;

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
   * Set.
   *
   * @param x the x
   * @param y the y
   * @param z the z
   */
  public void set(float x, float y, float z) {
    this.vectorX = x;
    this.vectorY = y;
    this.vectorZ = z;
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

  /**
   * Add.
   *
   * @param x the x
   * @param y the y
   * @param z the z
   */
  public void add(float x, float y, float z) {
    this.vectorX += x;
    this.vectorY += y;
    this.vectorZ += z;
  }
}
