package math;

public class Vector4f extends Vector3f {
  private float vectorW;

  public Vector4f(float x, float y, float z, float w) {
    super(x, y, z);
    this.vectorW = w;
  }

  public float getW() {
    return vectorW;
  }
}
