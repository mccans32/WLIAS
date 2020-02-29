package game;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.graphics.Vertex3D;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.io.Input;
import engine.objects.gui.GuiObject;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.tools.MousePicker;
import engine.utils.ColourUtils;
import game.menu.MainMenu;
import game.world.GUI;
import java.awt.Color;
import java.util.Arrays;
import math.Vector2f;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.Sys;
import sun.rmi.rmic.Main;

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
  public static GameState state = GameState.MAIN_MENU;
  private MousePicker mousePicker;
  /**
   * The Window.
   */
  private Window window;
  private float knownAspect = (float) WINDOW_WIDTH / (float) WINDOW_HEIGHT;
  private Shader worldShader;
  private Shader guiShader;
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
      new Vector3f(0, 0, 0f),
      new Vector3f(0, 0, 0),
      new Vector3f(1f, 1f, 1f),
      tempMesh);

  public static void setState(GameState state) {
    Game.state = state;
  }

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
    // Create Mouse Picker
    mousePicker = new MousePicker(camera, window.getProjectionMatrix());

    if (state == GameState.MAIN_MENU) {
      // Create the Main Menu
      MainMenu.create(window);
    } else if (state == GameState.GAME) {
      /* Create Temporary Mesh; */
      tempObject.create();
      //  create GUI
      GUI.create(window);
    }

  }

  /**
   * Update.
   */
  public void update() {

    camera.update();
    window.update();

    // Update Mouse Picker
    mousePicker.update(window);
    Vector3f ray = mousePicker.getCurrentRay();
//    System.out.println(ray.getX() + ", " + ray.getY() + ", " + ray.getZ());
//    System.out.println("RAY DIRECTION: " + Vector3f.length(ray));

    if (state == GameState.MAIN_MENU) {
      MainMenu.update(window);
    }

    if (this.knownAspect != window.getAspect()) {
      // Reposition GUI Elements
      if (state == GameState.MAIN_MENU) {
        MainMenu.resize(window);
      } else if (state == GameState.GAME) {
        GUI.resize(window);
      }
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
    } else {
      // Render all game objects
      GUI.render(guiRenderer);
      worldRenderer.renderObject(tempObject, camera);
    }
    window.swapBuffers();
  }
}
