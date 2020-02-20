package math;

// A Matrix4f is visualised as a 2D Array, However the GPU requires the vector to be in 1D
// Indexed using column major order, meaning (0, 2) = 1st Column, 3rd Entry etc.
public class Matrix4f {
  private static final int SIZE = 4;
  private float[] elements = new float[SIZE * SIZE];

  public static int getSize() {
    return SIZE;
  }

  public static int calculateIndex(int col, int row) {
    return col * SIZE + row;
  }

  public float getElement(int col, int row) {
    return elements[calculateIndex(col, row)];
  }

  public void setElement(int col, int row, float value) {
    elements[calculateIndex(col, row)] = value;
  }

  public float[] getElements() {
    return elements.clone();
  }

}
