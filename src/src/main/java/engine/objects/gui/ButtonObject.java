package engine.objects.gui;

import engine.Window;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.text.Text;
import math.Vector3f;
import math.Vector4f;

public class ButtonObject extends HudObject {
  private static final Vector4f DEFAULT_INACTIVE_COLOR_OFFSET = new Vector4f(1, 1, 1, 1);
  private static final Vector4f DEFAULT_ACTIVE_COLOR_OFFSET = new Vector4f(0.6f, 0.6f, 0.6f, 1);
  private Vector4f inactiveColourOffset = DEFAULT_INACTIVE_COLOR_OFFSET.copy();
  private Vector4f activeColourOffset = DEFAULT_ACTIVE_COLOR_OFFSET.copy();
  private Vector4f disabledColourOffset = new Vector4f(1, 1, 1, 0.2f);
  private boolean enabled = true;

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

  public ButtonObject(RectangleMesh backgroundMesh, float edgeX, float offsetX, float edgeY,
                      float offsetY) {
    this(backgroundMesh, new Text(""), edgeX, offsetX, edgeY, offsetY);
  }

  public static Vector4f getDefaultInactiveColorOffset() {
    return DEFAULT_INACTIVE_COLOR_OFFSET;
  }

  public static Vector4f getDefaultActiveColorOffset() {
    return DEFAULT_ACTIVE_COLOR_OFFSET;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Vector3f getInactiveColourOffset() {
    return inactiveColourOffset;
  }

  public void setInactiveColourOffset(Vector4f inactiveColourOffset) {
    this.inactiveColourOffset = inactiveColourOffset;
  }

  public Vector3f getActiveColourOffset() {
    return activeColourOffset;
  }

  public void setActiveColourOffset(Vector4f activeColourOffset) {
    this.activeColourOffset = activeColourOffset;
  }

  public void update(Window window) {
    updateColourOffset(window);
  }

  private void updateColourOffset(Window window) {
    if (!enabled) {
      this.getHudImage().getMesh().getMaterial().setColorOffset(disabledColourOffset);
    } else {
      if (isMouseOver(window)) {
        this.getHudImage().getMesh().getMaterial().setColorOffset(activeColourOffset);
      } else {
        this.getHudImage().getMesh().getMaterial().setColorOffset(inactiveColourOffset);
      }
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

  /**
   * Enable the button.
   */
  public void enable() {
    this.enabled = true;
    for (HudText line : this.getLines()) {
      line.getText().setAlpha(1);
    }
  }

  /**
   * Disable the button.
   */
  public void disable() {
    this.enabled = false;
    for (HudText line : this.getLines()) {
      line.getText().setAlpha(disabledColourOffset.getW());
    }
  }
}
