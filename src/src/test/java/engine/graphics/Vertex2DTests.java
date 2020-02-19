package test.engine.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import engine.graphics.Vertex2D;
import math.Vector2f;
import org.junit.jupiter.api.Test;

public class Vertex2DTests {
  private static float[] testXCoordinates = {-6.0f, 1.2f, 0.4f, 0.7f, 1.0f, 2.0f};
  private static float[] testYCoordinates = {-1.9f, 3.4f, 7.8f, 1.1f, 3.1f, 3.1f};

  @Test
  public void testPositionType() {
    Vector2f testVector = new Vector2f(0f, 0f);
    Vertex2D testVertex = new Vertex2D(testVector);
    assertNotNull(testVertex.getPosition());
  }

  @Test
  public void testGetX() {
    for (float testXCoordinate : testXCoordinates) {
      Vector2f testVector = new Vector2f(testXCoordinate, 0f);
      Vertex2D testVertex = new Vertex2D(testVector);
      assertEquals(testVertex.getPosition().getX(), testXCoordinate);
    }
  }

  @Test
  public void testGetY() {
    for (float testYCoordinate : testYCoordinates) {
      Vector2f testVector = new Vector2f(0f, testYCoordinate);
      Vertex2D testVertex = new Vertex2D(testVector);
      assertEquals(testVertex.getPosition().getY(), testYCoordinate);
    }
  }
}