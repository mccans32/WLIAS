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

public class GameOverMenu {
  private static final ButtonObject[] buttons = new ButtonObject[2];
  private static ButtonObject restartButton;
  private static ButtonObject mainMenuButton;
  private static RectangleModel buttonModel = new RectangleModel(0.75f, 0.3f);
  private static Image buttonImage = new Image("/images/buttonTexture.png");
  private static Vector3f buttonTextColour = new Vector3f(1, 1, 1);
  private static HudText gameOverText;

  /**
   * Create.
   */
  public static void create() {
    // Create RestartButton
    RectangleMesh resumeMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
    Text resumeText = new Text("Restart", 1.5f, buttonTextColour, true, true, false);
    restartButton = new ButtonObject(resumeMesh, resumeText, 0, 0, 1, -0.75f);
    restartButton.create();
    buttons[0] = restartButton;
    // Create Exit Button
    RectangleMesh exitMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
    Text exitText = new Text("Main Menu", 1.2f, buttonTextColour, true, true, true);
    mainMenuButton = new ButtonObject(exitMesh, exitText, 0, 0, -1, 0.75f);
    mainMenuButton.create();
    buttons[1] = mainMenuButton;
    // Create Game Over Text
    Text text = new Text("Game Over!", 2.3f, ColourUtils.convertColor(Color.RED.brighter()));
    gameOverText = new HudText(text, 0, 0, 1, -0.2f);
    gameOverText.setOffsetX(-(gameOverText.getWidth() / 2));
    gameOverText.create();
  }

  /**
   * Update the Elements.
   *
   * @param window the window
   * @param camera the camera
   */
  public static void update(Window window, Camera camera) {
    resize();
    for (ButtonObject button : buttons) {
      button.update(window);
    }
    checkButtonClick(window, camera);
  }

  private static void checkButtonClick(Window window, Camera camera) {
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && Game.canClick()) {
      Game.resetButtonLock();
      if (restartButton.isMouseOver(window)) {
        restartGame(window, camera);
      } else if (mainMenuButton.isMouseOver(window)) {
        destroy();
        Hud.destroy();
        World.destroy();
        Game.setState(GameState.MAIN_MENU);
        MainMenu.create(window, camera);
      }
    }
  }

  private static void restartGame(Window window, Camera camera) {
    Game.setRestarted(true);
    Game.setState(GameState.GAME_MAIN);
    Hud.destroy();
    World.destroy();
    TradingMenu.destroy();
    DealingMenu.destroy();
    World.create(window, camera);
    TradingMenu.create();
    DealingMenu.create();
    ChoiceMenu.create();
    World.update(window, camera);
    Hud.create();
  }

  private static void resize() {
    for (ButtonObject button : buttons) {
      button.reposition();
    }
    gameOverText.reposition();
  }

  /**
   * Render the game over Menu.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    for (ButtonObject button : buttons) {
      button.render(guiRenderer, textRenderer);
    }
    gameOverText.render(textRenderer);
  }


  /**
   * Destroy the elements.
   */
  public static void destroy() {
    for (ButtonObject button : buttons) {
      button.destroy();
    }
    gameOverText.destroy();
  }
}
