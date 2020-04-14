package game.world;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;

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
import engine.objects.gui.HudImage;
import engine.objects.gui.HudObject;
import engine.objects.gui.HudText;
import engine.objects.gui.SocietyButton;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import engine.tools.MousePicker;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import java.awt.Color;
import java.util.ArrayList;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.WaterTile;
import math.Vector3f;
import math.Vector4f;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class Hud {
  // Sets a number of game cycles so when we press a button to toggle it is not as sensitive
  private static final Vector4f ARROW_ENABLE_COLOUR =
      new Vector4f(ColourUtils.convertColor(Color.GREEN), 1);
  private static final Vector4f ARROW_ENABLE_HOVER
      = new Vector4f(ColourUtils.convertColor(ChartColor.VERY_DARK_GREEN), 1);
  private static final Vector4f ARROW_DISABLE_COLOUR
      = new Vector4f(ColourUtils.convertColor(Color.RED), 0.5f);
  private static final float ARROW_BUTTON_OFFSET_Y = 0.1f;
  private static final Image PANEL_IMAGE = new Image("/images/hudPanel.png");
  private static final Image SOCIETY_PANEL_IMAGE = new Image("/images/hudPanel2.png");
  private static final Vector3f PANEL_COLOUR = ColourUtils.convertColor(Color.GRAY.brighter());
  private static final float SOCIETY_BUTTON_WIDTH = 0.4f;
  private static final float SOCIETY_BUTTON_HEIGHT = 0.1f;
  private static final float SOCIETY_BUTTON_PADDING = 0.06f;
  private static final float SOCIETY_BUTTON_OFFSET_Y = (SOCIETY_BUTTON_HEIGHT / 2) + 0.02f;
  private static HudObject turnCounter;
  private static HudObject scoreCounter;
  private static HudObject societyInspectionPanel;
  private static HudObject terrainInspectionPanel;
  private static Text coordText = new Text("", 0.9f, ColourUtils.convertColor(Color.BLACK));
  private static Text turnText = new Text("", 0.7f);
  private static Text scoreText = new Text("", 0.7f);
  private static Text arrowText = new Text("Next Turn", 0.5f);
  private static Text societyPanelText = new Text("", 0.5f);
  private static Text terrainPanelText = new Text("", 0.5f);
  private static Text panelTitleText = new Text("Inspection Panel", 0.8f);
  private static HudImage terrainTileImage;
  private static HudText coordinates;
  private static Boolean devHudActive = false;
  private static int turn = 1;
  private static ArrayList<SocietyButton> societyButtons = new ArrayList<>();
  private static HudImage societyButtonPanel;
  private static HudImage arrowButtonPanel;
  private static HudText panelTitle;
  private static ButtonObject arrowButton;
  private static HudText arrowTextObject;
  private static float arrowCounter;
  private static boolean terrainPanelActive = false;
  private static boolean societyPanelActive = false;
  private static ButtonObject closeButton;
  private static ArrayList<HudImage> panelBorders = new ArrayList<>();
  private static boolean mouseOverHud = false;
  private static HudText hint;

  public static int getTurn() {
    return turn;
  }

  public static ArrayList<SocietyButton> getSocietyButtons() {
    return societyButtons;
  }

  public static boolean isTerrainPanelActive() {
    return terrainPanelActive;
  }

  public static void setTerrainPanelActive(boolean terrainPanelActive) {
    Hud.terrainPanelActive = terrainPanelActive;
  }

  public static boolean isSocietyPanelActive() {
    return societyPanelActive;
  }

  public static void setSocietyPanelActive(boolean societyPanelActive) {
    Hud.societyPanelActive = societyPanelActive;
  }

  /**
   * Create the Hud Elements and reset the variables for if a player restarts a new world.
   */
  public static void create() {
    // reset variables
    turn = 1;
    terrainPanelActive = false;
    societyPanelActive = false;
    devHudActive = false;
    createObjects();
  }

  /**
   * Update the hud elements.
   *
   * @param window the window
   */
  public static void update(Window window) {
    mouseOverHud = false;
    resize();
    updateTerrainPanel();
    updateSocietyButtons(window);
    updateArrowButton(window);
    updatePanelCloseButton(window);
    updateHint();
  }

  /**
   * Update the hint text.
   */
  public static void updateHint() {
    String hintString = null;
    if (Game.getState() == GameState.WARRING) {
      if (World.getAttackingTile() == null) {
        hintString = "Select an Attacking Tile";
      } else if (World.getOpponentTile() == null) {
        hintString = "Select an Opponent's Tile to Attack";
      }
    } else if (Game.getState() == GameState.CLAIM_TILE) {
      hintString = "Select a Tile to Claim";
    } else if (Game.getState() == GameState.AI_CLAIM) {
      hintString = String.format("Society %d expands their territory",
          World.getActiveSociety().getSocietyId() + 1);
    }
    if (!hint.getText().getString().equals(hintString) && hintString != null) {
      Text hintText = new Text(hintString, 1, ColourUtils.convertColor(Color.WHITE));
      hint.setText(hintText);
      hint.setOffsetX(-(hint.getWidth() / 2));
    }
  }

  private static void updatePanelCloseButton(Window window) {
    closeButton.update(window);
    // check for button click
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && closeButton.isMouseOver(window)) {
      // close the panel
      mouseOverHud = true;
      terrainPanelActive = false;
      societyPanelActive = false;
    }
  }

  private static void updateTerrainPanel() {
    if (MousePicker.getCurrentSelected() != null
        && Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT) && !mouseOverHud) {
      terrainPanelActive = true;
      societyPanelActive = false;
      // update the text
      String panelString = calculateTerrainPanelString(MousePicker.getCurrentSelected());
      terrainPanelText.setString(panelString);
      terrainPanelText.setShouldWrap(true);
      terrainInspectionPanel.updateText(terrainPanelText);
      // update Terrain Panel Image
      Image terrainPanelImage = MousePicker.getCurrentSelected().getTile().getImage();
      terrainTileImage.getMesh().getMaterial().setImage(terrainPanelImage);
    }
  }

  private static void updateArrowButton(Window window) {
    // Modify Arrow to make next turn clear
    if (Game.getState() == GameState.TURN_END) {
      // can click for next turn update colour and add animation
      arrowButton.setInactiveColourOffset(ARROW_ENABLE_COLOUR);
      arrowButton.setActiveColourOffset(ARROW_ENABLE_HOVER);
      arrowButton.getHudImage().getMesh().getMaterial().setColorOffset(ARROW_ENABLE_COLOUR);
      arrowCounter += 0.05f;
      float newValue = (float) Math.sin(arrowCounter) * 0.0012f;
      float offsetY = arrowButton.getHudImage().getOffsetY();
      arrowButton.getHudImage().setOffsetY(offsetY + newValue);
      // Check if clicked
      if (((Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT) && arrowButton.isMouseOver(window))
          || Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
          && Game.canClick()) {
        mouseOverHud = true;
        Game.resetButtonLock();
        updateTurnCounter();
        arrowButton.getHudImage().setOffsetY(ARROW_BUTTON_OFFSET_Y);
        arrowCounter = 0;
        // change the state so the the next turn can begin the in game loop
        Game.setState(GameState.GAME_MAIN);
      }
    } else {
      // Reset Y-Offset and Reset Colours
      arrowButton.setInactiveColourOffset(ARROW_DISABLE_COLOUR);
      arrowButton.setActiveColourOffset(ARROW_DISABLE_COLOUR);
    }
    arrowButton.update(window);
  }

  private static void updateSocietyButtons(Window window) {
    purgeButtons();
    for (int i = 0; i < societyButtons.size(); i++) {
      societyButtons.get(i).update(window);
      // check if mouse click
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
          && societyButtons.get(i).isMouseOver(window) && Game.canClick()) {
        Game.resetButtonLock();
        mouseOverHud = true;
        terrainPanelActive = false;
        societyPanelActive = true;
        // set the text for the panel
        Society society = World.getActiveSocieties().get(i);
        String panelString = calculateSocietyPanelString(society);
        societyPanelText.setString(panelString);
        societyPanelText.setShouldWrap(true);
        societyInspectionPanel.updateText(societyPanelText);
      }
    }
  }

  private static void purgeButtons() {
    // remove buttons of societies no longer in the game
    if (World.getActiveSocieties().size() < societyButtons.size()) {
      ArrayList<ButtonObject> buttonsToRemove = new ArrayList<>();
      for (ButtonObject button : societyButtons) {
        String buttonString = button.getLines().get(0).getText().getString();
        if (!buttonString.equals("Your Society")) {
          // It is an AI society
          int buttonNumber = Integer.parseInt(buttonString.split(" ")[1]);
          boolean found = false;
          for (Society society : World.getActiveSocieties()) {
            if (society.getSocietyId() + 1 == buttonNumber) {
              found = true;
              break;
            }
          }
          if (!found) {
            buttonsToRemove.add(button);
          }
        } else {
          // Check if player society is active
          if (!World.getActiveSocieties().contains(World.getSocieties()[0])) {
            buttonsToRemove.add(societyButtons.get(0));
          }
        }
      }
      // Remove the buttons
      for (ButtonObject button : buttonsToRemove) {
        societyButtons.remove(button);
      }
      // Reposition the buttons
      for (int i = 0; i < societyButtons.size(); i++) {
        float offsetX = calculateSocietyButtonXOffset(societyButtons.size(), i + 1);
        societyButtons.get(i).getHudImage().setOffsetX(offsetX);
      }
      // Resize the panelBackground
      createSocietyButtonPanel();
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
    if (Input.isKeyDown(GLFW.GLFW_KEY_F3) && Game.canClick()) {
      devHudActive = !devHudActive;
      Game.resetButtonLock();
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
    Image hudImage = new Image("/images/hudElementBackground.png");
    RectangleModel model = new RectangleModel(0.45f, 0.1f);
    // create the turn Counter
    RectangleMesh turnMesh = new RectangleMesh(model, new Material(hudImage));
    turnCounter = new HudObject(turnMesh, turnText, -1f, 0.2f, 1f, -0.05f);
    turnCounter.create();
    // Create the score counter for the player
    scoreText.setString(String.format("Score: %d", World.getSocieties()[0].getScore()));
    scoreText.setCentreHorizontal(true);
    scoreText.setCentreVertical(true);
    RectangleMesh scoreMesh = new RectangleMesh(model, new Material(hudImage));
    scoreCounter = new HudObject(scoreMesh, scoreText, -1f, 0.2f, 1f, -0.15f);
    scoreCounter.create();
    // Create the coordinate hud element
    coordinates = new HudText(coordText, -1, 0, 1, -1.95f);
    coordinates.create();
    // Create society buttons
    createSocietyButtons();
    // Create Next Turn Button
    createTurnButton();
    // create Society Inspection Panel
    createInspectionPanels();
    // CreateHint
    createHint();
  }

  private static void createHint() {
    Text hintText = new Text("");
    hint = new HudText(hintText, 0, 0, 1, -0.25f);
    hint.create();
  }
  private static void createInspectionPanels() {
    float borderSize = 0.03f;
    float width = 0.9f;
    float height = 0.8f;
    float edgeX = -1;
    float offsetX = width / 2f + borderSize;
    float edgeY = 0;
    float offsetY = 0f;
    RectangleModel panelModel = new RectangleModel(width, height);
    Image panelImage = new Image("/images/blankFace.png");
    float panelAlpha = 0.8f;
    // Create the society Inspection Panel
    RectangleMesh societyPanelMesh = new RectangleMesh(panelModel, new Material(panelImage));
    societyPanelMesh.getMaterial().setAlpha(panelAlpha);
    societyInspectionPanel = new HudObject(societyPanelMesh, societyPanelText, edgeX, offsetX,
        edgeY, offsetY);
    societyInspectionPanel.create();
    // Create the terrain inspection Panel
    RectangleMesh terrainPanelMesh = new RectangleMesh(panelModel, new Material(panelImage));
    terrainPanelMesh.getMaterial().setAlpha(panelAlpha);
    terrainInspectionPanel = new HudObject(terrainPanelMesh, terrainPanelText, edgeX, offsetX,
        edgeY, offsetY);
    terrainInspectionPanel.create();
    // create the close button
    RectangleModel closeModel = new RectangleModel(borderSize, borderSize);
    Image closeImage = new Image("/images/close-button-darker.png");
    RectangleMesh closeMesh = new RectangleMesh(closeModel, new Material(closeImage));
    closeButton = new ButtonObject(closeMesh, -1, width + borderSize * 1.5f, 0,
        offsetY + height / 2f + borderSize / 2f);
    closeButton.getHudImage().getMesh().getMaterial().setAlpha(panelAlpha);
    closeButton.create();
    // create Borders
    Image borderImage = new Image("/images/hudBorder.png");
    Material borderMaterial = new Material(borderImage);
    //create top border
    float horizontalWidth = width + (borderSize * 2);
    RectangleModel horizontalModel = new RectangleModel(horizontalWidth, borderSize);
    RectangleMesh horizontalMesh = new RectangleMesh(horizontalModel, borderMaterial);
    HudImage borderTop = new HudImage(horizontalMesh, -1, horizontalWidth / 2, 0,
        offsetY + height / 2f + borderSize / 2f);
    borderTop.create();
    panelBorders.add(borderTop);
    // create bottom border
    HudImage borderBottom = new HudImage(horizontalMesh, -1, horizontalWidth / 2, 0,
        offsetY - height / 2f - borderSize / 2f);
    borderBottom.create();
    panelBorders.add(borderBottom);
    // create left border
    float verticalHeight = height + (borderSize * 2);
    RectangleModel verticalModel = new RectangleModel(borderSize, verticalHeight);
    RectangleMesh verticalMesh = new RectangleMesh(verticalModel, borderMaterial);
    HudImage borderLeft = new HudImage(verticalMesh, -1, borderSize / 2, 0, offsetY);
    borderLeft.create();
    panelBorders.add(borderLeft);
    //create right border
    HudImage borderRight = new HudImage(verticalMesh, -1, width + (borderSize * 1.5f), 0, offsetY);
    borderRight.create();
    panelBorders.add(borderRight);
    // set the alpha for the borders
    for (HudImage border : panelBorders) {
      border.getMesh().getMaterial().setAlpha(panelAlpha);
    }
    // create the Panel Title
    panelTitle = new HudText(panelTitleText, edgeX, 0, edgeY, offsetY + height / 2f);
    panelTitle.setOffsetX(borderSize + (width / 2f) - (panelTitle.getWidth() / 2f));
    panelTitle.create();
    // create the terrainTileImage
    float terrainTileSize = 0.2f;
    RectangleModel terrainTileModel = new RectangleModel(terrainTileSize, terrainTileSize);
    RectangleMesh terrainTileMesh = new RectangleMesh(terrainTileModel, new Material());
    terrainTileImage = new HudImage(terrainTileMesh, edgeX, borderSize + width / 2, 0, height / 4);
    terrainTileImage.create();
  }

  private static void renderInspectionPanel(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    boolean shouldRender = false;
    if (societyPanelActive) {
      societyInspectionPanel.render(guiRenderer, textRenderer);
      shouldRender = true;
    } else if (terrainPanelActive) {
      terrainTileImage.render(guiRenderer);
      terrainInspectionPanel.render(guiRenderer, textRenderer);
      shouldRender = true;
    }
    if (shouldRender) {
      closeButton.render(guiRenderer, textRenderer);
      for (HudImage border : panelBorders) {
        border.render(guiRenderer);
      }
      panelTitle.render(textRenderer);
    }
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
    // create background Panel for the turn Button
    float panelWidth = arrowTextObject.getWidth() * 1.2f;
    float panelHeight = (height + arrowTextObject.getHeight()) * 1.4f;
    RectangleModel panelModel = new RectangleModel(panelWidth, panelHeight);
    Material panelMaterial = new Material(PANEL_IMAGE, PANEL_COLOUR);
    RectangleMesh panelMesh = new RectangleMesh(panelModel, panelMaterial);
    arrowButtonPanel = new HudImage(panelMesh, 1, -width * 1.1f, -1, height * 0.8f);
    arrowButtonPanel.create();
  }

  private static void createSocietyButtons() {
    // set the image and the model and create the button
    Image buttonImage = new Image("/images/hudElementBackground.png");
    RectangleModel buttonModel = new RectangleModel(SOCIETY_BUTTON_WIDTH, SOCIETY_BUTTON_HEIGHT);
    for (int i = 0; i < World.getSocieties().length; i++) {
      Society society = World.getSocieties()[i];
      String societyString;
      float fontSize;
      if (i == 0) {
        societyString = "Your Society";
        fontSize = 0.5f;
      } else {
        societyString = String.format("Society: %d", society.getSocietyId() + 1);
        fontSize = 0.6f;
      }
      Text societyText = new Text(societyString, fontSize,
          Vector3f.subtract(society.getSocietyColor(), 0.2f));
      societyText.setCentreHorizontal(true);
      societyText.setCentreVertical(true);
      float xoffset = calculateSocietyButtonXOffset(World.getSocieties().length, i + 1);
      RectangleMesh buttonMesh = new RectangleMesh(buttonModel, new Material(buttonImage));
      SocietyButton societyButton = new SocietyButton(buttonMesh, societyText, 0, xoffset,
          -1, SOCIETY_BUTTON_OFFSET_Y, society);
      // create the button's VAO and VBOs
      societyButton.create();
      // add to the list of societies
      societyButtons.add(societyButton);
    }
    createSocietyButtonPanel();
  }

  private static void createSocietyButtonPanel() {
    // create the background panel for the buttons
    float panelWidth = (SOCIETY_BUTTON_WIDTH * societyButtons.size())
        + (SOCIETY_BUTTON_PADDING * (societyButtons.size()));
    float panelHeight = SOCIETY_BUTTON_HEIGHT * 1.5f;
    RectangleModel panelModel = new RectangleModel(panelWidth, panelHeight);
    Material panelMaterial = new Material(SOCIETY_PANEL_IMAGE, PANEL_COLOUR);
    RectangleMesh panelMesh = new RectangleMesh(panelModel, panelMaterial);
    societyButtonPanel = new HudImage(panelMesh, 0, 0, -1, SOCIETY_BUTTON_OFFSET_Y);
    societyButtonPanel.create();
  }


  private static float calculateSocietyButtonXOffset(int amount, int number) {
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
      offset = -(amountCenter * ((SOCIETY_BUTTON_WIDTH / 2) + (SOCIETY_BUTTON_PADDING / 2)));
      // find how far to place left
      int difference = amountSide - number;
      offset = offset - (difference * (SOCIETY_BUTTON_PADDING + SOCIETY_BUTTON_WIDTH));
      // add final amount
      offset = offset - (SOCIETY_BUTTON_PADDING / 2) - (SOCIETY_BUTTON_WIDTH / 2);
    } else if (number == amountSide + amountCenter) {
      // place a button in the center
      offset = 0;
    } else {
      // place buttons to the right
      offset = amountCenter * ((SOCIETY_BUTTON_WIDTH / 2) + (SOCIETY_BUTTON_PADDING / 2));
      // find how far to place right
      int difference = number - (amountSide + amountCenter + 1);
      offset = offset + (difference * (SOCIETY_BUTTON_PADDING + SOCIETY_BUTTON_WIDTH));
      // add final amount
      offset = offset + (SOCIETY_BUTTON_PADDING / 2) + (SOCIETY_BUTTON_WIDTH / 2);
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
    glDisable(GL_DEPTH_TEST);
    arrowButtonPanel.render(guiRenderer);
    glEnable(GL_DEPTH_TEST);
    //Render Hud Elements First to fix alpha blending on text
    arrowTextObject.render(textRenderer);
    turnCounter.render(guiRenderer, textRenderer);
    scoreCounter.render(guiRenderer, textRenderer);
    for (ButtonObject button : societyButtons) {
      button.render(guiRenderer, textRenderer);
    }
    arrowButton.render(guiRenderer, textRenderer);
    societyButtonPanel.render(guiRenderer);
    if (devHudActive) {
      coordinates.render(textRenderer);
    }
    renderInspectionPanel(guiRenderer, textRenderer);
    if (Game.getState() == GameState.WARRING
        || Game.getState() == GameState.CLAIM_TILE
        || Game.getState() == GameState.AI_CLAIM) {
      hint.render(textRenderer);
    }
  }

  /**
   * Resize elements to compensate for screen resize.
   */
  public static void resize() {
    turnCounter.reposition();
    scoreCounter.reposition();
    coordinates.reposition();
    for (ButtonObject button : societyButtons) {
      button.reposition();
    }
    arrowButton.reposition();
    arrowTextObject.reposition();
    societyButtonPanel.reposition();
    arrowButtonPanel.reposition();
    societyInspectionPanel.reposition();
    terrainInspectionPanel.reposition();
    closeButton.reposition();
    for (HudImage border : panelBorders) {
      border.reposition();
    }
    panelTitle.reposition();
    terrainTileImage.reposition();
    hint.reposition();
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
    scoreCounter.destroy();
    coordinates.destroy();
    for (ButtonObject button : societyButtons) {
      button.destroy();
    }
    societyButtons.clear();
    arrowButton.destroy();
    arrowTextObject.destroy();
    societyButtonPanel.destroy();
    arrowButtonPanel.destroy();
    societyInspectionPanel.destroy();
    terrainInspectionPanel.destroy();
    closeButton.destroy();
    for (HudImage border : panelBorders) {
      border.destroy();
    }
    panelBorders.clear();
    panelTitle.destroy();
    terrainTileImage.destroy();
    hint.destroy();
    turn = 0;
  }

  public static String calculateSocietyPanelString(Society society) {
    String societyString;
    if (society.getSocietyId() == 0) {
      societyString = "Your Society";
    } else {
      societyString = "Society " + (society.getSocietyId() + 1);
    }
    String startPadding = StringUtils.repeat(" \n", 3);
    String linePadding = "\n \n";
    return String.format("%9$s Society Name: %s %10$s Population: %d %10$s Food: %d "
            + "%10$s Raw Material: %d %10$s Territory Size: %d %10$s Average Aggressiveness: %.2f "
            + "%10$s Average Productivity: %.2f %10$s Average Lifespan: %.2f",
        societyString, society.getPopulation().size(), society.getTotalFoodResource(),
        society.getTotalRawMaterialResource(), society.getTerritory().size(),
        society.getAverageAggressiveness(), society.getAverageLifeExpectancy(),
        society.getAverageLifeExpectancy(), startPadding, linePadding);
  }

  private static String calculateTerrainPanelString(TileWorldObject tile) {
    String startPadding = StringUtils.repeat("\n ", 12);
    String linePadding = "\n \n";
    // Calculate the tile type
    String tileType;
    if (tile.getTile() instanceof WaterTile) {
      tileType = "Water Tile";
    } else if (tile.getTile() instanceof FertileTile) {
      tileType = "Fertile Tile";
    } else if (tile.getTile() instanceof AridTile) {
      tileType = "Arid Tile";
    } else {
      tileType = "Plain Tile";
    }
    // check if claimed by a society
    String claimedSocietyString = "Unclaimed";
    boolean found = false;
    int i = 0;
    while (i < World.getSocieties().length && !found) {
      Society society = World.getSocieties()[i];
      if (society.getTerritory().contains(tile)) {
        found = true;
        if (i == 0) {
          claimedSocietyString = "Your Society";
        } else {
          claimedSocietyString = String.format("Society %d", society.getSocietyId() + 1);
        }
      }
      i++;
    }
    // return the string
    return String.format("%5$s Tile Type: %s %6$s Food Resources: %d "
            + "%6$s Raw Material: %d %6$s Claimed By: %s",
        tileType, tile.getFoodResource(), tile.getRawMaterialResource(), claimedSocietyString,
        startPadding, linePadding);
  }
}