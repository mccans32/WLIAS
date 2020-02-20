package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Matrix4FTests {
  private static final int TEST_SIZE = 4;
  private Matrix4f testMatrix;
  private int[][] testDimensionsAndIndex = {{1, 1, 5}, {0, 2, 2}, {2, 3, 11}};

  @BeforeEach
  public void setup() {
    testMatrix = new Matrix4f();
  }

  @Test
  public void testSize() {
    assertEquals(Matrix4f.getSize(), TEST_SIZE);
  }

  @Test
  public void testCalculateIndex() {
    for (int[] values : testDimensionsAndIndex) {
      int col = values[0];
      int row = values[1];
      int index = values[2];
      assertEquals(Matrix4f.calculateIndex(col, row), index);
    }
  }

  @Test
  public void testSettersAndGetters() {
    for (int[] values : testDimensionsAndIndex) {
      int col = values[0];
      int row = values[1];
      int value = values[2];
      testMatrix.setElement(col, row, value);
      assertEquals(testMatrix.getElement(col, row), value);
    }
    System.out.print(Arrays.toString(testMatrix.getElements()));
  }
}