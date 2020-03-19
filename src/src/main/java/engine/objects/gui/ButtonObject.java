package engine.objects.gui;

import engine.Window;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.text.Text;
import math.Vector3f;

public class ButtonObject extends GuiObject {
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

  /**
   * Instantiates a new Button object.
   *
   * @param backgroundMesh the background mesh
   * @param text           the text
   * @param edgeX          the edge x
   * @param offsetX        the offset x
   * @param edgeY          the edge y
   * @param offsetY        the offset y
   */
  public ButtonObject(RectangleMesh backgroundMesh, Text text, float edgeX, float offsetX,
                      float edgeY, float offsetY) {
    super(backgroundMesh, text, edgeX, offsetX, edgeY, offsetY);
    this.getGuiImage().getMesh().getMaterial().setColorOffset(INACTIVE_COLOUR_OFFSET);
  }

  public void update(Window window) {
    updateColourOffset(window);
  }

  private void updateColourOffset(Window window) {
    if (isMouseOver(window)) {
      this.getGuiImage().getMesh().getMaterial().setColorOffset(ACTIVE_COLOUR_OFFSET);
    } else {
      this.getGuiImage().getMesh().getMaterial().setColorOffset(INACTIVE_COLOUR_OFFSET);
    }
  }

  public boolean isMouseOver(Window window) {
    return this.getGuiImage().isMouseOver(window);
  }

  public void destroy() {
    this.getGuiImage().destroy();
    this.getGuiText().destroy();
  }
}
