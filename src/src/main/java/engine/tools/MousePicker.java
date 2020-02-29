package engine.tools;

import engine.Window;
import engine.io.Input;
import engine.objects.world.Camera;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;

// For more information on ray casting visit http://antongerdelan.net/opengl/raycasting.html
public class MousePicker {
  private Vector3f currentRay;
  private Matrix4f projectionMatrix;
  private Matrix4f viewMatrix;
  private Camera camera;

  public MousePicker(Camera camera, Matrix4f projectionMatrix) {
    this.camera = camera;
    this.projectionMatrix = projectionMatrix;
    this.viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
  }

  public static Vector2f getNormalisedDeviceCoordinates(Window window) {
    float normalisedX = (2f * (float) Input.getMouseX()) / window.getWidth() - 1f;
    float normalisedY = -(2f * (float) Input.getMouseY()) / window.getHeight() + 1f;
    return new Vector2f(normalisedX, normalisedY);
  }

  public void update(Window window) {
    viewMatrix = Matrix4f.view(camera.getPosition(), camera.getRotation());
    currentRay = calculateMouseRay(window);
  }

  public Vector3f getCurrentRay() {
    return currentRay;
  }

  private Vector3f calculateMouseRay(Window window) {
    Vector2f normalisedCoordinates = getNormalisedDeviceCoordinates(window);
    Vector4f clipCoordinates = new Vector4f(normalisedCoordinates.getX(), normalisedCoordinates.getY(), -1f, 1f);
    Vector4f eyeCoordinates = toEyeCoordinates(clipCoordinates);
    return toWorldCoordinates(eyeCoordinates);
  }

  private Vector3f toWorldCoordinates(Vector4f eyeCoordinates) {
    Matrix4f invertedView = Matrix4f.invert(viewMatrix);
    Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoordinates);
    Vector3f mouseRay = new Vector3f(rayWorld.getX(), rayWorld.getY(), rayWorld.getZ());
    mouseRay = Vector3f.normalise(mouseRay);
    return mouseRay;
  }

  private Vector4f toEyeCoordinates(Vector4f clipCoordinates) {
    Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix);
    Vector4f eyeCoordinates = Matrix4f.transform(invertedProjection, clipCoordinates);
    return new Vector4f(eyeCoordinates.getX(), eyeCoordinates.getY(), -1f, 0f);
  }
}
