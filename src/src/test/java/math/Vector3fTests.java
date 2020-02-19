package test.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import math.Vector3f;
import org.junit.jupiter.api.Test;


public class Vector3fTests {
  private static float[] testXCoordinates = {-6.0f, 1.2f, 0.4f, 0.7f, 1.0f, 2.0f};
  private static float[] testYCoordinates = {-1.9f, 3.4f, 7.8f, 1.1f, 3.1f, 3.1f};
  private static float[] testZCoordinates = {-7.8f, 0.0f, -6.7f, 0.1f, 4.1f, 9.2f};

  @Test
  void testInit() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f testVector = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          assertEquals(testVector.getX(), testXCoordinate);
          assertEquals(testVector.getY(), testYCoordinate);
          assertEquals(testVector.getZ(), testZCoordinate);
        }
      }
    }
  }

  @Test
  void testSetX() {
    Vector3f testVector = new Vector3f(0.0f, 0.0f, 0.0f);
    for (float testXCoordinate : testXCoordinates) {
      testVector.setX(testXCoordinate);
      assertEquals(testVector.getX(), testXCoordinate);
    }
  }

  @Test
  void testSetY() {
    Vector3f testVector = new Vector3f(0.0f, 0.0f, 0f);
    for (float testYCoordinate : testYCoordinates) {
      testVector.setY(testYCoordinate);
      assertEquals(testVector.getY(), testYCoordinate);
    }
  }

  @Test
  void testSetZ() {
    Vector3f testVector = new Vector3f(0.0f, 0.0f, 0f);
    for (float testZCoordinate : testZCoordinates) {
      testVector.setY(testZCoordinate);
      assertEquals(testVector.getY(), testZCoordinate);
    }
  }

  @Test
  void testSet() {
    Vector3f testVector = new Vector3f(0.0f, 0.0f, 0f);
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          testVector.set(testXCoordinate, testYCoordinate, testZCoordinate);
          assertEquals(testVector.getX(), testXCoordinate);
          assertEquals(testVector.getY(), testYCoordinate);
        }
      }
    }
  }
}
