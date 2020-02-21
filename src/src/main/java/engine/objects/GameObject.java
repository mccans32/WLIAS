package engine.objects;

import engine.graphics.Mesh;
import math.Vector2f;

public class GameObject {
  private Vector2f position;
  private Vector2f rotation;
  private Vector2f scale;
  private Mesh mesh;


  /**
   * Instantiates a new Game object.
   *
   * @param position the position
   * @param rotation the rotation
   * @param scale    the scale
   * @param mesh     the mesh
   */
  public GameObject(Vector2f position, Vector2f rotation, Vector2f scale, Mesh mesh) {
    this.position = position;
    this.rotation = rotation;
    this.scale = scale;
    this.mesh = mesh;
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector2f getRotation() {
    return rotation;
  }

  public Vector2f getScale() {
    return scale;
  }

  public Mesh getMesh() {
    return mesh;
  }

  // TEMP
  public void update() {
    position.add(0.1f, 0);
  }


}
