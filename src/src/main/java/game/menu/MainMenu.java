package game.menu;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex2D;
import engine.graphics.renderer.GuiRenderer;
import engine.objects.gui.GuiObject;
import engine.objects.world.Camera;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;

public class MainMenu {

  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_GREEN.brighter());
  private static final Vector3f DEFAULT_BUTTON_COLOUR = ColourUtils.convertColor(Color.white);
  private static final float DEFAULT_BUTTON_WIDTH = 1f;
  private static final float DEFAULT_BUTTON_HEIGHT = 0.75f;
  private static GuiObject startButton;

  public static void create(Window window) {
    createObjects();
    window.setBackgroundColour(
        BACKGROUND_COLOUR.getX(),
        BACKGROUND_COLOUR.getY(),
        BACKGROUND_COLOUR.getZ(),
        1f);
  }

  private static void createObjects() {
    createStartButton();
  }

  private static void createStartButton() {
    Vector2f position = new Vector2f(0, 0);
    Vector2f scale = new Vector2f(1, 1);
    Vertex2D topLeft = new Vertex2D(
        new Vector2f(
        -DEFAULT_BUTTON_WIDTH/2,
        DEFAULT_BUTTON_HEIGHT/2),
        new Vector3f(
            DEFAULT_BUTTON_COLOUR.getX(),
            DEFAULT_BUTTON_COLOUR.getY(),
            DEFAULT_BUTTON_COLOUR.getZ()),
        new Vector2f(0, 0));
    Vertex2D bottomLeft = new Vertex2D(
        new Vector2f(
            -DEFAULT_BUTTON_WIDTH/2,
            -DEFAULT_BUTTON_HEIGHT/2),
        new Vector3f(
            DEFAULT_BUTTON_COLOUR.getX(),
            DEFAULT_BUTTON_COLOUR.getY(),
            DEFAULT_BUTTON_COLOUR.getZ()),
        new Vector2f(0, 1));
    Vertex2D bottomRight = new Vertex2D(
        new Vector2f(
            DEFAULT_BUTTON_WIDTH/2,
            -DEFAULT_BUTTON_HEIGHT/2),
        new Vector3f(
            DEFAULT_BUTTON_COLOUR.getX(),
            DEFAULT_BUTTON_COLOUR.getY(),
            DEFAULT_BUTTON_COLOUR.getZ()),
        new Vector2f(1, 1));
    Vertex2D topRight = new Vertex2D(
        new Vector2f(
            DEFAULT_BUTTON_WIDTH/2,
            DEFAULT_BUTTON_HEIGHT/2),
        new Vector3f(
            DEFAULT_BUTTON_COLOUR.getX(),
            DEFAULT_BUTTON_COLOUR.getY(),
            DEFAULT_BUTTON_COLOUR.getZ()),
        new Vector2f(1, 0));

    Vertex2D[] vertices = new Vertex2D[] {topLeft, bottomLeft, bottomRight, topRight};
    int [] indices = new int[] {0, 3, 1, 2};
    Material material = new Material("/images/button_texture.jpg");
    Mesh mesh = new Mesh(vertices, indices, material);
    startButton = new GuiObject(position, scale, mesh);
    startButton.create();
  }

  public static void render(GuiRenderer renderer) {
    renderer.renderObject(startButton);
  }

}
