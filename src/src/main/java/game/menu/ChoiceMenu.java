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
import engine.utils.ColourUtils;
import game.world.World;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;

public class ChoiceMenu {

  private static final String[] CHOICE_BUTTON_NAMES = new String[] {"Start War", "Claim Tile",
      "Start Trade", "Nothing"};
  private static ArrayList<ButtonObject> choiceButtons = new ArrayList<>();

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
  }

  public static void create() {
    createChoiceButtons();
  }

  /**
   * Update.
   *
   * @param window the window
   */
  public static void update(Window window) {
    checkChoiceButtonClick(window);
    resize();
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.update(window);
    }
  }

  private static void checkChoiceButtonClick(Window window) {
    // Check if left button is down
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
      // cycle thorough all buttons in out list of choice buttons
      for (ButtonObject choiceButton : choiceButtons) {
        // check if the mouse is over the button
        if (choiceButton.isMouseOver(window)) {
          if (choiceButton.getLines().get(0).getText().getString().equals(CHOICE_BUTTON_NAMES[0])) {
            // War button was highlighted
            World.warMove();
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[1])) {
            // Claim Tile button was highlighted
            World.claimTileMove();
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[2])) {
            // Trade move button was highlighted
            World.tradeMove();
          } else if (choiceButton.getLines().get(0).getText().getString()
              .equals(CHOICE_BUTTON_NAMES[3])) {
            // Nothing button was highlighted
            World.nothingMove();
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
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    for (ButtonObject choiceButton : choiceButtons) {
      choiceButton.destroy();
    }
    choiceButtons.clear();
  }


  /**
   * Create choice buttons.
   */
  public static void createChoiceButtons() {
    // set dimensions for the choice buttons
    float width = 0.44f;
    float height = 0.1f;
    float padding = 0.06f;
    float offsetY = (height / 2);
    // set the model for the choice buttons
    RectangleModel choiceButtonModel = new RectangleModel(width, height);
    // bind the image for the buttons
    Image buttonImage = new Image("/images/hudElementBackground.png");
    // cycle thorough all the button names available
    for (int i = 0; i < CHOICE_BUTTON_NAMES.length; i++) {
      // Create the text for the individual button
      Text choiceButtonText = new Text(CHOICE_BUTTON_NAMES[i], 0.6f,
          ColourUtils.convertColor(Color.BLACK), true, true, true);
      // calculate the offset for the individual button
      float xoffset = calculateChoiceButtonOffset(CHOICE_BUTTON_NAMES.length,
          width, padding, i + 1);
      // create the mesh for teh individual button
      RectangleMesh choiceButtonMesh = new RectangleMesh(choiceButtonModel,
          new Material(buttonImage));
      // create the button object
      ButtonObject choiceButton = new ButtonObject(choiceButtonMesh, choiceButtonText,
          0, xoffset, 0f, offsetY);
      // create the button and bind it
      choiceButton.create();
      // add the created button to a list of choice buttons
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
