package engine.objects.gui;

import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.text.Text;

public class GuiObject {
  private GuiImage guiImage;
  private GuiText guiText;

  /**
   * Instantiates a new Gui object.
   *
   * @param backgroundMesh the background mesh
   * @param text           the text
   * @param edgeX          the edge x
   * @param offsetX        the offset x
   * @param edgeY          the edge y
   * @param offsetY        the offset y
   */
  public GuiObject(RectangleMesh backgroundMesh, Text text, float edgeX, float offsetX, float edgeY,
                   float offsetY) {
    this.guiImage = new GuiImage(backgroundMesh, edgeX, offsetX, edgeY, offsetY);

    this.guiText = new GuiText(text, edgeX, offsetX - (backgroundMesh.getWidth() / 2), edgeY,
        offsetY + (backgroundMesh.getHeight() / 2));

    centerText(text);
  }

  private void centerText(Text text) {
    if (text.isCentreHorizontal()) {
      float textWidth = guiText.getWidth();
      guiText.setEdgeX(guiImage.getEdgeX());
      guiText.setOffsetX(guiImage.getOffsetX() - (textWidth / 2));
    }

    if (text.isCentreVertical()) {
      float textHeight = guiText.getHeight();
      guiText.setEdgeY(guiImage.getEdgeY());
      guiText.setOffsetY(guiImage.getOffsetY() + (textHeight / 2));
    }
  }

  public GuiImage getGuiImage() {
    return guiImage;
  }

  public GuiText getGuiText() {
    return guiText;
  }

  public void create() {
    guiImage.create();
    guiText.create();
  }

  public void reposition() {
    guiImage.reposition();
    guiText.reposition();
  }
}
