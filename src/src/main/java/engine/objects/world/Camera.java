package engine.objects.world;

import engine.io.Input;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {
  private static final float MIN_CAMERA_Z = 1f;
  private static final float MAX_CAMERA_Z = 30f;
  private static final float ZOOM_MODIFIER = 0.05f;
  private static final float MOVE_SPEED = 0.1f;
  private static final float MIN_CAMERA_BORDER = 5f;
  private float maxCameraX;
  private float minCameraX;
  private float maxCameraY;
  private float minCameraY;
  private Vector3f defaultPosition;
  private Vector3f defaultRotation;
  private float defaultDistance;
  private Vector3f position;
  private Vector3f rotation;
  private boolean isFrozen = false;
  /**
   * Instantiates a new Camera.
   *
   * @param position the position
   * @param rotation the rotation
   */
  public Camera(Vector3f position, Vector3f rotation) {
    this.position = position;
    // Compensate for Zoom Factor
    this.position.setZ(this.position.getZ() / -(ZOOM_MODIFIER));
    this.defaultDistance = this.position.getZ();
    this.rotation = rotation;
    // Set Defaults, used when resetting
    this.defaultPosition = position;
    this.defaultRotation = rotation;
    this.maxCameraX = MIN_CAMERA_BORDER;
    this.minCameraX = -MIN_CAMERA_BORDER;
    this.maxCameraY = MIN_CAMERA_BORDER;
    this.minCameraY = -MIN_CAMERA_BORDER;
  }

  public static float getMinCameraBorder() {
    return MIN_CAMERA_BORDER;
  }

  public static float getZoomModifier() {
    return ZOOM_MODIFIER;
  }

  public float getMaxCameraX() {
    return maxCameraX;
  }

  public float getMinCameraX() {
    return minCameraX;
  }

  public float getMaxCameraY() {
    return maxCameraY;
  }

  public float getMinCameraY() {
    return minCameraY;
  }

  public Vector3f getDefaultPosition() {
    return defaultPosition;
  }

  public Vector3f getDefaultRotation() {
    return defaultRotation;
  }

  public boolean isFrozen() {
    return isFrozen;
  }

  public void freeze() {
    isFrozen = true;
  }

  public void unfreeze() {
    isFrozen = false;
  }

  /**
   * Update.
   */
  public void update() {
    if (!isFrozen) {
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

      // Calculate Distance for Zoom
      float cameraDistance = (float) -(Input.getScrollY() + defaultDistance * ZOOM_MODIFIER);

      if (cameraDistance < MIN_CAMERA_Z) {
        position.setZ(MIN_CAMERA_Z);
      } else {
        position.setZ(Math.min(cameraDistance, MAX_CAMERA_Z));
      }

      // Calculate if camera is within X Border
      if (this.position.getX() > maxCameraX) {
        position.setX(maxCameraX);
      } else {
        position.setX(Math.max(position.getX(), minCameraX));
      }

      // Calculate if camera is within Y Border
      if (this.position.getY() > maxCameraY) {
        position.setY(maxCameraY);
      } else {
        position.setY(Math.max(position.getY(), minCameraY));
      }
    }
  }

  /**
   * Sets camera border.
   *
   * @param botLeft  the bot left
   * @param topRight the top right
   */
  public void setCameraBorder(Vector2f botLeft, Vector2f topRight) {
    this.maxCameraX = topRight.getX();
    this.minCameraX = botLeft.getX();
    this.maxCameraY = topRight.getY();
    this.minCameraY = botLeft.getY();

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

  public void reset() {
    this.position = defaultPosition;
    this.rotation = defaultRotation;
  }

}
