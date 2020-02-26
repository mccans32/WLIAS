package game;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Vertex2D;
import engine.graphics.Vertex3D;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.io.Input;
import engine.objects.gui.GuiObject;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.utils.ColourUtils;
import game.menu.MainMenu;
import java.awt.Color;

import game.world.GUI;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.jfree.data.json.JSONUtils;
import org.lwjgl.glfw.GLFW;

/**
 * The type Game.
 */
public class Game {

  static final int WINDOW_WIDTH = 1200;
  static final int WINDOW_HEIGHT = 900;
  static final String WINDOW_TILE = "We Live in a Society";
  static final String SHADERS_PATH = "/shaders/";
  static final String VERTEX_DIRECTORY = "vertex/";
  static final String FRAGMENT_DIRECTORY = "fragment/";
  static final String VERTEX_SHADER_FILE_NAME = "mainVertex.glsl";
  static final String FRAGMENT_SHADER_FILE_NAME = "mainFragment.glsl";
  static final String GUI_VERTEX_SHADER_FILE_NAME = "guiVertex.glsl";
  static final String GUI_FRAGMENT_SHADER_FILE_NAME = "guiFragment.glsl";

  private static WorldRenderer worldRenderer;
  private static GuiRenderer guiRenderer;
  public Camera camera = new Camera(new Vector3f(0, 0, 10f), new Vector3f(0, 0, 0));
  private GameState state = GameState.GAME;
  /**
   * The Window.
   */
  private Window window;
  private float knownAspect = (float) WINDOW_WIDTH / (float) WINDOW_HEIGHT;
  private Shader worldShader;
  private Shader guiShader;
  // Temporary Mesh
  private Mesh tempMesh = new Mesh(
      new Vertex3D[]{
          new Vertex3D(new Vector3f(-0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
          new Vertex3D(new Vector3f(-0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
          new Vertex3D(new Vector3f(0.5f, -0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
          new Vertex3D(new Vector3f(0.5f, 0.5f, 0),
              ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))
      },
      new int[]{0, 3, 1, 2},
      new Material("/images/mid-tier-tile.png"));
  private GameObject tempObject = new GameObject(
      new Vector3f(0, 0, 0f),
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
    worldShader.destroy();
    guiShader.destroy();
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
    worldShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + FRAGMENT_SHADER_FILE_NAME);
    // Initialise GUI Shader
    guiShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + GUI_VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + GUI_FRAGMENT_SHADER_FILE_NAME);
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);
    // Initialise WorldRenderer
    worldRenderer = new WorldRenderer(window, worldShader);
    guiRenderer = new GuiRenderer(window, guiShader);
    window.create();
    // Create World Shader
    worldShader.create();
    // Create GUI Shader
    guiShader.create();

    if (state == GameState.GAME) {
      /* Create Temporary Mesh; */
      tempObject.create();
//      create GUI
      GUI.create(window);
    }
    else if (state == GameState.MAIN_MENU) {
      MainMenu.create(window);
    }

  }

  /**
   * Update.
   */
  public void update() {
    camera.update();
    window.update();

    if (this.knownAspect != window.getAspect()) {
      // Reposition GUI Elements
      GUI.resize(window);
      // update Known Aspect
      this.knownAspect = window.getAspect();
    }
  }

  /**
   * Render.
   */
  public void render() {
    if (state == GameState.MAIN_MENU) {
      MainMenu.render(guiRenderer);
    }
    else {
      // Render all game objects
      Vector3f background = ColourUtils.convertColor(ChartColor.VERY_LIGHT_CYAN.brighter());
      window.setBackgroundColour(background.getX(), background.getY(), background.getZ(), 1);
      GUI.render(guiRenderer);
      worldRenderer.renderObject(tempObject, camera);
      window.swapBuffers();
    }
  }
}
