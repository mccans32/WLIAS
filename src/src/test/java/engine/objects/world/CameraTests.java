package engine.objects.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  public void testDefaultPosition() {
    camera.getPosition().setZ(camera.getPosition().getZ() / -(Camera.getZoomModifier()));
    assertEquals(camera.getDefaultPosition(), camera.getPosition());
    camera.setRotation(new Vector3f(
        CAMERA_ROTATION.getX() + 1f,
        CAMERA_ROTATION.getY() + 1f,
        CAMERA_ROTATION.getZ() - 1f));
    assertEquals(camera.getDefaultRotation(), CAMERA_ROTATION);
  }

  @Test
  public void testFreeze() {
    camera.freeze();
    assertTrue(camera.isFrozen());
    camera.unfreeze();
    assertFalse(camera.isFrozen());
  }

  @Test
  public void testReset() {
    camera.setPosition(new Vector3f(
        camera.getPosition().getX() + 1f,
        camera.getPosition().getY() + 1f,
        camera.getPosition().getZ() - 1f));
    assertNotEquals(camera.getPosition(), camera.getDefaultPosition());
    camera.setRotation(new Vector3f(
        camera.getRotation().getX() + 1f,
        camera.getRotation().getY() + 1f,
        camera.getRotation().getZ() - 1f));
    assertNotEquals(camera.getRotation(), camera.getDefaultRotation());
    camera.reset();
    assertEquals(camera.getPosition(), camera.getDefaultPosition());
    assertEquals(camera.getPosition(), camera.getDefaultPosition());
  }
}
