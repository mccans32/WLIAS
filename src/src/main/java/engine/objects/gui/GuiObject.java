package engine.objects.gui;

import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.twoDimensional.RectangleMesh;
import math.Vector3f;

public class GuiObject {
  private GuiImage guiImage;
  private GuiText guiText;

  public GuiObject(RectangleMesh backgroundMesh, String text, float fontSize, String fontFileName,
                   int numColumns, int numRows, Vector3f textColour, float edgeX, float offsetX,
                   float edgeY, float offsetY, boolean centerHorizontal, boolean centerVertical) {
    this.guiImage = new GuiImage(backgroundMesh, edgeX, offsetX, edgeY, offsetY);
    this.guiText = new GuiText(text, fontSize, fontFileName, numColumns, numRows, textColour, edgeX,
        offsetX, edgeY, offsetY);
  }

  private void centerText(boolean centerHorizontal, boolean centerVertical) {
    // To center the text we utilise the idea of padding.
    // Get the total length or height of text then reposition half of this amount from the center
    // of the GuiImage
    if (centerHorizontal) {
      float textWidth = guiText.getWidth();
      float imageWidth = guiImage.getMesh().getWidth();
    }

    if (centerVertical) {
      float textHeight = guiText.getHeight();
      float imageHeight = guiImage.getMesh().getHeight();
    }
  }

  public GuiImage getGuiImage() {
    return guiImage;
  }

  public GuiText getGuiText() {
    return guiText;
  }
}
