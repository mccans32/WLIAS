package engine.objects.world;

import static java.lang.Math.max;
import static org.joml.FrustumIntersection.OUTSIDE;

import engine.graphics.mesh.Mesh;
import engine.graphics.renderer.WorldRenderer;
import math.Vector3f;
import org.joml.FrustumIntersection;

public class GameObject {
  private Vector3f position = new Vector3f(0f, 0f, 0f);
  private Vector3f rotation = new Vector3f(0f, 0f, 0f);
  private Vector3f scale = new Vector3f(1f, 1f, 1f);
  private Mesh mesh;

  public GameObject(Mesh mesh) {
    this.mesh = mesh;
  }

  public GameObject(Vector3f position, Mesh mesh) {
    this.position = position;
    this.mesh = mesh;
  }

  /**
   * Instantiates a new Game object.
   *
   * @param position the position
   * @param rotation the rotation, angle to rotate each axis in radians
   * @param scale    the scale
   * @param mesh     the mesh
   */
  public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.mesh = mesh;
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

  public Vector3f getScale() {
    return scale;
  }

  public void setScale(Vector3f scale) {
    this.scale = scale;
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void create() {
    mesh.create();
  }

  public void destroy() {
    mesh.destroy();
  }

  public void render(WorldRenderer renderer, Camera camera) {
    renderer.renderObject(this, camera);
  }

  private float calculateBoundingSphereRadius() {
    float scaleX = scale.getX();
    float squareSide =
        max(mesh.getModel().getHeight() * scaleX, mesh.getModel().getWidth() * scaleX);
    // return radius
    return squareSide * squareSide;
  }

  /**
   * Is contained in the frustum's view boolean.
   *
   * @param frustum the frustum
   * @return the boolean
   */
  public boolean isContained(FrustumIntersection frustum) {
    int isContained = frustum.intersectSphere(
        position.getX(),
        position.getY(),
        position.getZ(),
        calculateBoundingSphereRadius());
    return isContained != OUTSIDE;
  }
}
