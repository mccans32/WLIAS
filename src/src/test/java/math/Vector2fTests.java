package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class Vector2fTests {
  private static float[] testXCoordinates = {-6.0f, 1.2f, 0.4f, 0.7f, 1.0f, 2.0f};
  private static float[] testYCoordinates = {-1.9f, 3.4f, 7.8f, 1.1f, 3.1f, 3.1f};

  @Test
  void testInit() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        Vector2f testVector = new Vector2f(testXCoordinate, testYCoordinate);
        assertEquals(testVector.getX(), testXCoordinate);
        assertEquals(testVector.getY(), testYCoordinate);
      }
    }
  }

  @Test
  void testSetX() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testXCoordinate : testXCoordinates) {
      testVector.setX(testXCoordinate);
      assertEquals(testVector.getX(), testXCoordinate);
    }
  }

  @Test
  void testSetY() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testYCoordinate : testYCoordinates) {
      testVector.setY(testYCoordinate);
      assertEquals(testVector.getY(), testYCoordinate);
    }
  }

  @Test
  void testSet() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        testVector.set(new Vector2f(testXCoordinate, testYCoordinate));
        assertEquals(testVector.getX(), testXCoordinate);
        assertEquals(testVector.getY(), testYCoordinate);
      }
    }
  }
}
