package engine.objects.gui;

import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import java.util.ArrayList;

public class HudObject {
  private HudImage hudImage;
  private ArrayList<HudText> lines = new ArrayList<>();

  /**
   * Instantiates a new Hud object.
   *
   * @param backgroundMesh the background mesh
   * @param text           the text
   * @param edgeX          the edge x
   * @param offsetX        the offset x
   * @param edgeY          the edge y
   * @param offsetY        the offset y
   */
  public HudObject(RectangleMesh backgroundMesh, Text text, float edgeX, float offsetX, float edgeY,
                   float offsetY) {
    // Create Background
    this.hudImage = new HudImage(backgroundMesh, edgeX, offsetX, edgeY, offsetY);
    calculateLines(text, this.hudImage);
    centerText();
  }

  private void calculateLines(Text text, HudImage backgroundImage) {
    StringBuilder currentSentence = new StringBuilder();
    if (text.shouldWrap()) {
      float charWidth = HudText.calculateCharWidth(text);
      float backgroundWidth = backgroundImage.getMesh().getModel().getWidth();
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

  private void createNewLine(StringBuilder sentence, Text text, HudImage backgroundImage) {
    // Create Text
    Text lineText = text.copy();
    lineText.setString(sentence.toString());
    // Add the sentence to lines
    lines.add(createGuiText(lineText, backgroundImage, lines.size()));
    sentence.setLength(0);
  }

  private HudText createGuiText(Text text, HudImage backgroundImage, int lineNumber) {
    return new HudText(text, backgroundImage.getEdgeX(),
        backgroundImage.getOffsetX() - (backgroundImage.getMesh().getModel().getWidth() / 2),
        backgroundImage.getEdgeY(),
        backgroundImage.getOffsetY() + (backgroundImage.getMesh().getModel().getHeight() / 2)
            - (HudText.calculateCharHeight(text) * lineNumber));
  }

  private void centerText() {
    for (HudText line : lines) {
      if (line.getText().isCentreHorizontal()) {
        float textWidth = line.getWidth();
        line.setEdgeX(hudImage.getEdgeX());
        line.setOffsetX(hudImage.getOffsetX() - (textWidth / 2));
      }
      if (line.getText().isCentreVertical()) {
        float textHeight = line.getHeight();
        line.setEdgeY(hudImage.getEdgeY());
        line.setOffsetY(hudImage.getOffsetY() + (textHeight / 2));
      }
    }
  }

  public HudImage getHudImage() {
    return hudImage;
  }

  /**
   * Create the meshes.
   */
  public void create() {
    hudImage.create();
    for (HudText line : lines) {
      line.create();
    }
  }

  /**
   * Reposition.
   */
  public void reposition() {
    hudImage.reposition();
    centerText();
    for (HudText line : lines) {
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
    this.hudImage.render(guiRenderer);

    for (HudText line : lines) {
      line.render(textRenderer);
    }
  }

  public ArrayList<HudText> getLines() {
    return lines;
  }
}
