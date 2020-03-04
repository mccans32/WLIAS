package game.world;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex2D;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.objects.gui.GuiObject;
import engine.objects.gui.GuiText;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;
import math.Vector3f;

public class Gui {
  private static final String TEMP_SENTENCE = "0";
  private static final String FONT_FILE_DIRECTORY = "/text/defaultFont.png";
  private static final Vector3f DEFAULT_TEXT_COLOUR = ColourUtils.convertColor(Color.BLACK);
  private static final int NUM_COLUMNS = 16;
  private static final int NUM_ROWS = 16;
  private static GuiObject tempGui;
  private static GuiText tempText;
  private static float[] TEMP_GUI_VALUES = {-1, 0.1f, 1, -0.1f};

  public static void create(Window window) {
    createObjects(window);
  }

  public static void update(Window window) {
    resize(window);
  }

  private static void createObjects(Window window) {
    createTempText(window);
    createTempGui(window);
  }

  private static void createTempGui(Window window) {
    Mesh guiMesh = new Mesh(
        new Vertex2D[] {
            new Vertex2D(new Vector2f(-0.1f, 0.1f),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
            new Vertex2D(new Vector2f(-0.1f, -0.1f),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
            new Vertex2D(new Vector2f(0.1f, -0.1f),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
            new Vertex2D(new Vector2f(0.1f, 0.1f),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))},
        new int[] {0, 3, 1, 2},
        new Material("/images/button_texture.jpg"));

    tempGui = new GuiObject(
        new Vector2f(-1 * window.getSpanX() + 0.1f, 1 * window.getSpanY() - 0.1f),
        new Vector2f(1, 1),
        guiMesh,
        TEMP_GUI_VALUES[0],
        TEMP_GUI_VALUES[1],
        TEMP_GUI_VALUES[2],
        TEMP_GUI_VALUES[3]);
    tempGui.create();
  }

  private static void createTempText(Window window) {
    tempText = new GuiText(TEMP_SENTENCE, FONT_FILE_DIRECTORY, NUM_COLUMNS, NUM_ROWS,
        DEFAULT_TEXT_COLOUR,
        new Vector2f(-1 * window.getSpanX() + 0, 1 * window.getSpanY() + 0),
        new Vector2f(1, 1),
        -1f, 0, 1f, 0);
    tempText.getMesh().createText();
  }

  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    textRenderer.renderObject(tempText);
//    guiRenderer.renderObject(tempGui);
  }

  public static void resize(Window window) {
    tempText.reposition(window.getSpanX(), window.getSpanY());
    tempGui.reposition(window.getSpanX(), window.getSpanY());
  }
}