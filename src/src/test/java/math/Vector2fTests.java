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
  public void testSetX() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testXCoordinate : testXCoordinates) {
      testVector.setX(testXCoordinate);
      assertEquals(testVector.getX(), testXCoordinate);
    }
  }

  @Test
  public void testSetY() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testYCoordinate : testYCoordinates) {
      testVector.setY(testYCoordinate);
      assertEquals(testVector.getY(), testYCoordinate);
    }
  }

  @Test
  public void testSet() {
    Vector2f testVector = new Vector2f(0.0f, 0.0f);
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        testVector.set(new Vector2f(testXCoordinate, testYCoordinate));
        assertEquals(testVector.getX(), testXCoordinate);
        assertEquals(testVector.getY(), testYCoordinate);
        testVector.set(testXCoordinate);
        assertEquals(testVector.getX(), testXCoordinate);
        assertEquals(testVector.getY(), testXCoordinate);
        testVector.set(testYCoordinate);
        assertEquals(testVector.getX(), testYCoordinate);
        assertEquals(testVector.getY(), testYCoordinate);
      }
    }
  }

  @Test
  public void testAdd() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        Vector2f vector1 = new Vector2f(testXCoordinate, testYCoordinate);
        Vector2f vector2 = new Vector2f(testYCoordinate, testXCoordinate);
        Vector2f vector3 = Vector2f.add(vector1, vector2);
        assertEquals(vector3, new Vector2f(
            testXCoordinate + testYCoordinate,
            testYCoordinate + testXCoordinate));
        vector1.add(vector2);
        assertEquals(vector1, vector3);
        vector1.add(testXCoordinate);
        assertEquals(vector1.getX(), vector3.getX() + testXCoordinate);
        assertEquals(vector1.getY(), vector3.getY() + testXCoordinate);
        vector3 =  Vector2f.add(vector3, testXCoordinate);
        assertEquals(vector1, vector3);
      }
    }
  }

  @Test
  public void testSubtract() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        Vector2f vector1 = new Vector2f(testXCoordinate, testYCoordinate);
        Vector2f vector2 = new Vector2f(testYCoordinate, testXCoordinate);
        Vector2f vector3 = Vector2f.subtract(vector1, vector2);
        assertEquals(vector3, new Vector2f(
            testXCoordinate - testYCoordinate,
            testYCoordinate - testXCoordinate));
        vector1.subtract(vector2);
        assertEquals(vector1, vector3);
        vector1.subtract(testXCoordinate);
        assertEquals(vector1.getX(), vector3.getX() - testXCoordinate);
        assertEquals(vector1.getY(), vector3.getY() - testXCoordinate);
        vector3 =  Vector2f.subtract(vector3, testXCoordinate);
        assertEquals(vector1, vector3);
      }
    }
  }

  @Test
  public void testMultiply() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        Vector2f vector1 = new Vector2f(testXCoordinate, testYCoordinate);
        Vector2f vector2 = new Vector2f(testYCoordinate, testXCoordinate);
        Vector2f vector3 = Vector2f.multiply(vector1, vector2);
        assertEquals(vector3, new Vector2f(
            testXCoordinate * testYCoordinate,
            testYCoordinate * testXCoordinate));
        vector1.multiply(vector2);
        assertEquals(vector1, vector3);
        vector1.multiply(testXCoordinate);
        assertEquals(vector1.getX(), vector3.getX() * testXCoordinate);
        assertEquals(vector1.getY(), vector3.getY() * testXCoordinate);
        vector3 =  Vector2f.multiply(vector3, testXCoordinate);
        assertEquals(vector1, vector3);
      }
    }
  }

  @Test
  public void testDivide() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        Vector2f vector1 = new Vector2f(testXCoordinate, testYCoordinate);
        Vector2f vector2 = new Vector2f(testYCoordinate, testXCoordinate);
        Vector2f vector3 = Vector2f.divide(vector1, vector2);
        assertEquals(vector3, new Vector2f(
            testXCoordinate / testYCoordinate,
            testYCoordinate / testXCoordinate));
        vector1.divide(vector2);
        assertEquals(vector1, vector3);
        vector1.divide(testXCoordinate);
        assertEquals(vector1.getX(), vector3.getX() / testXCoordinate);
        assertEquals(vector1.getY(), vector3.getY() / testXCoordinate);
        vector3 =  Vector2f.divide(vector3, testXCoordinate);
        assertEquals(vector1, vector3);
      }
    }
  }
}
