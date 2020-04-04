package game.world;

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
import engine.objects.gui.HudObject;
import engine.objects.gui.HudText;
import engine.objects.world.Camera;
import engine.utils.ColourUtils;
import java.awt.Color;
import java.util.ArrayList;
import math.Vector3f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class Hud {
  // Sets a number of game cycles so when we press a button to toggle it is not as sensitive
  private static final int BUTTON_LOCK_CYCLES = 20;
  private static final Vector3f ARROW_ENABLE_COLOUR = ColourUtils.convertColor(Color.GREEN);
  private static final Vector3f ARROW_ENABLE_HOVER
      = ColourUtils.convertColor(ChartColor.VERY_DARK_GREEN);
  private static final Vector3f ARROW_DISABLE_COLOUR = ColourUtils.convertColor(Color.RED);
  private static final float ARROW_BUTTON_OFFSET_Y = 0.1f;
  private static HudObject turnCounter;
  private static Text coordText = new Text("", 0.9f, ColourUtils.convertColor(Color.BLACK));
  private static Text turnText = new Text("", 0.7f, new Vector3f(0, 0, 0));
  private static Text arrowText = new Text("Next Turn", 0.5f);
  private static HudText coordinates;
  private static Boolean devHudActive = false;
  private static int hudCycleLock = 0;
  private static int turn = 1;
  private static ArrayList<ButtonObject> societyButtons = new ArrayList<>();
  private static ButtonObject arrowButton;
  private static HudText arrowTextObject;
  private static boolean canNextTurn = true;
  private static float arrowCounter;

  public static void create() {
    createObjects();
  }

  /**
   * Update the hud elements.
   *
   * @param window the window
   */
  public static void update(Window window) {
    hudCycleLock--;
    hudCycleLock = Math.max(hudCycleLock, 0);
    resize();
    updateSocietyButtons(window);
    updateArrowButton(window);
  }

  private static void updateArrowButton(Window window) {
    // Modify Arrow to make next turn clear
    if (canNextTurn) {
      // can click for next turn update colour and add animation
      arrowButton.setInactiveColourOffset(ARROW_ENABLE_COLOUR);
      arrowButton.setActiveColourOffset(ARROW_ENABLE_HOVER);
      arrowButton.getHudImage().getMesh().getMaterial().setColorOffset(ARROW_ENABLE_COLOUR);
      arrowCounter += 0.05f;
      float newValue = (float) Math.sin(arrowCounter) * 0.0012f;
      float offsetY = arrowButton.getHudImage().getOffsetY();
      arrowButton.getHudImage().setOffsetY(offsetY + newValue);
      // Check if clicked
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && hudCycleLock == 0
          && arrowButton.isMouseOver(window)) {
        hudCycleLock = BUTTON_LOCK_CYCLES;
        updateTurnCounter();
        // TODO Fix this bug
        // If we don't reset here then for some reason the arrow text is overwritten
        arrowTextObject.setText(arrowText);
        arrowButton.getHudImage().setOffsetY(ARROW_BUTTON_OFFSET_Y);
        canNextTurn = false;
        arrowCounter = 0;
      }
    } else {
      // Reset Y-Offset and Reset Colours
      arrowButton.setInactiveColourOffset(ARROW_DISABLE_COLOUR);
      arrowButton.setActiveColourOffset(ARROW_DISABLE_COLOUR);
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
          && hudCycleLock == 0 && arrowButton.isMouseOver(window)) {
        hudCycleLock = BUTTON_LOCK_CYCLES;
        canNextTurn = true;
      }
    }
    arrowButton.update(window);
  }

  private static void updateSocietyButtons(Window window) {
    for (int i = 0; i < societyButtons.size(); i++) {
      societyButtons.get(i).update(window);
      // check if mouse click
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
          && societyButtons.get(i).isMouseOver(window) && hudCycleLock == 0) {
        hudCycleLock = BUTTON_LOCK_CYCLES;
        Society society = World.getSocieties()[i];
        System.out.println(String.format("Society: %d", i + 1));
      }
    }
  }

  private static void updateTurnCounter() {
    turn++;
    turnText.setString(String.format("Turn: %d", turn));
    turnCounter.getLines().get(0).setText(turnText);
  }

  /**
   * Update dev hud, displays the camera coordinates.
   *
   * @param camera the camera
   */
  public static void updateDevHud(Camera camera) {
    // check for key press to toggle
    if (Input.isKeyDown(GLFW.GLFW_KEY_F3) && (hudCycleLock == 0)) {
      devHudActive = !devHudActive;
      hudCycleLock = BUTTON_LOCK_CYCLES;
    }
    if (devHudActive) {
      calculateCoordText(camera);
      coordinates.setText(coordText);
    }
  }

  private static void createObjects() {
    // Create the turn hud Element
    turnText.setString(String.format("Turn: %d", turn));
    turnText.setCentreHorizontal(true);
    turnText.setCentreVertical(true);
    RectangleMesh mesh = new RectangleMesh(new RectangleModel(0.45f, 0.1f),
        new Material(new Image("/images/hudElementBackground.png")));
    turnCounter = new HudObject(mesh, turnText, -1f, 0.2f, 1f, -0.05f);
    turnCounter.create();
    // Create the coordinate hud element
    coordinates = new HudText(coordText, -1, 0, 1, -1.95f);
    coordinates.create();
    // Create society buttons
    createSocietyButtons();
    // Create Next Turn Button
    createTurnButton();
  }

  private static void createTurnButton() {
    float width = 0.15f;
    float height = 0.15f;
    Image buttonImage = new Image("/images/arrow2.png");
    RectangleModel arrowModel = new RectangleModel(width, height);
    RectangleMesh arrowMesh = new RectangleMesh(arrowModel, new Material(buttonImage,
        ColourUtils.convertColor(Color.RED)));
    arrowButton = new ButtonObject(arrowMesh, new Text(""), 1, -0.12f, -1f,
        ARROW_BUTTON_OFFSET_Y);
    arrowButton.create();
    arrowTextObject = new HudText(arrowText, 1, -0.3f, -1, 0.05f);
    arrowTextObject.create();
  }

  private static void createSocietyButtons() {
    float fontSize = 0.6f;
    float width = 0.4f;
    float height = 0.1f;
    float padding = 0.06f;
    Image buttonImage = new Image("/images/hudElementBackground.png");
    RectangleModel buttonModel = new RectangleModel(width, height);
    for (int i = 0; i < World.getSocieties().length; i++) {
      Society society = World.getSocieties()[i];
      String societyString = String.format("Society: %d", i + 1);
      Text societyText = new Text(societyString, fontSize,
          Vector3f.subtract(society.getSocietyColor(), 0.2f));
      societyText.setCentreHorizontal(true);
      societyText.setCentreVertical(true);
      float xoffset = calculateXOffset(World.getSocieties().length, width, padding, i + 1);
      RectangleMesh buttonMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
      ButtonObject societyButton = new ButtonObject(buttonMesh, societyText, 0, xoffset,
          -1, (height / 2) + 0.02f);
      societyButton.create();
      societyButtons.add(societyButton);
    }
  }

  private static float calculateXOffset(int amount, float width, float padding, int number) {
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

  /**
   * Render Hud Elements and Text Elements in the world setting.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    //Render Hud Elements First to fix alpha blending on text
    renderImages(guiRenderer);
    renderTexts(textRenderer);
    renderObjects(guiRenderer, textRenderer);
    if (devHudActive) {
      coordinates.render(textRenderer);
    }
  }

  private static void renderImages(GuiRenderer renderer) {
  }

  private static void renderTexts(TextRenderer renderer) {
    arrowTextObject.render(renderer);
  }

  private static void renderObjects(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    turnCounter.render(guiRenderer, textRenderer);
    for (ButtonObject button : societyButtons) {
      button.render(guiRenderer, textRenderer);
    }
    arrowButton.render(guiRenderer, textRenderer);
  }

  /**
   * Resize elements to compensate for screen resize.
   */
  public static void resize() {
    turnCounter.reposition();
    coordinates.reposition();
    for (ButtonObject button : societyButtons) {
      button.reposition();
    }
    arrowButton.reposition();
    arrowTextObject.reposition();
  }

  private static void calculateCoordText(Camera camera) {
    Vector3f pos = camera.getPosition();
    String coordString = String.format("x: %.2f, y: %.2f, z: %.2f", pos.getX(), pos.getY(),
        pos.getZ());
    coordText.setString(coordString);
  }

  /**
   * Destroy All Hud Elements.
   */
  public static void destroy() {
    turnCounter.destroy();
    coordinates.destroy();
    for (ButtonObject button : societyButtons) {
      button.destroy();
    }
    societyButtons.clear();
    arrowButton.destroy();
    arrowTextObject.destroy();
  }
}