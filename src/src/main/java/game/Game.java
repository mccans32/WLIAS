package game;

import static java.lang.Math.max;

import engine.Window;
import engine.audio.AudioMaster;
import engine.audio.Source;
import engine.graphics.Shader;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.io.Input;
import engine.objects.world.Camera;
import game.menu.ChoiceMenu;
import game.menu.GameOverMenu;
import game.menu.MainMenu;
import game.menu.PauseMenu;
import game.world.Hud;
import game.world.World;
import java.util.ArrayList;
import java.util.Collections;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;
import society.Society;

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
  static final String BACKGROUND_SHADER = "backgroundVertex.glsl";
  private static final int BUTTON_LOCK_CYCLES = 20;
  private static GameState state = GameState.MAIN_MENU;
  private static WorldRenderer worldRenderer;
  private static GuiRenderer guiRenderer;
  private static TextRenderer textRenderer;
  private static GuiRenderer backgroundRenderer;
  private static Source musicSource;
  private static int buttonLock = BUTTON_LOCK_CYCLES;
  public Camera camera = new Camera(new Vector3f(0, 0, 10f), new Vector3f(30, 0, 0));
  private Window window;
  private Shader worldShader;
  private Shader guiShader;
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
    backgroundShader.destroy();
    musicSource.destroy();
    AudioMaster.cleanUp();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loop\n");
    while (!window.shouldClose()) {
      // Main game loop where each turn is being decided
      if (World.getActiveSocieties().size() > 0) {
        // generates a random turn order of all the societies in play
        ArrayList<Society> turnOrder = new ArrayList<>(World.getActiveSocieties());
        Collections.shuffle(turnOrder);
        // cycles thorough all societies in play
        for (Society society : turnOrder) {
          // set the end turn flag to false
          society.setEndTurn(false);
          // update and render the screen until the society finishes its move
          // or the simulation closes
          while (!society.isEndTurn() && !window.shouldClose()) {
            if (society.getSocietyId() != 0) {
              World.aiTurn(society);
            }
            update();
            render();
          }
        }
      } else {
        // updating and rendering of the main menu.
        update();
        render();
      }
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

    executeEscapeKeyFunctionality();

    if (state == GameState.MAIN_MENU) {
      MainMenu.update(window, camera);
    } else {
      checkGameOver();
      if (state == GameState.GAME_PAUSE) {
        PauseMenu.update(window, camera);
      } else if (state == GameState.GAME_CHOICE) {
        // Update The Dev Hud
        Hud.updateDevHud(camera);
        // Update the Hud
        Hud.update(window);
        // Update Choice Menu
        ChoiceMenu.update(window);
        // Update The World
        World.update(window, camera);
      } else {
        // Update The Dev Hud
        Hud.updateDevHud(camera);
        // Update the Hud
        Hud.update(window);
        // Update The World
        World.update(window, camera);
        if (state == GameState.GAME_OVER) {
          GameOverMenu.update(window, camera);
        }
      }
    }
  }

  private void checkGameOver() {
    if (!World.getActiveSocieties().contains(World.getSocieties()[0])) {
      // The Player's Society is not present in the active societies
      state = GameState.GAME_OVER;
    }
  }

  private void executeEscapeKeyFunctionality() {
    // check for game pause
    buttonLock--;
    buttonLock = max(0, buttonLock);
    if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE) && (buttonLock == 0)) {
      buttonLock = BUTTON_LOCK_CYCLES;
      // close inspection panel if currently open
      if (Hud.isSocietyPanelActive() || Hud.isTerrainPanelActive()) {
        Hud.setSocietyPanelActive(false);
        Hud.setTerrainPanelActive(false);
      } else if (Game.getState() == GameState.GAME_PAUSE) {
        PauseMenu.unpauseGame(camera);
      } else {
        PauseMenu.pauseGame(window, camera, state);
      }
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
      // Render all hud elements
      Hud.render(guiRenderer, textRenderer);
      if (state == GameState.GAME_PAUSE) {
        // Render the PauseMenu
        PauseMenu.render(guiRenderer, textRenderer);
      } else if (state == GameState.GAME_CHOICE) {
        ChoiceMenu.render(guiRenderer, textRenderer);
      } else if (state == GameState.GAME_OVER) {
        GameOverMenu.render(guiRenderer, textRenderer);
      }
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
    musicSource.setGain(0.01f);
    int musicBuffer =
        AudioMaster.loadSound("src/main/resources/audio/music/Aphex_Twin_Stone_In_Focus.ogg");
    musicSource.playSound(musicBuffer);
  }
}
