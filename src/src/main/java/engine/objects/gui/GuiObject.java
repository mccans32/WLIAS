package engine.objects.gui;

import engine.graphics.Mesh;
import math.Vector2f;
import math.Vector3f;

public class GuiObject {
  private Vector2f defaultPosition;
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
    this.defaultPosition = position;
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

  public void reposition(float xEdge, float xOffset, float xSpan, float yEdge, float yOffset, float ySpan) {
    position.setX(xEdge * xSpan + xOffset);
    position.setY(yEdge * ySpan + yOffset);
  }
}
