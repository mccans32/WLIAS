package engine.objects.gui;

import engine.graphics.Mesh;
import math.Vector2f;

public class GuiObject {

  private Vector2f position;
  private Vector2f scale;
  private Mesh mesh;

  /**
   * Instantiates a new Gui object.
   *
   * @param position the position
   * @param scale    the scale
   * @param mesh     the mesh.
   */
  public GuiObject(Vector2f position, Vector2f scale, Mesh mesh) {
    this.position = position;
    this.scale = scale;
    this.mesh = mesh;
  }

  public Vector2f getPosition() {
    return position;
  }

  public Vector2f getScale() {
    return scale;
  }

  public Mesh getMesh() {
    return mesh;
  }

  public void create() {
    mesh.create();
  }
}
