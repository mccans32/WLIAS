package engine.objects.world;

import engine.io.Input;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
  private static final float MIN_CAMERA_Z = 1f;
  private static final float MAX_CAMERA_Z = 50f;
  private static final float ZOOM_MODIFIER = 0.05f;
  private static final float MOVE_SPEED = 0.05f;
  private Vector3f position;
  private Vector3f rotation;

  public Camera(Vector3f position, Vector3f rotation) {
    this.position = position;
    this.rotation = rotation;
  }

  /**
   * Update.
   */
  public void update() {
    // Move Camera with keyboard
    if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
      position = Vector3f.add(position, new Vector3f(-MOVE_SPEED, 0, 0));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_W)) {
      position = Vector3f.add(position, new Vector3f(0, MOVE_SPEED, 0));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_S)) {
      position = Vector3f.add(position, new Vector3f(0, -MOVE_SPEED, 0));
    }
    if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
      position = Vector3f.add(position, new Vector3f(MOVE_SPEED, 0, 0));
    }

    position = Vector3f.add(position, new Vector3f(0, 0, 1));
    float cameraDistance = (float) -(Input.getScrollY() + MIN_CAMERA_Z * ZOOM_MODIFIER);

    if (cameraDistance < MIN_CAMERA_Z) {
      position.setZ(MIN_CAMERA_Z);
    } else {
      position.setZ(Math.min(cameraDistance, MAX_CAMERA_Z));
    }
  }

  public Vector3f getPosition() {
    return position;
  }

  public void setPosition(Vector3f position) {
    this.position = position;
  }

  public Vector3f getRotation() {
    return rotation;
  }

  public void setRotation(Vector3f rotation) {
    this.rotation = rotation;
  }
}
