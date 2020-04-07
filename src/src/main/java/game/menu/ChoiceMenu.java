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
import engine.objects.world.Camera;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.world.World;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;

public class ChoiceMenu {

  private static final String[] CHOICE_BUTTONS = new String[] {"Start War", "Claim Tile", "Start Trade", "Nothing"};
  private static ArrayList<ButtonObject> choiceButtons = new ArrayList<>();

  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer){
    for (ButtonObject choiceButton : choiceButtons){
      choiceButton.render(guiRenderer, textRenderer);
    }
  }

  public static void create() {
    createChoiceButtons();
  }

  public static void update(Window window, Camera camera) {
    checkChoiceButtonClick(window, camera);
    resize();
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.update(window);
    }
  }

  private static void checkChoiceButtonClick(Window window, Camera camera) {
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      for (ButtonObject choiceButton : choiceButtons) {
        if (choiceButton.isMouseOver(window)) {
          if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTONS[0])) {
            destroy();
            Game.setState(GameState.GAME_MAIN);
            World.warMove();
          } else if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTONS[1])) {
            destroy();
            Game.setState(GameState.GAME_MAIN);
            World.claimTileMove();
          } else if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTONS[2])) {
            destroy();
            Game.setState(GameState.GAME_MAIN);
            World.tradeMove();
          } else if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTONS[3])) {
            destroy();
            Game.setState(GameState.GAME_MAIN);
            World.nothingMove();
          }
        }
      }
    }
  }

  public static void resize() {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.reposition();
    }
  }

  public static void destroy() {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.destroy();
    }
  }


  public static void createChoiceButtons() {
    float width = 0.44f;
    float height = 0.1f;
    float padding = 0.06f;
    float offsetY = (height / 2);
    RectangleModel choiceButtonModel = new RectangleModel(width, height);
    Image buttonImage = new Image("/images/hudElementBackground.png");
    for (int i = 0; i < CHOICE_BUTTONS.length; i++) {
      Text choiceButtonText = new Text(CHOICE_BUTTONS[i], 0.6f, ColourUtils.convertColor(Color.BLACK), true, true, true);
      float xoffset = calculateChoiceButtonOffset(CHOICE_BUTTONS.length, width, padding, i + 1);
      RectangleMesh choiceButtonMesh = new RectangleMesh(choiceButtonModel, new Material(buttonImage));
      ButtonObject choiceButton = new ButtonObject(choiceButtonMesh, choiceButtonText, 0, xoffset, 0f, offsetY);
      choiceButton.create();
      choiceButtons.add(choiceButton);
    }
  }

  private static float calculateChoiceButtonOffset(int amount, float width, float padding,
                                                   int number) {
    // determine if odd or even
    int amountSide = amount / 2;
    int amountCenter;
    if (amount % 2 == 0) {
      amountCenter = 0;
    } else {
      amountCenter = 1;
    }
    float offset;
    // Should go on left side
    if (number <= amountSide) {
      // compensate for center if amount is odd
      offset = -(amountCenter * ((width / 2) + (padding / 2)));
      // find how far to place left
      int difference = amountSide - number;
      offset = offset - (difference * (padding + width));
      // add final amount
      offset = offset - (padding / 2) - (width / 2);
    } else if (number == amountSide + amountCenter) {
      // place a button in the center
      offset = 0;
    } else {
      // place buttons to the right
      offset = amountCenter * ((width / 2) + (padding / 2));
      // find how far to place right
      int difference = number - (amountSide + amountCenter + 1);
      offset = offset + (difference * (padding + width));
      // add final amount
      offset = offset + (padding / 2) + (width / 2);
    }

    return offset;
  }
}
