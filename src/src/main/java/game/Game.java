package game;

import engine.Window;
import engine.graphics.Shader;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.tools.MousePicker;
import game.menu.MainMenu;
import game.world.Gui;
import game.world.World;
import math.Vector3f;

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
  static final String TEXT_VERTEX_SHADER_FILE_NAME = "textVertex.glsl";
  static final String TEXT_FRAGMENT_SHADER_FILE_NAME = "textFragment.glsl";
  private static GameState state = GameState.MAIN_MENU;
  private static WorldRenderer worldRenderer;
  private static GuiRenderer guiRenderer;
  private static TextRenderer textRenderer;
  public Camera camera = new Camera(new Vector3f(0, 0, 10f), new Vector3f(0, 0, 0));
  private MousePicker mousePicker;
  /**
   * The Window.
   */
  private Window window;
  private Shader worldShader;
  private Shader guiShader;
  private Shader textShader;

  public static GameState getState() {
    return state;
  }

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
    worldShader.destroy();
    guiShader.destroy();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loop\n");
    // while (true)
    while (!window.shouldClose()) {
      update();
      render();
    }
  }

  private void initialize() {
    System.out.println("Initializing Simulation\n");
    // Initialise the Shader
    worldShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + FRAGMENT_SHADER_FILE_NAME);
    // Initialise Gui Shader
    guiShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + GUI_VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + GUI_FRAGMENT_SHADER_FILE_NAME);
    // Initialise Text Shader
    textShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + TEXT_VERTEX_SHADER_FILE_NAME,
        SHADERS_PATH + FRAGMENT_DIRECTORY + TEXT_FRAGMENT_SHADER_FILE_NAME);
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);

    // Initialise WorldRenderer
    worldRenderer = new WorldRenderer(window, worldShader);
    guiRenderer = new GuiRenderer(window, guiShader);
    textRenderer = new TextRenderer(window, guiShader);
    window.create();
    // Create World Shader
    worldShader.create();
    // Create Gui Shader
    guiShader.create();
    // Create Text Shader
    textShader.create();
    // Create Mouse Picker
    mousePicker = new MousePicker(camera, window.getProjectionMatrix());

    if (state == GameState.MAIN_MENU) {
      // Create the Main Menu
      MainMenu.create(window);
    } else if (state == GameState.GAME) {
      //  create Gui
      Gui.create();
      World.create(camera);
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

    if (state == GameState.MAIN_MENU) {
      MainMenu.update(window, camera);
    } else { // state == GameState.GAME
      // Update The World
      World.update();
      // Update The Gui
      Gui.update();
    }
  }

  /**
   * Render.
   */
  public void render() {
    if (state == GameState.MAIN_MENU) {
      MainMenu.render(guiRenderer, textRenderer);
    } else { // state == GameState.GAME;
      // Render world objects
      World.render(worldRenderer, camera);
      // Render all game objects
      Gui.render(guiRenderer, textRenderer);
    }
    window.swapBuffers();
  }
}
