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
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.menu.data.TradeDeal;
import game.world.Hud;
import game.world.World;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class ChoiceMenu {
  private static final String[] CHOICE_BUTTON_NAMES = new String[] {"Start War", "Claim Tile",
      "Start Trade", "Nothing"};
  private static final float BUTTON_WIDTH = 0.44f;
  private static final float BUTTON_HEIGHT = 0.1f;
  private static final float BUTTON_PADDING = 0.06f;
  private static final float BUTTON_OFFSET_Y = (BUTTON_HEIGHT / 2) + 0.5f;
  private static final float HINT_GAP = 0.05f;
  private static HudText choiceHint;
  private static ArrayList<ButtonObject> choiceButtons = new ArrayList<>();
  private static boolean choiceMade;

  public static boolean isChoiceMade() {
    return choiceMade;
  }

  public static void setChoiceMade(boolean choiceMade) {
    ChoiceMenu.choiceMade = choiceMade;
  }

  /**
   * Render.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.render(guiRenderer, textRenderer);
    }
    choiceHint.render(textRenderer);
  }

  public static void create() {
    createChoiceButtons();
    createChoiceHint();
  }

  /**
   * Update.
   *
   * @param window the window
   */
  public static void update(Window window) {
    updateButtonStatus();
    checkChoiceButtonClick(window);
    resize();
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.update(window);
    }
  }

  private static void updateButtonStatus() {
    Society playerSociety = World.getActiveSocieties().get(0);
    // Update War Button
    playerSociety.calculateAttackingTiles();
    if (playerSociety.getAttackingTiles().isEmpty()) {
      choiceButtons.get(0).disable();
    } else {
      choiceButtons.get(0).enable();
    }
    // Update Claim Tile Button
    playerSociety.calculateClaimableTerritory();
    if (playerSociety.getClaimableTerritory().isEmpty()) {
      choiceButtons.get(1).disable();
    } else {
      choiceButtons.get(1).enable();
    }

    // Update Trade Button
    playerSociety.calculatePossibleTradingSocieties();
    if (playerSociety.getPossibleTradingSocieties().isEmpty()) {
      choiceButtons.get(2).disable();
    } else {
      choiceButtons.get(2).enable();
    }
  }

  private static void createChoiceHint() {
    String choiceHintString = "Please Select A Move";
    Text choiceText = new Text(choiceHintString, 1f, ColourUtils.convertColor(Color.WHITE));
    choiceHint = new HudText(choiceText, 0, 0, 0, 0);
    choiceHint.setOffsetX(-(choiceHint.getWidth() / 2f));
    choiceHint.setOffsetY(BUTTON_OFFSET_Y + BUTTON_HEIGHT + HINT_GAP);
    choiceHint.create();
  }

  private static void checkChoiceButtonClick(Window window) {
    // Check if left button is down
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && Game.canClick()) {
      Game.resetButtonLock();
      // cycle thorough all buttons in out list of choice buttons
      for (ButtonObject choiceButton : choiceButtons) {
        // check if the mouse is over the button
        if (choiceButton.isMouseOver(window) && choiceButton.isEnabled()) {
          if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTON_NAMES[0])) {
            choiceMade = true;
            // War button was highlighted
            Game.setState(GameState.WARRING);
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[1])) {
            choiceMade = true;
            // Claim Tile button was highlighted
            Game.setState(GameState.CLAIM_TILE);
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[2])) {
            choiceMade = true;
            Hud.setTerrainPanelActive(false);
            Hud.setSocietyPanelActive(false);
            TradingMenu.setTradeDeal(new TradeDeal(0, 0, 0, 0));
            // Trade move button was highlighted
            Game.setState(GameState.TRADING);
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[3])) {
            choiceMade = true;
            // Nothing button was highlighted
            World.getActiveSocieties().get(0).setEndTurn(true);
            Game.setState(GameState.GAME_MAIN);
          }
        }
      }
    }
  }


  /**
   * Resize.
   */
  public static void resize() {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.reposition();
    }
    choiceHint.reposition();
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.destroy();
    }
    choiceButtons.clear();
    choiceHint.destroy();
  }


  /**
   * Create choice buttons.
   */
  public static void createChoiceButtons() {
    // set the model for the choice buttons
    RectangleModel choiceButtonModel = new RectangleModel(BUTTON_WIDTH, BUTTON_HEIGHT);
    // bind the image for the buttons
    Image buttonImage = new Image("/images/hudElementBackground.png");
    // cycle thorough all the button names available
    for (int i = 0; i < CHOICE_BUTTON_NAMES.length; i++) {
      // Create the text for the individual button
      Text choiceButtonText = new Text(CHOICE_BUTTON_NAMES[i], 0.6f,
          ColourUtils.convertColor(Color.BLACK), true, true, true);
      // calculate the offset for the individual button
      float xoffset = calculateChoiceButtonOffset(CHOICE_BUTTON_NAMES.length, i + 1);
      // create the mesh for teh individual button
      RectangleMesh choiceButtonMesh = new RectangleMesh(choiceButtonModel,
          new Material(buttonImage));
      // create the button object
      ButtonObject choiceButton = new ButtonObject(choiceButtonMesh, choiceButtonText,
          0, xoffset, 0f, BUTTON_OFFSET_Y);
      // create the button and bind it
      choiceButton.create();
      // add the created button to a list of choice buttons
      choiceButtons.add(choiceButton);
    }
  }

  private static float calculateChoiceButtonOffset(int amount, int number) {
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
      offset = -(amountCenter * ((BUTTON_WIDTH / 2) + (BUTTON_PADDING / 2)));
      // find how far to place left
      int difference = amountSide - number;
      offset = offset - (difference * (BUTTON_PADDING + BUTTON_WIDTH));
      // add final amount
      offset = offset - (BUTTON_PADDING / 2) - (BUTTON_WIDTH / 2);
    } else if (number == amountSide + amountCenter) {
      // place a button in the center
      offset = 0;
    } else {
      // place buttons to the right
      offset = amountCenter * ((BUTTON_WIDTH / 2) + (BUTTON_PADDING / 2));
      // find how far to place right
      int difference = number - (amountSide + amountCenter + 1);
      offset = offset + (difference * (BUTTON_PADDING + BUTTON_WIDTH));
      // add final amount
      offset = offset + (BUTTON_PADDING / 2) + (BUTTON_WIDTH / 2);
    }

    return offset;
  }
}
