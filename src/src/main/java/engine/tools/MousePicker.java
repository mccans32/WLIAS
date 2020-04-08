package engine.tools;

import engine.Window;
import engine.io.Input;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import java.util.ArrayList;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

// For more information on ray casting visit http://antongerdelan.net/opengl/raycasting.html
public class MousePicker {
  private static float groundZ;
  private static Vector3f currentRay;
  private static Matrix4f projectionMatrix;
  private static Matrix4f viewMatrix;
  private static Camera camera;
  private static TileWorldObject currentSelected;

  public static float getGroundZ() {
    return groundZ;
  }

  public static void setGroundZ(float groundZ) {
    MousePicker.groundZ = groundZ;
  }

  public static Matrix4f getProjectionMatrix() {
    return projectionMatrix;
  }

  public static void setProjectionMatrix(Matrix4f projectionMatrix) {
    MousePicker.projectionMatrix = projectionMatrix;
  }

  public static Matrix4f getViewMatrix() {
    return viewMatrix;
  }

  public static void setViewMatrix(Matrix4f viewMatrix) {
    MousePicker.viewMatrix = viewMatrix;
  }

  public static Camera getCamera() {
    return camera;
  }

  public static void setCamera(Camera camera) {
    MousePicker.camera = camera;
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

  public static TileWorldObject getCurrentSelected() {
    return currentSelected;
  }

  /**
   * Update the matrices to ensure the mousePicker is accurate on resize and camera movement.
   * Update the picker ray
   * Calculate the currently selected object if any.
   *
   * @param window the window
   */
  public static void update(Window window, TileWorldObject[][] map) {
    projectionMatrix = window.getProjectionMatrix();
    viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
    currentRay = calculateMouseRay(window);
    select(map);
  }

  public static void update(Window window, ArrayList<TileWorldObject> map) {
    projectionMatrix = window.getProjectionMatrix();
    viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
    currentRay = calculateMouseRay(window);
    select(map);
  }

  public static Vector3f getCurrentRay() {
    return currentRay;
  }

  private static Vector3f calculateMouseRay(Window window) {
    Vector2f normalisedCoordinates = getNormalisedDeviceCoordinates(window);
    Vector4f clipCoordinates = new Vector4f(
        normalisedCoordinates.getX(),
        normalisedCoordinates.getY(),
        -1f,
        1f);
    Vector4f eyeCoordinates = toEyeCoordinates(clipCoordinates);
    return toWorldCoordinates(eyeCoordinates);
  }

  private static Vector3f toWorldCoordinates(Vector4f eyeCoordinates) {
    Vector4f rayWorld = Matrix4f.transform(viewMatrix, eyeCoordinates);
    Vector3f mouseRay = new Vector3f(rayWorld.getX(), rayWorld.getY(), rayWorld.getZ());
    mouseRay = Vector3f.normalise(mouseRay);
    return mouseRay;
  }

  private static Vector4f toEyeCoordinates(Vector4f clipCoordinates) {
    Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix);
    Vector4f eyeCoordinates = Matrix4f.transform(invertedProjection, clipCoordinates);
    return new Vector4f(eyeCoordinates.getX(), eyeCoordinates.getY(), -1f, 0f);
  }

  private static void select(TileWorldObject[][] map) {
    Vector3f groundPoint = getGroundPoint(currentRay);
    currentSelected = calculateSelected(groundPoint, map);
  }

  private static void select(ArrayList<TileWorldObject> map) {
    Vector3f groundPoint = getGroundPoint(currentRay);
    currentSelected = calculateSelected(groundPoint, map);
  }

  private static TileWorldObject calculateSelected(Vector3f groundPoint, TileWorldObject[][] map) {
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

  private static TileWorldObject calculateSelected(Vector3f groundPoint, ArrayList<TileWorldObject> map) {
    TileWorldObject selected = null;
    for (TileWorldObject worldTile : map) {
      Vector3f worldTilePos = worldTile.getPosition();
      float worldTileHeight = worldTile.getMesh().getModel().getHeight();
      float worldTileWidth = worldTile.getMesh().getModel().getWidth();
      if (groundPoint.getX() <= (worldTilePos.getX() + worldTileHeight / 2)
          && (groundPoint.getX() >= worldTilePos.getX() - worldTileHeight / 2)) {
        if (groundPoint.getY() <= (worldTilePos.getY() + worldTileWidth / 2)
            && (groundPoint.getY() >= worldTilePos.getY() - worldTileWidth / 2)) {
          selected = worldTile;
        }
      }
    }
    return selected;
  }

  private static Vector3f getGroundPoint(Vector3f ray) {
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
