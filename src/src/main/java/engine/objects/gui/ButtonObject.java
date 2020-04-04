package engine.objects.gui;

import engine.Window;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.text.Text;
import math.Vector3f;

public class ButtonObject extends HudObject {
  private Vector3f inactiveColourOffset = new Vector3f(1, 1, 1);
  private Vector3f activeColourOffset = new Vector3f(0.6f, 0.6f, 0.6f);

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
    this.getHudImage().getMesh().getMaterial().setColorOffset(inactiveColourOffset);
  }

  public Vector3f getInactiveColourOffset() {
    return inactiveColourOffset;
  }

  public void setInactiveColourOffset(Vector3f inactiveColourOffset) {
    this.inactiveColourOffset = inactiveColourOffset;
  }

  public Vector3f getActiveColourOffset() {
    return activeColourOffset;
  }

  public void setActiveColourOffset(Vector3f activeColourOffset) {
    this.activeColourOffset = activeColourOffset;
  }

  public void update(Window window) {
    updateColourOffset(window);
  }

  private void updateColourOffset(Window window) {
    if (isMouseOver(window)) {
      this.getHudImage().getMesh().getMaterial().setColorOffset(activeColourOffset);
    } else {
      this.getHudImage().getMesh().getMaterial().setColorOffset(inactiveColourOffset);
    }
  }

  public boolean isMouseOver(Window window) {
    return this.getHudImage().isMouseOver(window);
  }

  /**
   * Destroy meshes.
   */
  public void destroy() {
    this.getHudImage().destroy();
    for (HudText line : getLines()) {
      line.destroy();
    }
  }
}
