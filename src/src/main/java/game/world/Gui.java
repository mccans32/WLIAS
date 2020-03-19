package game.world;

import engine.graphics.Material;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.objects.gui.GuiImage;
import engine.objects.gui.GuiText;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector3f;

public class Gui {
  private static final String TEMP_SENTENCE = "0";
  private static final String FONT_FILE_DIRECTORY = "/text/defaultFont.png";
  private static final Vector3f DEFAULT_TEXT_COLOUR = ColourUtils.convertColor(Color.BLACK);
  private static final int NUM_COLUMNS = 16;
  private static final int NUM_ROWS = 16;
  private static GuiImage tempGui;
  private static GuiText tempText;
  private static float[] TEMP_GUI_VALUES = {-1, 0.1f, 1, -0.1f};

  public static void create() {
    createObjects();
  }

  public static void update() {
    resize();
  }

  private static void createObjects() {
    createTempText();
    createTempGui();
    createTempObject();
  }

  private static void createTempGui() {
    RectangleMesh guiMesh = new RectangleMesh(0.2f, 0.2f,
        new Material("/images/button_texture.jpg"));

    tempGui = new GuiImage(guiMesh, TEMP_GUI_VALUES[0], TEMP_GUI_VALUES[1], TEMP_GUI_VALUES[2],
        TEMP_GUI_VALUES[3]);
    tempGui.create();
  }

  private static void createTempText() {
    tempText = new GuiText(TEMP_SENTENCE, 1f, FONT_FILE_DIRECTORY, NUM_COLUMNS, NUM_ROWS,
        DEFAULT_TEXT_COLOUR, -1f, 0, 1f, 0);
    tempText.create();
  }

  private static void createTempObject() {
  }

  /**
   * Render Gui Elements and Text Elements in the world setting.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    //Render Gui Elements First to fix alpha blending on text
    renderImages(guiRenderer);
    renderTexts(textRenderer);
    renderObjects(guiRenderer, textRenderer);
  }

  private static void renderImages(GuiRenderer renderer) {
    renderer.renderObject(tempGui);
  }

  private static void renderTexts(TextRenderer renderer) {
    renderer.renderObject(tempText);
  }

  private static void renderObjects(GuiRenderer guiRenderer, TextRenderer textRenderer) {
  }

  public static void resize() {
    tempText.reposition();
    tempGui.reposition();
  }
}