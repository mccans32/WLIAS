package game.menu;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex2D;
import engine.graphics.renderer.GuiRenderer;
import engine.io.Input;
import engine.objects.gui.ButtonObject;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.world.Gui;
import game.world.World;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;

public class MainMenu {
  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());
  private static float BUTTON_WIDTH = 0.7f;
  private static float BUTTON_HEIGHT = 0.3f;
  private static String BUTTON_TEXTURE = "/images/button_texture.jpg";
  private static float[] START_REPOSITION_VALUES = {0, 0, 1, -0.6f};
  private static float[] EXIT_REPOSITION_VALUES = {0, 0, -1, 0.6f};
  private static Vector3f BUTTON_COLOUR = new Vector3f(0, 0, 0);
  private static Vertex2D TOP_LEFT_VERTEX = new Vertex2D(
      new Vector2f(-BUTTON_WIDTH / 2, BUTTON_HEIGHT / 2),
      BUTTON_COLOUR,
      new Vector2f(0, 0));
  private static Vertex2D TOP_RIGHT_VERTEX = new Vertex2D(
      new Vector2f(BUTTON_WIDTH / 2, BUTTON_HEIGHT / 2),
      BUTTON_COLOUR,
      new Vector2f(1, 0));
  private static Vertex2D BOTTOM_LEFT_VERTEX = new Vertex2D(
      new Vector2f(-BUTTON_WIDTH / 2, -BUTTON_HEIGHT / 2),
      BUTTON_COLOUR,
      new Vector2f(0, 1));
  private static Vertex2D BOTTOM_RIGHT_VERTEX = new Vertex2D(
      new Vector2f(BUTTON_WIDTH / 2, -BUTTON_HEIGHT / 2),
      BUTTON_COLOUR,
      new Vector2f(1, 1));
  private static int[] BUTTON_INDICES = {0, 1, 2, 3};
  private static ButtonObject startButton;
  private static ButtonObject exitButton;
  private static ButtonObject[] buttons = new ButtonObject[2];

  public static void create(Window window) {
    setBackgroundColour(BACKGROUND_COLOUR, window);
    createButtons(window);
  }

  /**
   * Update.
   *
   * @param window the window
   */
  public static void update(Window window) {
    resize(window);
    updateButtons(window);
    checkButtonClick(window);
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    // Destroy Buttons
    for (ButtonObject button : buttons) {
      button.destroy();
    }
  }


  private static void updateButtons(Window window) {
    for (ButtonObject button : buttons) {
      button.update(window);
    }
  }

  /**
   * Check button click.
   *
   * @param window the window
   */
  public static void checkButtonClick(Window window) {
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      if (startButton.isMouseOver(window)) {
        // Change the Game State
        Game.setState(GameState.GAME);
        // Destroy the Main Menu
        destroy();
        // Create the Gui
        Gui.create(window);
        // Create the World
        World.create(window);
      } else if (exitButton.isMouseOver(window)) {
        GLFW.glfwSetWindowShouldClose(window.getWindow(), true);
      }
    }
  }

  public static ButtonObject[] getButtonObjects() {
    return buttons;
  }

  private static void createButtons(Window window) {
    // Create Start Button
    startButton = initButton(
        window,
        START_REPOSITION_VALUES[0],
        START_REPOSITION_VALUES[1],
        START_REPOSITION_VALUES[2],
        START_REPOSITION_VALUES[3]);
    startButton.create();
    buttons[0] = startButton;
    // Create Exit Button
    exitButton = initButton(
        window,
        EXIT_REPOSITION_VALUES[0],
        EXIT_REPOSITION_VALUES[1],
        EXIT_REPOSITION_VALUES[2],
        EXIT_REPOSITION_VALUES[3]);
    exitButton.create();
    buttons[1] = exitButton;
  }

  public static void render(GuiRenderer renderer) {
    renderer.renderObject(startButton);
    renderer.renderObject(exitButton);
  }

  public static void resize(Window window) {
    startButton.reposition(window.getSpanX(), window.getSpanY());
  }

  private static void setBackgroundColour(Vector3f colour, Window window) {
    window.setBackgroundColour(colour.getX(), colour.getY(), colour.getZ(), 1f);
  }

  private static ButtonObject initButton(
      Window window,
      float edgeX,
      float offsetX,
      float edgeY,
      float offsetY) {
    Mesh tempMesh = new Mesh(
        new Vertex2D[] {TOP_LEFT_VERTEX, TOP_RIGHT_VERTEX, BOTTOM_LEFT_VERTEX, BOTTOM_RIGHT_VERTEX},
        BUTTON_INDICES,
        new Material(BUTTON_TEXTURE));

    return new ButtonObject(
        new Vector2f(edgeX * window.getSpanX() + offsetX, edgeY * window.getSpanY() + offsetY),
        new Vector2f(1, 1),
        tempMesh,
        edgeX,
        offsetX,
        edgeY,
        offsetY);
  }
}