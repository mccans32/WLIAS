package engine.objects.gui;

import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import java.util.ArrayList;

public class GuiObject {
  private GuiImage guiImage;
  private ArrayList<GuiText> lines = new ArrayList<>();

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
    // Create Background
    this.guiImage = new GuiImage(backgroundMesh, edgeX, offsetX, edgeY, offsetY);
    calculateLines(text, this.guiImage);
    centerText();
  }

  private void calculateLines(Text text, GuiImage backgroundImage) {
    StringBuilder currentSentence = new StringBuilder();
    if (text.shouldWrap()) {
      float charWidth = GuiText.calculateTitleWidth(text);
      float backgroundWidth = backgroundImage.getMesh().getWidth();
      String[] stringArray = text.getString().split(" ");

      for (int i = 0; i < stringArray.length; i++) {
        String word = stringArray[i];
        float sentenceWidth = charWidth * currentSentence.length();
        float wordWidth = charWidth * word.length();

        if (word.equals("\n")) {
          // create a line separation
          if (currentSentence.length() > 0) {
            createNewLine(currentSentence, text, backgroundImage);
          }
          createNewLine(currentSentence, text, backgroundImage);
        } else {
          if (sentenceWidth == 0 || sentenceWidth + wordWidth <= backgroundWidth) {
            // Sentence is less than width, just check if we need to add a space
            if (sentenceWidth > 0) {
              currentSentence.append(" ");
            }
          } else {
            // Sentence is greater than width, make a new line
            createNewLine(currentSentence, text, backgroundImage);
          }
          // Add our current word to the sentence
          currentSentence.append(word);

          // We have hit the last word
          if (i == stringArray.length - 1) {
            createNewLine(currentSentence, text, backgroundImage);
          }
        }
      }
    } else {
      currentSentence.append(text.getString());
      createNewLine(currentSentence, text, backgroundImage);
    }
  }

  private void createNewLine(StringBuilder sentence, Text text, GuiImage backgroundImage) {
    // Create Text
    Text lineText = text.copy();
    lineText.setString(sentence.toString());
    // Add the sentence to lines
    lines.add(createGuiText(lineText, backgroundImage, lines.size()));
    sentence.setLength(0);
  }

  private GuiText createGuiText(Text text, GuiImage backgroundImage, int lineNumber) {
    return new GuiText(text, backgroundImage.getEdgeX(),
        backgroundImage.getOffsetX() - (backgroundImage.getMesh().getWidth() / 2),
        backgroundImage.getEdgeY(),
        backgroundImage.getOffsetY() + (backgroundImage.getMesh().getHeight() / 2)
            - (GuiText.calculateTitleHeight(text) * lineNumber));
  }

  private void centerText() {
    for (GuiText line : lines) {
      if (line.getText().isCentreHorizontal()) {
        float textWidth = line.getWidth();
        line.setEdgeX(guiImage.getEdgeX());
        line.setOffsetX(guiImage.getOffsetX() - (textWidth / 2));
      }
      if (line.getText().isCentreVertical()) {
        float textHeight = line.getHeight();
        line.setEdgeY(guiImage.getEdgeY());
        line.setOffsetY(guiImage.getOffsetY() + (textHeight / 2));
      }
    }
  }

  public GuiImage getGuiImage() {
    return guiImage;
  }

  /**
   * Create the meshes.
   */
  public void create() {
    guiImage.create();
    for (GuiText line : lines) {
      line.create();
    }
  }

  /**
   * Reposition.
   */
  public void reposition() {
    guiImage.reposition();
    centerText();
    for (GuiText line : lines) {
      line.reposition();
    }
  }

  /**
   * Render.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    this.guiImage.render(guiRenderer);

    for (GuiText line : lines) {
      line.render(textRenderer);
    }
  }

  public ArrayList<GuiText> getLines() {
    return lines;
  }
}
