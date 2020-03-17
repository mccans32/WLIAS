package engine.objects.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import engine.graphics.Material;
import engine.graphics.mesh.Mesh;
import engine.graphics.Vertex3D;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;
import math.Vector3f;
import org.junit.jupiter.api.Test;

public class GameObjectTests {
  private static Vector3f objectPosition = new Vector3f(0, 0, 0);
  private static Vector3f objectRotation = new Vector3f(0, 0, 0);
  private static Vector3f objectScale = new Vector3f(1f, 1f, 1f);

  private Mesh testMesh = new Mesh(
      new Vertex3D[] {
          new Vertex3D(new Vector3f(-0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
          new Vertex3D(new Vector3f(-0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
          new Vertex3D(new Vector3f(0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
          new Vertex3D(new Vector3f(0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))
      },
      new int[] {0, 3, 1, 2},
      new Material("/images/mid-tier-tile.png"));

  private GameObject testObject = new GameObject(
      objectPosition,
      objectRotation,
      objectScale,
      testMesh);

  @Test
  public void testGetters() {
    assertEquals(testObject.getPosition().getX(), objectPosition.getX());
    assertEquals(testObject.getPosition().getY(), objectPosition.getY());
    assertEquals(testObject.getPosition().getZ(), objectPosition.getZ());
  }
}
