package engine.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import math.Vector3f;
import org.junit.jupiter.api.Test;

public class Vertex3DTests {
  private static float[] testXCoordinates = {-6.0f, 1.2f, 0.4f, 0.7f, 1.0f, 2.0f};
  private static float[] testYCoordinates = {-1.9f, 3.4f, 7.8f, 1.1f, 3.1f, 3.1f};
  private static float[] testZCoordinates = {-1.9f, 3.4f, 7.8f, 1.1f, 3.1f, 3.1f};

  @Test
  public void testPositionType() {
    Vector3f testVector = new Vector3f(0f, 0f, 0f);
    Vertex3D testVertex = new Vertex3D(testVector);
    assertNotNull(testVertex.getPosition());
  }

  @Test
  public void testGetX() {
    for (float testXCoordinate : testXCoordinates) {
      Vector3f testVector = new Vector3f(testXCoordinate, 0f, 0f);
      Vertex3D testVertex = new Vertex3D(testVector);
      assertEquals(testVertex.getPosition().getX(), testXCoordinate);
    }
  }

  @Test
  public void testGetY() {
    for (float testYCoordinate : testYCoordinates) {
      Vector3f testVector = new Vector3f(0f, testYCoordinate, 0);
      Vertex3D testVertex = new Vertex3D(testVector);
      assertEquals(testVertex.getPosition().getY(), testYCoordinate);
    }
  }

  @Test
  public void testGetZ() {
    for (float testZCoordinate : testZCoordinates) {
      Vector3f testVector = new Vector3f(0f, 0, testZCoordinate);
      Vertex3D testVertex = new Vertex3D(testVector);
      assertEquals(testVertex.getPosition().getZ(), testZCoordinate);
    }
  }
}