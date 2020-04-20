package game;

import engine.Window;
import engine.audio.AudioMaster;
import engine.audio.Source;
import engine.graphics.Shader;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.renderer.WorldRenderer;
import engine.io.Input;
import engine.objects.world.Camera;
import engine.tools.Timer;
import game.menu.ChoiceMenu;
import game.menu.GameOverMenu;
import game.menu.MainMenu;
import game.menu.PauseMenu;
import game.menu.TradingMenu;
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
  private static final int REPRODUCE_FREQUENCY = 2;
  private static final int AGE_FREQUENCY = 2;
  private static Timer notificationTimer = new Timer();
  private static GameState state = GameState.MAIN_MENU;
  private static WorldRenderer worldRenderer;
  private static GuiRenderer guiRenderer;
  private static TextRenderer textRenderer;
  private static GuiRenderer backgroundRenderer;
  private static Source musicSource;
  private static int buttonLock = BUTTON_LOCK_CYCLES;
  private static boolean restarted;
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

  public static int getButtonLock() {
    return buttonLock;
  }

  public static boolean canClick() {
    return buttonLock == 0;
  }

  public static void resetButtonLock() {
    buttonLock = BUTTON_LOCK_CYCLES;
  }

  private static void updateButtonLock() {
    buttonLock--;
    buttonLock = Math.max(0, buttonLock);
  }

  public static Timer getNotificationTimer() {
    return notificationTimer;
  }

  public static boolean isRestarted() {
    return restarted;
  }

  public static void setRestarted(boolean restarted) {
    Game.restarted = restarted;
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
    notificationTimer.clearDuration();
  }

  private void gameLoop() {
    System.out.println("This is the Game Loop\n");
    while (!window.shouldClose()) {
      restarted = false;
      // Main game loop where each turn is being decided
      if (World.getActiveSocieties().size() > 0
          && state != GameState.TURN_END
          && state != GameState.GAME_PAUSE) {
        ChoiceMenu.setChoiceMade(false);
        // Age everyone in each society
        if (Hud.getTurn() % AGE_FREQUENCY == 0) {
          for (Society society : World.getActiveSocieties()) {
            society.agePopulation();
          }
        }
        // generates a random turn order of all the societies in play
        ArrayList<Society> turnOrder = new ArrayList<>(World.getActiveSocieties());
        Collections.shuffle(turnOrder);
        // cycles thorough all societies in play
        for (Society society : turnOrder) {
          // closing of the inspection panel so that information is up to date
          if (society.getSocietyId() == 0) {
            Hud.setSocietyPanelActive(false);
            Hud.setTerrainPanelActive(false);
          }
          World.setActiveSociety(society);
          // check and end Trade deals
          society.checkTradeDeal();
          // set the end turn flag to false
          society.setEndTurn(false);
          society.setMadeMove(false);
          // update and render the screen until the society finishes its move
          // or the simulation closes
          while (!society.isEndTurn()
              && !window.shouldClose()
              && !World.getActiveSocieties().isEmpty()) {
            // Break this loop if the game is restarted form the game over screen
            if (restarted) {
              break;
            }
            if (society.getSocietyId() != 0) {
              World.aiTurn(society);
              // If the user has not made their choice update the menu
            } else if (!ChoiceMenu.isChoiceMade() && state != GameState.GAME_PAUSE) {
              state = GameState.GAME_CHOICE;
            }
            update();
            render();
          }
          // Society reproduces and the notification loop is set
          // Need to check this, if we don't the state is reset to reproducing and the main menu
          // is not rendered.
          if (state != GameState.MAIN_MENU
              && state != GameState.GAME_OVER
              && state != GameState.GAME_PAUSE
              && Hud.getTurn() % REPRODUCE_FREQUENCY == 0) {
            reproduceLoop(society);
            World.setActiveSociety(null);
          }
        }
        // End the turn if the state is appropriate
        // If these states aren't accounted for there are game play bugs
        if (state != GameState.MAIN_MENU
            && state != GameState.GAME_OVER
            && state != GameState.GAME_PAUSE
            && !restarted) {
          state = GameState.TURN_END;
        }
      }
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
    updateButtonLock();
    if (Game.getState() != GameState.GAME_PAUSE) {
      notificationTimer.update();
    }
    camera.update(window);
    window.update();

    executeEscapeKeyFunctionality();

    if (state == GameState.MAIN_MENU) {
      MainMenu.update(window, camera);
    } else {
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
      } else if (state == GameState.TRADING) {
        TradingMenu.update(window);
      } else {
        checkGameOver();
        if (state != GameState.GAME_OVER) {
          // Update The Dev Hud
          Hud.updateDevHud(camera);
          // Update the Hud
          Hud.update(window);
          // Update The World
          World.update(window, camera);
        } else {
          GameOverMenu.update(window, camera);
        }
      }
    }
  }

  private void checkGameOver() {
    if (!World.getActiveSocieties().contains(World.getSocieties()[0])
        && !World.getActiveSocieties().isEmpty()) {
      // The Player's Society is not present in the active societies
      state = GameState.GAME_OVER;
    }
  }

  private void executeEscapeKeyFunctionality() {
    if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE) && canClick()) {
      resetButtonLock();
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
      } else if (state == GameState.TRADING) {
        TradingMenu.render(guiRenderer, textRenderer);
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

  private void reproduceLoop(Society society) {
    society.reproduce();
    Game.setState(GameState.REPRODUCING);
    Game.getNotificationTimer().setDuration(1);
    while (!notificationTimer.isDurationMet()
        && !window.shouldClose()
        && !World.getActiveSocieties().isEmpty()) {
      update();
      render();
    }
    // Only change to main if the game has not been restarted during the loop
    // If we don't then the state is set back to main and the main menu doesnt render.
    if (state != GameState.MAIN_MENU) {
      Game.setState(GameState.GAME_MAIN);
    }
  }
}
