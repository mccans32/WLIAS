package game.world;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex2D;
import engine.graphics.renderer.GuiRenderer;
import engine.objects.gui.GuiObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;

public class Gui {
  private static GuiObject tempGui;
  private static float[] TEMP_GUI_VALUES = {-1, 0.1f, 1, -0.1f};

  public static void create(Window window) {
    createObjects(window);
  }

  public static void update(Window window) {
    resize(window);
  }

  private static void createObjects(Window window) {
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

  public static void render(GuiRenderer renderer) {
    renderer.renderObject(tempGui);
  }

  public static void resize(Window window) {
    tempGui.reposition(window.getSpanX(), window.getSpanY());
  }
}