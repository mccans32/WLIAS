package engine.objects;

import engine.graphics.Mesh;
import math.Vector2f;
import math.Vector3f;

public class GameObject {
  private Vector2f position = new Vector2f(0f, 0f);
  private Vector3f rotation = new Vector3f(0f, 0f, 0f);
  private Vector2f scale = new Vector2f(1f, 1f);
  private Mesh mesh;


  public GameObject(Mesh mesh) {
    this.mesh = mesh;
  }

  public GameObject(Vector2f position, Mesh mesh) {
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
  public GameObject(Vector2f position, Vector3f rotation, Vector2f scale, Mesh mesh) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.mesh = mesh;
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector3f getRotation() {
    return rotation;
  }

  public Vector2f getScale() {
    return scale;
  }

  public Mesh getMesh() {
    return mesh;
  }

}
