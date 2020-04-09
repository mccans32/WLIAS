package game.menu;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.io.Input;
import engine.objects.gui.ButtonObject;
import engine.objects.gui.HudText;
import engine.objects.world.Camera;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.world.Hud;
import game.world.World;
import java.awt.Color;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class PauseMenu {
  private static final ButtonObject[] buttons = new ButtonObject[2];
  private static RectangleModel buttonModel = new RectangleModel(0.75f, 0.3f);
  private static Image buttonImage = new Image("/images/buttonTexture.png");
  private static Vector3f buttonTextColour = new Vector3f(1, 1, 1);
  private static Text pauseText = new Text("Paused", 2.3f, ColourUtils.convertColor(Color.RED));
  private static HudText pauseObject = new HudText(pauseText, 0, 0, 1, -0.2f);
  private static ButtonObject resumeButton;
  private static ButtonObject exitButton;
  private static GameState previousState;

  /**
   * Create the objects for the pause menu.
   */
  public static void create() {
    createButtons();
    pauseObject.setOffsetX(-(pauseObject.getWidth() / 2f));
    pauseObject.create();
  }

  private static void createButtons() {
    // Create Resume Button
    RectangleMesh resumeMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
    Text resumeText = new Text("Resume", 1.5f, buttonTextColour, true, true, false);
    resumeButton = new ButtonObject(resumeMesh, resumeText, 0, 0, 1, -0.75f);
    resumeButton.create();
    buttons[0] = resumeButton;
    // Create Exit Button
    RectangleMesh exitMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
    Text exitText = new Text("Main Menu", 1.2f, buttonTextColour, true, true, true);
    exitButton = new ButtonObject(exitMesh, exitText, 0, 0, -1, 0.75f);
    exitButton.create();
    buttons[1] = exitButton;
  }

  /**
   * Update the objects and their positions and check for button clicks.
   *
   * @param window the window
   * @param camera the camera
   */
  public static void update(Window window, Camera camera) {
    checkButtonClick(window, camera);
    resize();
    for (ButtonObject button : buttons) {
      button.update(window);
    }
  }

  private static void resize() {
    pauseObject.reposition();
    for (ButtonObject button : buttons) {
      button.reposition();
    }
  }

  /**
   * Render the objects.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    pauseObject.render(textRenderer);
    for (ButtonObject button : buttons) {
      button.render(guiRenderer, textRenderer);
    }
  }

  /**
   * Destroy the objects and remove them form memory.
   */
  public static void destroy() {
    for (ButtonObject button : buttons) {
      button.destroy();
    }
    pauseObject.destroy();
  }

  /**
   * Pause the game.
   *
   * @param window the window
   * @param camera the camera
   */
  public static void pauseGame(Window window, Camera camera, GameState state) {
    previousState = state;
    Game.setState(GameState.GAME_PAUSE);
    window.unlockMouse();
    camera.freeze();
  }

  public static void unpauseGame(Camera camera) {
    Game.setState(previousState);
    camera.unfreeze();
  }

  private static void checkButtonClick(Window window, Camera camera) {
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      if (resumeButton.isMouseOver(window)) {
        unpauseGame(camera);
      } else if (exitButton.isMouseOver(window)) {
        destroy();
        Hud.destroy();
        World.destroy();
        Game.setState(GameState.MAIN_MENU);
        MainMenu.create(window, camera);
      }
    }
  }
}
