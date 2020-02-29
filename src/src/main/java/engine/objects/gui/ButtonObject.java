package engine.objects.gui;

import engine.Window;
import engine.graphics.Mesh;
import math.Vector2f;
import math.Vector3f;

public class ButtonObject extends GuiObject {
  /**
   * Instantiates a new Gui object.
   *
   * @param position the position
   * @param scale    the scale
   * @param mesh     the mesh.
   * @param xEdge
   * @param xOffset
   * @param yEdge
   * @param yOffset
   */
  private static final Vector3f INACTIVE_COLOUR_OFFSET = new Vector3f(1, 1, 1);
  private static final Vector3f ACTIVE_COLOUR_OFFSET = new Vector3f(0.6f, 0.6f, 0.6f);

  public ButtonObject(Vector2f position, Vector2f scale, Mesh mesh, float xEdge, float xOffset, float yEdge, float yOffset) {
    super(position, scale, mesh, xEdge, xOffset, yEdge, yOffset);
    this.getMesh().getMaterial().setColorOffset(INACTIVE_COLOUR_OFFSET);
  }

  public void update(Window window) {
    updateColourOffset(window);
  }

  private void updateColourOffset(Window window) {
    if (super.isMouseOver(window)) {
      this.getMesh().getMaterial().setColorOffset(ACTIVE_COLOUR_OFFSET);
    } else {
      this.getMesh().getMaterial().setColorOffset(INACTIVE_COLOUR_OFFSET);
    }
  }
}
