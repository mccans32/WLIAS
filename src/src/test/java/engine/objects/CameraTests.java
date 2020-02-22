package engine.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import math.Vector3f;
import org.junit.jupiter.api.Test;

public class CameraTests {
  private static Vector3f CAMERA_POSITION = new Vector3f(0, 0, 1f);
  private static Vector3f CAMERA_ROTATION = new Vector3f(0, 0, 0);

  private Camera camera = new Camera(CAMERA_POSITION, CAMERA_ROTATION);

  @Test
  public void testGetPosition() {
    assertEquals(camera.getPosition().getX(), CAMERA_POSITION.getX());
    assertEquals(camera.getPosition().getZ(), CAMERA_POSITION.getZ());
    assertEquals(camera.getPosition().getY(), CAMERA_POSITION.getY());
  }

  @Test
  public void testGetRotation() {
    assertEquals(camera.getRotation().getX(), CAMERA_ROTATION.getX());
    assertEquals(camera.getRotation().getZ(), CAMERA_ROTATION.getZ());
    assertEquals(camera.getRotation().getY(), CAMERA_ROTATION.getY());
  }
}
