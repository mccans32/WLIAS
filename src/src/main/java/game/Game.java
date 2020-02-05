package game;

import engine.Input;
import engine.Window;
import org.lwjgl.glfw.GLFW;

/**
 * The type Game.
 */
public class Game {

  /**
   * The Window width.
   */
  static final int WINDOW_WIDTH = 1200;
  /**
   * The Window height.
   */
  static final int WINDOW_HEIGHT = 900;
  /**
   * The Window tile.
   */
  static final String WINDOW_TILE = "We Live in a Society";
  /**
   * The Background red.
   */
  static final float BACKGROUND_RED = 1.0f;
  /**
   * The Background green.
   */
  static final float BACKGROUND_GREEN = 0.0f;
  /**
   * The Background blue.
   */
  static final float BACKGROUND_BLUE = 0.0f;
  /**
   * The Background alpha.
   */
  static final float BACKGROUND_ALPHA = 1.0f;
  /**
   * The Window.
   */
  public Window window;

  /**
   * Start.
   */
  public void start() {
    initialize();
    gameLoop();
    dispose();
  }

  private void dispose() {
    System.out.println("Disposing active processes");
    window.destroy();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loop\n");
    // while (true)
    while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
      update();
      render();
    }
  }

  private void initialize() {
    System.out.println("Initializing Simulation\n");
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);
    window.setBackgroundColour(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE, BACKGROUND_ALPHA);
    window.create();
  }

  /**
   * Update.
   */
  public void update() {
    window.update();
    // Temporary
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      System.out.println("X: " + Input.getMouseX() + ", Y: " + Input.getMouseY());
    }
  }

  /**
   * Render.
   */
  public void render() {
    window.swapBuffers();
  }
}