package game;

import engine.Input;
import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex3D;
import engine.objects.Camera;
import engine.objects.GameObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;

/**
 * The type Game.
 */
public class Game {

  static final int WINDOW_WIDTH = 1200;
  static final int WINDOW_HEIGHT = 900;
  static final String WINDOW_TILE = "We Live in a Society";
  static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());
  static final float BACKGROUND_RED = BACKGROUND_COLOUR.getX();
  static final float BACKGROUND_GREEN = BACKGROUND_COLOUR.getY();
  static final float BACKGROUND_BLUE = BACKGROUND_COLOUR.getZ();
  static final float BACKGROUND_ALPHA = 1f;
  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_DIRECTORY = "vertex/";
  static final String FRAGMENT_DIRECTORY = "fragment/";
  static final String VERTEX_SHADER_FILE_NAME = "mainVertex.glsl";
  static final String FRAGMENT_SHADER_FILE_NAME = "mainFragment.glsl";

  private static Renderer renderer;
  public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
  /**
   * The Window.
   */
  private Window window;
  private Shader shader;
  // Temporary Mesh
  private Mesh tempMesh = new Mesh(
      new Vertex3D[] {
          new Vertex3D(new Vector3f(-0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
          new Vertex3D(new Vector3f(-0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
          new Vertex3D(new Vector3f(0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
          new Vertex3D(new Vector3f(0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))
      },
      new int[] {0, 3, 1, 2},
      new Material("/images/mid-tier-tile.png"));
  private GameObject tempObject = new GameObject(
      new Vector3f(0, 0, 0),
      new Vector3f(0, 0, 0),
      new Vector3f(1f, 1f, 1f),
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
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);
    window.setBackgroundColour(BACKGROUND_RED, BACKGROUND_GREEN, BACKGROUND_BLUE, BACKGROUND_ALPHA);
    // Initialise Renderer
    renderer = new Renderer(window, shader);
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
    window.update();
    tempObject.getPosition().add(0, 0, -0.1f);
    tempObject.getRotation().add(0, 0, 1f);
  }

  /**
   * Render.
   */
  public void render() {
    // Render all game objects
    renderer.renderObject(tempObject, camera);
    window.swapBuffers();
  }
}
