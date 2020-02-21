package game;

import engine.Input;
import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex2D;
import engine.objects.GameObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;
import math.Vector3f;
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
   * The Simulation Renderer.
   */

  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_DIRECTORY = "vertex/";
  static final String FRAGMENT_DIRECTORY = "fragment/";
  static final String VERTEX_SHADER_FILE_NAME = "mainVertex.glsl";
  static final String FRAGMENT_SHADER_FILE_NAME = "mainFragment.glsl";

  private static Renderer renderer;
  /**
   * The Window.
   */
  private Window window;
  private Shader shader;

  // Temporary Mesh
  private Mesh tempMesh = new Mesh(
      new Vertex2D[] {
          new Vertex2D(new Vector2f(-0.5f, 0.5f),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
          new Vertex2D(new Vector2f(-0.5f, -0.5f),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
          new Vertex2D(new Vector2f(0.5f, -0.5f),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
          new Vertex2D(new Vector2f(0.5f, 0.5f),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))
      },
      new int[] {0, 3, 1, 2},
      new Material("/images/mid-tier-tile.png"));

  private GameObject tempObject = new GameObject(
      new Vector2f(0, 0),
      new Vector3f(0, 0, 0),
      new Vector2f(1f, 1f),
      tempMesh);

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
    tempMesh.destroy();
    shader.destroy();
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
    // Initialise the Shader
    shader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + FRAGMENT_SHADER_FILE_NAME);
    // Initialise Renderer
    renderer = new Renderer(shader);
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);
    window.setBackgroundColour(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE, BACKGROUND_ALPHA);
    window.create();
    // Create Shader
    shader.create();
    //  Create Temporary Mesh;
    tempMesh.create();
  }

  /**
   * Update.
   */
  public void update() {
    tempObject.update();
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
    // Render all game objects
    renderer.renderObject(tempObject);
    window.swapBuffers();
  }
}
