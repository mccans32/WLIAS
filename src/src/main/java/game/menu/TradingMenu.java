package game.menu;

import static game.world.Hud.calculateSocietyPanelString;

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
import engine.objects.gui.InspectionPanelObject;
import engine.objects.gui.SocietyButton;
import engine.utils.ColourUtils;
import game.world.Hud;
import game.world.World;
import java.awt.Color;
import java.util.ArrayList;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class TradingMenu {
  private static final Image PLUS_TEXTURE = new Image("/images/plusTexture.png");
  private static final Image MINUS_TEXTURE = new Image("/images/minusTexture.png");
  private static final Image FOOD_ICON = new Image("/images/foodIcon.png");
  private static final Image RAW_MATS_ICON = new Image("/images/rawMaterials.png");
  private static final RectangleModel ICON_MODEL = new RectangleModel(0.1f, 0.1f);
  private static final float BUTTON_WIDTH = 0.1f;
  private static final float BUTTON_HEIGHT = 0.1f;
  private static final float BORDER_SIZE = 0.03f;
  private static final float WIDTH = 0.65f;
  private static final float HEIGHT = 0.7f;
  private static final float EDGE_X = -1;
  private static final float OFFSET_X = WIDTH / 2f + BORDER_SIZE;
  private static final float EDGE_Y = 0;
  private static final float OFFSET_Y = 0f;
  private static final float TRADE_PANEL_EDGE_X = 0.2f;
  private static ArrayList<HudImage> icons = new ArrayList<>();
  private static HudImage leftFoodIcon;
  private static HudImage rightFoodIcon;
  private static HudImage rightRawMaterialsIcon;
  private static HudImage leftRawMaterialsIcon;
  private static ButtonObject leftAddFoodButton;
  private static ButtonObject leftMinusFoodButton;
  private static ButtonObject leftAddRawMatButton;
  private static ButtonObject leftMinusRawMatButton;
  private static ButtonObject rightAddFoodButton;
  private static ButtonObject rightMinusFoodButton;
  private static ButtonObject rightAddRawMatButton;
  private static ButtonObject rightMinusRawMatButton;
  private static ArrayList<ButtonObject> dealButtons = new ArrayList<>();
  private static Text leftSocietyPanelText = new Text("", 0.4f);
  private static Text rightSocietyPanelText = new Text("", 0.4f);
  private static Text tradeDealPanelText = new Text("", 0.4f);
  private static InspectionPanelObject leftSocietyPanel;
  private static InspectionPanelObject rightSocietyPanel;
  private static InspectionPanelObject leftTradeDealPanel;
  private static InspectionPanelObject rightTradeDealPanel;
  private static Text leftPanelTitleText = new Text("Your Societies Details", 0.45f);
  private static Text rightPanelTitleText = new Text("Other Societies Details", 0.45f);
  private static Text leftTradeDealPanelText = new Text("Your Goods", 0.45f);
  private static Text rightTradeDealPanelText = new Text("Opponent Goods", 0.45f);
  private static ArrayList<SocietyButton> societyButtons = new ArrayList<>();
  private static RectangleModel dealButtonModel = new RectangleModel(BUTTON_WIDTH, BUTTON_HEIGHT);

  public static InspectionPanelObject getLeftSocietyPanel() {
    return leftSocietyPanel;
  }

  public static InspectionPanelObject getRightSocietyPanel() {
    return rightSocietyPanel;
  }

  public static InspectionPanelObject getLeftTradeDealPanel() {
    return leftTradeDealPanel;
  }

  public static void create() {
    societyButtons = Hud.getSocietyButtons();
    createObjects();
    createHints();
  }

  private static void createHints() {

  }

  private static void createObjects() {
    createTradingPanels();
    createLeftDealButtons();
    createRightDealButtons();
  }

  private static void createLeftDealButtons() {
    Text dealButtonText = new Text("", 0.6f,
        ColourUtils.convertColor(Color.BLACK), true, true, true);
    RectangleMesh FoodAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    leftAddFoodButton = new ButtonObject(FoodAddMesh, dealButtonText, -TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    leftAddFoodButton.create();
    dealButtons.add(leftAddFoodButton);

    RectangleMesh foodMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    leftMinusFoodButton = new ButtonObject(foodMinusMesh, dealButtonText, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    leftMinusFoodButton.create();
    dealButtons.add(leftMinusFoodButton);

    RectangleMesh rawMatAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    leftAddRawMatButton = new ButtonObject(rawMatAddMesh, dealButtonText, -TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    leftAddRawMatButton.create();
    dealButtons.add(leftAddRawMatButton);

    RectangleMesh rawMatMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    leftMinusRawMatButton = new ButtonObject(rawMatMinusMesh, dealButtonText, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    leftMinusRawMatButton.create();
    dealButtons.add(leftMinusRawMatButton);
  }

  private static void createRightDealButtons() {
    Text dealButtonText = new Text("", 0.6f,
        ColourUtils.convertColor(Color.BLACK), true, true, true);
    RectangleMesh FoodAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    rightAddFoodButton = new ButtonObject(FoodAddMesh, dealButtonText, TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    rightAddFoodButton.create();
    dealButtons.add(rightAddFoodButton);

    RectangleMesh foodMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    rightMinusFoodButton = new ButtonObject(foodMinusMesh, dealButtonText, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    rightMinusFoodButton.create();
    dealButtons.add(rightMinusFoodButton);

    RectangleMesh rawMatAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    rightAddRawMatButton = new ButtonObject(rawMatAddMesh, dealButtonText, TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    rightAddRawMatButton.create();
    dealButtons.add(rightAddRawMatButton);

    RectangleMesh rawMatMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    rightMinusRawMatButton = new ButtonObject(rawMatMinusMesh, dealButtonText, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    rightMinusRawMatButton.create();
    dealButtons.add(rightMinusRawMatButton);
  }


  private static void createTradingPanels() {
    leftSocietyPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH, HEIGHT, EDGE_X, OFFSET_X, EDGE_Y, OFFSET_Y, leftSocietyPanelText, leftPanelTitleText);
    rightSocietyPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH, HEIGHT, -EDGE_X, -OFFSET_X, EDGE_Y, OFFSET_Y, rightSocietyPanelText, rightPanelTitleText);
    leftTradeDealPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH / 1.5f, HEIGHT, -TRADE_PANEL_EDGE_X, 0, EDGE_Y, OFFSET_Y, tradeDealPanelText, leftTradeDealPanelText);
    rightTradeDealPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH / 1.5f, HEIGHT, TRADE_PANEL_EDGE_X, 0, EDGE_Y, OFFSET_Y, tradeDealPanelText, rightTradeDealPanelText);
    RectangleMesh foodIcon = new RectangleMesh(ICON_MODEL, new Material(FOOD_ICON));
    RectangleMesh rawMatsIcon = new RectangleMesh(ICON_MODEL, new Material(RAW_MATS_ICON));
    // create Left Food Icon
    leftFoodIcon = new HudImage(foodIcon, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.06f, 0);
    leftFoodIcon.create();
    icons.add(leftFoodIcon);
    // create Left Raw Materials Icon
    leftRawMaterialsIcon = new HudImage(rawMatsIcon, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.22f, 0);
    leftRawMaterialsIcon.create();
    icons.add(leftRawMaterialsIcon);
    // create Right food Icon
    rightFoodIcon = new HudImage(foodIcon, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.06f, 0);
    leftFoodIcon.create();
    icons.add(rightFoodIcon);
    // create Right Raw Materials Icon
    rightRawMaterialsIcon = new HudImage(rawMatsIcon, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.22f, 0);
    leftRawMaterialsIcon.create();
    icons.add(rightRawMaterialsIcon);
  }

  public static void update(Window window) {
    for (ButtonObject dealButton : dealButtons) {
      dealButton.update(window);
    }
    for (int i = 0; i < societyButtons.size(); i++) {
      societyButtons.get(i).update(window);
      // check if mouse click
      if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
          && societyButtons.get(i).isMouseOver(window)) {
        if (societyButtons.get(i).getSociety() == World.getActiveSocieties().get(0)) {
          leftSocietyPanel.setActive(true);
          // set the text for the panel
          Society society = World.getActiveSocieties().get(i);
          String panelString = calculateSocietyPanelString(society);
          leftSocietyPanel.getPanelText().setString(panelString);
          leftSocietyPanel.getPanelText().setShouldWrap(true);
          leftSocietyPanel.getPanel().updateText(leftSocietyPanel.getPanelText());
        } else {
          rightSocietyPanel.setActive(true);
          // set the text for the panel
          Society society = World.getActiveSocieties().get(i);
          String panelString = calculateSocietyPanelString(society);
          rightSocietyPanel.getPanelText().setString(panelString);
          rightSocietyPanel.getPanelText().setShouldWrap(true);
          rightSocietyPanel.getPanel().updateText(rightSocietyPanel.getPanelText());
        }
      }
    }
  }

  public static void render(GuiRenderer renderer, TextRenderer textRenderer) {
    for (HudImage icon : icons) {
      icon.render(renderer);
    }
    for (ButtonObject dealButton : dealButtons) {
      dealButton.render(renderer, textRenderer);
    }
    for (SocietyButton societyButton : societyButtons) {
      societyButton.render(renderer, textRenderer);
    }
    leftTradeDealPanel.getPanel().render(renderer, textRenderer);
    leftTradeDealPanel.getPanelTitle().render(textRenderer);
    for (HudImage border : leftTradeDealPanel.getPanelBorders()) {
      border.render(renderer);
    }
    rightTradeDealPanel.getPanel().render(renderer, textRenderer);
    rightTradeDealPanel.getPanelTitle().render(textRenderer);
    for (HudImage border : rightTradeDealPanel.getPanelBorders()) {
      border.render(renderer);
    }
    if (leftSocietyPanel.isActive()) {
      for (HudImage border : leftSocietyPanel.getPanelBorders()) {
        border.render(renderer);
      }
      leftSocietyPanel.getPanel().render(renderer, textRenderer);
      leftSocietyPanel.getPanelTitle().render(textRenderer);
    }
    if (rightSocietyPanel.isActive()) {
      for (HudImage border : rightSocietyPanel.getPanelBorders()) {
        border.render(renderer);
      }
      rightSocietyPanel.getPanel().render(renderer, textRenderer);
      rightSocietyPanel.getPanelTitle().render(textRenderer);
    }
  }
}
