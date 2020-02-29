package engine.objects.world;

import engine.graphics.Mesh;
import math.Vector3f;

public abstract class GameObject {
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

  public Vector3f getRotation() {
    return rotation;
  }

  public Vector3f getScale() {
    return scale;
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

}
