package engine.tools;

import engine.Window;
import engine.io.Input;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.objects.world.TileWorldObject;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

// For more information on ray casting visit http://antongerdelan.net/opengl/raycasting.html
public class MousePicker {
  private float groundZ;
  private Vector3f currentRay;
  private Matrix4f projectionMatrix;
  private Matrix4f viewMatrix;
  private Camera camera;
  private GameObject currentSelected;

  /**
   * Instantiates a new Mouse picker.
   *
   * @param camera           the camera
   * @param projectionMatrix the projection matrix
   */
  public MousePicker(Camera camera, Matrix4f projectionMatrix, float groundZ) {
    this.camera = camera;
    this.projectionMatrix = projectionMatrix;
    this.viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
    this.groundZ = groundZ;
  }

  /**
   * Gets normalised device coordinates.
   *
   * @param window the window
   * @return the normalised device coordinates
   */
  public static Vector2f getNormalisedDeviceCoordinates(Window window) {
    float normalisedX = (2f * (float) Input.getMouseX()) / window.getWidth() - 1f;
    float normalisedY = -(2f * (float) Input.getMouseY()) / window.getHeight() + 1f;
    return new Vector2f(normalisedX, normalisedY);
  }

  public GameObject getCurrentSelected() {
    return currentSelected;
  }

  /**
   * Update the matrices to ensure the mousePicker is accurate on resize and camera movement.
   * Update the picker ray
   * Calculate the currently selected object if any.
   *
   * @param window the window
   */
  public void update(Window window, TileWorldObject[][] map) {
    projectionMatrix = window.getProjectionMatrix();
    viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
    currentRay = calculateMouseRay(window);
    select(map);
  }

  public Vector3f getCurrentRay() {
    return currentRay;
  }

  private Vector3f calculateMouseRay(Window window) {
    Vector2f normalisedCoordinates = getNormalisedDeviceCoordinates(window);
    Vector4f clipCoordinates = new Vector4f(
        normalisedCoordinates.getX(),
        normalisedCoordinates.getY(),
        -1f,
        1f);
    Vector4f eyeCoordinates = toEyeCoordinates(clipCoordinates);
    return toWorldCoordinates(eyeCoordinates);
  }

  private Vector3f toWorldCoordinates(Vector4f eyeCoordinates) {
    Vector4f rayWorld = Matrix4f.transform(viewMatrix, eyeCoordinates);
    Vector3f mouseRay = new Vector3f(rayWorld.getX(), rayWorld.getY(), rayWorld.getZ());
    mouseRay = Vector3f.normalise(mouseRay);
    return mouseRay;
  }

  private Vector4f toEyeCoordinates(Vector4f clipCoordinates) {
    Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix);
    Vector4f eyeCoordinates = Matrix4f.transform(invertedProjection, clipCoordinates);
    return new Vector4f(eyeCoordinates.getX(), eyeCoordinates.getY(), -1f, 0f);
  }

  private void select(TileWorldObject[][] map) {
    Vector3f groundPoint = getGroundPoint(currentRay);
    currentSelected = calculateSelected(groundPoint, map);
  }

  private TileWorldObject calculateSelected(Vector3f groundPoint, TileWorldObject[][] map) {
    TileWorldObject selected = null;
    for (TileWorldObject[] row : map) {
      for (TileWorldObject tile : row) {
        Vector3f tilePos = tile.getPosition();
        float tileHeight = tile.getMesh().getModel().getHeight();
        float tileWidth = tile.getMesh().getModel().getWidth();
        if (groundPoint.getX() <= (tilePos.getX() + tileHeight / 2)
            && (groundPoint.getX() >= tilePos.getX() - tileHeight / 2)) {
          if (groundPoint.getY() <= (tilePos.getY() + tileWidth / 2)
              && (groundPoint.getY() >= tilePos.getY() - tileWidth / 2)) {
            selected = tile;
          }
        }
      }
    }
    return selected;
  }

  private Vector3f getGroundPoint(Vector3f ray) {
    // Get camera position and Make it the start point
    Vector3f camPos = camera.getPosition();
    Vector3f start = new Vector3f(camPos.getX(), camPos.getY(), camPos.getZ());
    // Find the distance required for our ray's z value to reach ground level
    float distance = (-start.getZ() + groundZ) / ray.getZ();
    Vector3f scaledRay = new Vector3f(
        ray.getX() * distance,
        ray.getY() * distance,
        ray.getZ() * distance);
    return Vector3f.add(start, scaledRay);
  }
}
