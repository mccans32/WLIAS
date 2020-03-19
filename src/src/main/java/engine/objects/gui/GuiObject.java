package engine.objects.gui;

import engine.graphics.mesh.dimension.two.RectangleMesh;
import math.Vector3f;

public class GuiObject {
  private GuiImage guiImage;
  private GuiText guiText;

  /**
   * Instantiates a new Gui object.
   *
   * @param backgroundMesh   the background mesh
   * @param text             the text
   * @param fontSize         the font size
   * @param fontFileName     the font file name
   * @param numColumns       the num columns
   * @param numRows          the num rows
   * @param textColour       the text colour
   * @param edgeX            the edge x
   * @param offsetX          the offset x
   * @param edgeY            the edge y
   * @param offsetY          the offset y
   * @param centerHorizontal whether to centre text horizontally
   * @param centerVertical   whether to centre text vertically.
   */
  public GuiObject(RectangleMesh backgroundMesh, String text, float fontSize, String fontFileName,
                   int numColumns, int numRows, Vector3f textColour, float edgeX, float offsetX,
                   float edgeY, float offsetY, boolean centerHorizontal, boolean centerVertical) {
    this.guiImage = new GuiImage(backgroundMesh, edgeX, offsetX, edgeY, offsetY);

    this.guiText = new GuiText(text, fontSize, fontFileName, numColumns, numRows, textColour, edgeX,
        offsetX - (backgroundMesh.getWidth() / 2), edgeY,
        offsetY + (backgroundMesh.getHeight() / 2));

    centerText(centerHorizontal, centerVertical);
  }

  private void centerText(boolean centerHorizontal, boolean centerVertical) {
    if (centerHorizontal) {
      float textWidth = guiText.getWidth();
      guiText.setEdgeX(guiImage.getEdgeX());
      guiText.setOffsetX(guiImage.getOffsetX() - (textWidth / 2));
    }

    if (centerVertical) {
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
