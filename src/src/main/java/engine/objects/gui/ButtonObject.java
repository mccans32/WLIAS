package engine.objects.gui;

import engine.Window;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.twoDimensional.RectangleMesh;
import math.Vector3f;

public class ButtonObject extends GuiImage {
  /**
   * Instantiates a new Gui object.
   *
   * @param mesh     the mesh.
   * @param edgeX    the x coordinate to offset from.
   * @param offsetX  amount to offset from the xEdge.
   * @param edgeY    the y coordinate to offset from.
   * @param offsetY  amount to offset from the yEdge.
   */
  private static final Vector3f INACTIVE_COLOUR_OFFSET = new Vector3f(1, 1, 1);
  private static final Vector3f ACTIVE_COLOUR_OFFSET = new Vector3f(0.6f, 0.6f, 0.6f);

  public ButtonObject(RectangleMesh mesh,
                      float edgeX,
                      float offsetX,
                      float edgeY,
                      float offsetY) {
    super(mesh, edgeX, offsetX, edgeY, offsetY);
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
