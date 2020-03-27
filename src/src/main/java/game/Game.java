package game;

import engine.Window;
import engine.audio.AudioMaster;
import engine.audio.Source;
import engine.graphics.Shader;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import game.menu.MainMenu;
import game.world.Hud;
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
  static final String VERTEX_SHADER = "mainVertex.glsl";
  static final String FRAGMENT_SHADER = "mainFragment.glsl";
  static final String GUI_VERTEX_SHADER = "guiVertex.glsl";
  static final String GUI_FRAGMENT_SHADER = "guiFragment.glsl";
  static final String TEXT_VERTEX_SHADER = "textVertex.glsl";
  static final String TEXT_FRAGMENT_SHADER = "textFragment.glsl";
  static final String BACKGROUND_SHADER = "backgroundVertex.glsl";
  private static GameState state = GameState.MAIN_MENU;
  private static WorldRenderer worldRenderer;
  private static GuiRenderer guiRenderer;
  private static TextRenderer textRenderer;
  private static GuiRenderer backgroundRenderer;
  private static Source musicSource;
  public Camera camera = new Camera(new Vector3f(0, 0, 10f), new Vector3f(30, 0, 0));
  /**
   * The Window.
   */
  private Window window;
  private Shader worldShader;
  private Shader guiShader;
  private Shader textShader;
  private Shader backgroundShader;

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
    textShader.destroy();
    backgroundShader.destroy();
    musicSource.destroy();
    AudioMaster.cleanUp();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loop\n");
    while (!window.shouldClose()) {
      update();
      render();
    }
  }

  private void initialize() {
    System.out.println("Initializing Simulation\n");
    // Initialise the Shader
    worldShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + VERTEX_SHADER,
        SHADERS_PATH + FRAGMENT_DIRECTORY + FRAGMENT_SHADER);
    // Initialise Hud Shader
    guiShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + GUI_VERTEX_SHADER,
        SHADERS_PATH + FRAGMENT_DIRECTORY + GUI_FRAGMENT_SHADER);
    backgroundShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + BACKGROUND_SHADER,
        SHADERS_PATH + FRAGMENT_DIRECTORY + GUI_FRAGMENT_SHADER);
    // Initialise Text Shader
    textShader = new Shader(SHADERS_PATH + VERTEX_DIRECTORY + TEXT_VERTEX_SHADER,
        SHADERS_PATH + FRAGMENT_DIRECTORY + TEXT_FRAGMENT_SHADER);
    // Create main window
    window = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TILE);

    // Initialise WorldRenderer
    worldRenderer = new WorldRenderer(window, worldShader);
    guiRenderer = new GuiRenderer(window, guiShader);
    textRenderer = new TextRenderer(window, guiShader);
    backgroundRenderer = new GuiRenderer(window, backgroundShader);
    window.create();
    // Create World Shader
    worldShader.create();
    // Create Hud Shader
    guiShader.create();
    // Create Text Shader
    textShader.create();
    // Create Background Shader
    backgroundShader.create();
    // Create the Main Menu
    // init Audio
    initAudio();
    playMusic();
    MainMenu.create(window, camera);
  }

  /**
   * Update.
   */
  public void update() {
    camera.update(window);
    window.update();

    if (state == GameState.MAIN_MENU) {
      MainMenu.update(window, camera);
    } else { // state == GameState.GAME
      // Update The Dev Hud
      Hud.updateDevHud(camera);
      // Update The World
      World.update(window, camera);
      // Update the Hud
      Hud.update();
    }
  }

  /**
   * Render.
   */
  public void render() {
    if (state == GameState.MAIN_MENU) {
      MainMenu.render(guiRenderer, textRenderer, backgroundRenderer);
    } else { // state == GameState.GAME;
      // Render world objects
      World.render(worldRenderer, camera, window);
      // Render all game objects
      Hud.render(guiRenderer, textRenderer);
    }
    window.swapBuffers();
  }

  private void initAudio() {
    AudioMaster.init();
    AudioMaster.setListener(new Vector3f(0, 0, 0));
  }

  private void playMusic() {
    musicSource = new Source();
    musicSource.setRelative(true);
    musicSource.setLooping(true);
    musicSource.setGain(0.02f);
    int musicBuffer =
        AudioMaster.loadSound("src/main/resources/audio/music/Aphex_Twin_Stone_In_Focus.ogg");
    musicSource.playSound(musicBuffer);
  }
}
