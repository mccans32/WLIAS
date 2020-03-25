package math;

import static java.lang.System.identityHashCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
          testVector.set(new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate));
          assertEquals(testVector.getX(), testXCoordinate);
          assertEquals(testVector.getY(), testYCoordinate);
          assertEquals(testVector.getZ(), testZCoordinate);
          testVector.set(testYCoordinate);
          assertEquals(testVector, new Vector3f(testYCoordinate, testYCoordinate, testYCoordinate));
        }
      }
    }
  }

  @Test
  public void testAdd() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f vector1 = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          Vector3f vector2 = new Vector3f(testYCoordinate, testXCoordinate, testZCoordinate);
          Vector3f vector3 = Vector3f.add(vector1, vector2);
          assertEquals(vector3, new Vector3f(
              testXCoordinate + testYCoordinate,
              testYCoordinate + testXCoordinate,
              testZCoordinate + testZCoordinate));
          vector1.add(vector2);
          assertEquals(vector1, vector3);
          vector1.add(testXCoordinate);
          assertEquals(vector1.getX(), vector3.getX() + testXCoordinate);
          assertEquals(vector1.getY(), vector3.getY() + testXCoordinate);
          assertEquals(vector1.getZ(), vector3.getZ() + testXCoordinate);
          vector3 = Vector3f.add(vector3, testXCoordinate);
          assertEquals(vector1, vector3);
        }
      }
    }
  }

  @Test
  public void testSubtract() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f vector1 = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          Vector3f vector2 = new Vector3f(testYCoordinate, testXCoordinate, testZCoordinate);
          Vector3f vector3 = Vector3f.subtract(vector1, vector2);
          assertEquals(vector3, new Vector3f(
              testXCoordinate - testYCoordinate,
              testYCoordinate - testXCoordinate,
              testZCoordinate - testZCoordinate));
          vector1.subtract(vector2);
          assertEquals(vector1, vector3);
          vector1.subtract(testXCoordinate);
          assertEquals(vector1.getX(), vector3.getX() - testXCoordinate);
          assertEquals(vector1.getY(), vector3.getY() - testXCoordinate);
          assertEquals(vector1.getZ(), vector3.getZ() - testXCoordinate);
          vector3 = Vector3f.subtract(vector3, testXCoordinate);
          assertEquals(vector1, vector3);
        }
      }
    }
  }

  @Test
  public void testMultiply() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f vector1 = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          Vector3f vector2 = new Vector3f(testYCoordinate, testXCoordinate, testZCoordinate);
          Vector3f vector3 = Vector3f.multiply(vector1, vector2);
          assertEquals(vector3, new Vector3f(
              testXCoordinate * testYCoordinate,
              testYCoordinate * testXCoordinate,
              testZCoordinate * testZCoordinate));
          vector1.multiply(vector2);
          assertEquals(vector1, vector3);
          vector1.multiply(testXCoordinate);
          assertEquals(vector1.getX(), vector3.getX() * testXCoordinate);
          assertEquals(vector1.getY(), vector3.getY() * testXCoordinate);
          assertEquals(vector1.getZ(), vector3.getZ() * testXCoordinate);
          vector3 = Vector3f.multiply(vector3, testXCoordinate);
          assertEquals(vector1, vector3);
        }
      }
    }
  }

  @Test
  public void testDivide() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f vector1 = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          Vector3f vector2 = new Vector3f(testYCoordinate, testXCoordinate, testZCoordinate);
          Vector3f vector3 = Vector3f.divide(vector1, vector2);
          assertEquals(vector3, new Vector3f(
              testXCoordinate / testYCoordinate,
              testYCoordinate / testXCoordinate,
              testZCoordinate / testZCoordinate));
          vector1.divide(vector2);
          assertEquals(vector1, vector3);
          vector1.divide(testXCoordinate);
          assertEquals(vector1.getX(), vector3.getX() / testXCoordinate);
          assertEquals(vector1.getY(), vector3.getY() / testXCoordinate);
          assertEquals(vector1.getZ(), vector3.getZ() / testXCoordinate);
          vector3 = Vector3f.divide(vector3, testXCoordinate);
          assertEquals(vector1, vector3);
        }
      }
    }
  }

  @Test
  public void testCopy() {
    for (float testXCoordinate : testXCoordinates) {
      for (float testYCoordinate : testYCoordinates) {
        for (float testZCoordinate : testZCoordinates) {
          Vector3f vector1 = new Vector3f(testXCoordinate, testYCoordinate, testZCoordinate);
          Vector3f vector2 = vector1.copy();
          assertEquals(vector1.getX(), vector2.getX());
          assertEquals(vector1.getY(), vector2.getY());
          assertEquals(vector1.getZ(), vector2.getZ());
          vector1.setX(vector1.getX() + 1);
          assertNotEquals(vector1, vector2);
          assertNotEquals(identityHashCode(vector1), identityHashCode(vector2));
        }
      }
    }
  }
}
