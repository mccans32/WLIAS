package game.menu;

import static game.world.Hud.calculateSocietyPanelString;
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
import engine.objects.gui.HudText;
import engine.objects.gui.InspectionPanelObject;
import engine.utils.ColourUtils;
import game.Game;
import game.menu.data.TradeDeal;
import game.world.Hud;
import java.awt.Color;
import java.util.ArrayList;
import math.Vector4f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;

public class DealingMenu {
  private static final int DEFAULT_LENGTH_OF_TRADE_DEAL_IN_TURNS = 3;
  private static final Image FOOD_ICON = new Image("/images/foodIcon.png");
  private static final Image RAW_MATS_ICON = new Image("/images/rawMaterials.png");
  private static final float DEFAULT_FONT_SIZE = 2f;
  private static final Text DEFAULT_DEAL_AMOUNT = new Text("0", DEFAULT_FONT_SIZE);
  private static final RectangleModel ICON_MODEL = new RectangleModel(0.1f, 0.1f);
  private static final float BORDER_SIZE = 0.03f;
  private static final float WIDTH = 0.65f;
  private static final float HEIGHT = 0.7f;
  private static final float EDGE_X = -1;
  private static final float OFFSET_X = WIDTH / 2f + BORDER_SIZE;
  private static final float EDGE_Y = 0;
  private static final float OFFSET_Y = 0f;
  private static final float TRADE_PANEL_EDGE_X = 0.2f;
  private static TradeDeal tradeDeal = new TradeDeal(0, 0, 0, 0);
  private static ArrayList<HudImage> icons = new ArrayList<>();
  private static HudImage leftFoodIcon;
  private static HudImage rightFoodIcon;
  private static HudImage rightRawMaterialsIcon;
  private static HudImage leftRawMaterialsIcon;
  private static HudText leftFoodAmount;
  private static HudText leftRawMatsAmount;
  private static HudText rightFoodAmount;
  private static HudText rightRawMatsAmount;
  private static ButtonObject acceptButton;
  private static ButtonObject declineButton;
  private static Text leftSocietyPanelText = new Text("", 0.4f);
  private static Text rightSocietyPanelText = new Text("", 0.4f);
  private static Text tradeDealPanelText = new Text("", 0.4f);
  private static InspectionPanelObject leftSocietyPanel;
  private static InspectionPanelObject rightSocietyPanel;
  private static InspectionPanelObject leftTradeDealPanel;
  private static InspectionPanelObject rightTradeDealPanel;
  private static Text leftPanelTitleText = new Text("Your Societies Details", 0.45f);
  private static Text rightPanelTitleText = new Text("Other Societies Details", 0.45f);
  private static Text leftTradeDealPanelText = new Text("Goods Lost", 0.45f);
  private static Text rightTradeDealPanelText = new Text("Goods Gained", 0.45f);
  private static ArrayList<HudText> dealNumbers = new ArrayList<>();
  private static boolean updatedHint = false;
  private static boolean updateTradeDeal = false;

  public static void setTradeDeal(TradeDeal tradeDeal) {
    DealingMenu.tradeDeal = tradeDeal;
  }

  /**
   * Create.
   */
  public static void create() {
    updatedHint = false;
    createObjects();
  }

  private static void createObjects() {
    createTradingPanels();
    createRightDealAmounts();
    createLeftDealAmount();
    createAcceptButton();
    createDeclineButton();
  }

  private static void createDeclineButton() {
    RectangleModel acceptButtonModel = new RectangleModel(0.5f, 0.1f);
    Material acceptButtonMat = new Material(new Image("/images/buttonTexture.png"));
    RectangleMesh acceptButtonMesh = new RectangleMesh(acceptButtonModel, acceptButtonMat);
    Text acceptButtonText = new Text("Decline", 0.8f,
        ColourUtils.convertColor(Color.WHITE), true, true, false);
    declineButton = new ButtonObject(acceptButtonMesh, acceptButtonText, -TRADE_PANEL_EDGE_X,
        0.005f, 0, -0.5f);
    declineButton.setInactiveColourOffset(new Vector4f(ColourUtils.convertColor(Color.RED),
        1.0f));
    declineButton.setActiveColourOffset(
        new Vector4f(ColourUtils.convertColor(ChartColor.DARK_RED), 1.0f));
    declineButton.create();

  }

  private static void createAcceptButton() {
    RectangleModel acceptButtonModel = new RectangleModel(0.5f, 0.1f);
    Material acceptButtonMat = new Material(new Image("/images/buttonTexture.png"));
    RectangleMesh acceptButtonMesh = new RectangleMesh(acceptButtonModel, acceptButtonMat);
    Text acceptButtonText = new Text("Accept", 0.8f,
        ColourUtils.convertColor(Color.WHITE), true, true, false);
    acceptButton = new ButtonObject(acceptButtonMesh, acceptButtonText, TRADE_PANEL_EDGE_X,
        0.005f, 0, -0.5f);
    acceptButton.setInactiveColourOffset(new Vector4f(ColourUtils.convertColor(Color.GREEN),
        1.0f));
    acceptButton.setActiveColourOffset(
        new Vector4f(ColourUtils.convertColor(ChartColor.DARK_GREEN), 1.0f));
    acceptButton.create();
  }

  private static void createLeftDealAmount() {
    // create left food amount
    leftFoodAmount = new HudText(DEFAULT_DEAL_AMOUNT, -TRADE_PANEL_EDGE_X, 0.05f,
        EDGE_Y, 0.2f);
    leftFoodAmount.create();
    dealNumbers.add(leftFoodAmount);
    // create left raw materials amount
    leftRawMatsAmount = new HudText(DEFAULT_DEAL_AMOUNT, -TRADE_PANEL_EDGE_X, 0.05f,
        EDGE_Y - 0.13f, 0);
    leftRawMatsAmount.create();
    dealNumbers.add(leftRawMatsAmount);
    // create right food amount
    rightFoodAmount = new HudText(DEFAULT_DEAL_AMOUNT, TRADE_PANEL_EDGE_X, 0.05f,
        EDGE_Y, 0.2f);
    rightFoodAmount.create();
    dealNumbers.add(rightFoodAmount);
    // create left raw materials amount
    rightRawMatsAmount = new HudText(DEFAULT_DEAL_AMOUNT, TRADE_PANEL_EDGE_X, 0.05f,
        EDGE_Y - 0.13f, 0);
    rightRawMatsAmount.create();
    dealNumbers.add(rightRawMatsAmount);
  }

  private static void createRightDealAmounts() {
  }

  private static void createTradingPanels() {
    leftSocietyPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH, HEIGHT, EDGE_X, OFFSET_X,
        EDGE_Y, OFFSET_Y, leftSocietyPanelText, leftPanelTitleText);
    rightSocietyPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH, HEIGHT, -EDGE_X, -OFFSET_X,
        EDGE_Y, OFFSET_Y, rightSocietyPanelText, rightPanelTitleText);
    leftTradeDealPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH / 1.5f, HEIGHT,
        -TRADE_PANEL_EDGE_X, 0, EDGE_Y, OFFSET_Y, tradeDealPanelText, leftTradeDealPanelText);
    rightTradeDealPanel = new InspectionPanelObject(BORDER_SIZE, WIDTH / 1.5f, HEIGHT,
        TRADE_PANEL_EDGE_X, 0, EDGE_Y, OFFSET_Y, tradeDealPanelText, rightTradeDealPanelText);
    RectangleMesh foodIcon = new RectangleMesh(ICON_MODEL, new Material(FOOD_ICON));
    // create Left Food Icon
    leftFoodIcon = new HudImage(foodIcon, -TRADE_PANEL_EDGE_X, -0.1f,
        EDGE_Y + 0.06f, .09f);
    leftFoodIcon.create();
    icons.add(leftFoodIcon);
    // create Left Raw Materials Icon
    RectangleMesh rawMatsIcon = new RectangleMesh(ICON_MODEL, new Material(RAW_MATS_ICON));
    leftRawMaterialsIcon = new HudImage(rawMatsIcon, -TRADE_PANEL_EDGE_X,
        -0.1f, EDGE_Y, -0.2f);
    leftRawMaterialsIcon.create();
    icons.add(leftRawMaterialsIcon);
    // create Right food Icon
    rightFoodIcon = new HudImage(foodIcon, TRADE_PANEL_EDGE_X, -0.1f,
        EDGE_Y + 0.06f, .09f);
    leftFoodIcon.create();
    icons.add(rightFoodIcon);
    // create Right Raw Materials Icon
    rightRawMaterialsIcon = new HudImage(rawMatsIcon, TRADE_PANEL_EDGE_X,
        -0.1f, EDGE_Y, -0.2f);
    leftRawMaterialsIcon.create();
    icons.add(rightRawMaterialsIcon);
  }

  private static void resize() {
    Hud.resize();
    for (HudImage icon : icons) {
      icon.reposition();
    }
    acceptButton.reposition();
    declineButton.reposition();
    leftSocietyPanel.reposition();
    rightSocietyPanel.reposition();
    leftTradeDealPanel.reposition();
    rightTradeDealPanel.reposition();
    for (HudText dealAmount : dealNumbers) {
      dealAmount.reposition();
    }
  }

  /**
   * Render.
   *
   * @param renderer     the renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer renderer, TextRenderer textRenderer) {
    if (updateTradeDeal) {
      glDisable(GL_DEPTH_TEST);
      leftTradeDealPanel.getPanel().render(renderer, textRenderer);
      leftTradeDealPanel.getPanelTitle().render(textRenderer);
      leftFoodAmount.render(textRenderer);
      for (HudImage border : leftTradeDealPanel.getPanelBorders()) {
        border.render(renderer);
      }
      rightTradeDealPanel.getPanel().render(renderer, textRenderer);
      rightTradeDealPanel.getPanelTitle().render(textRenderer);
      for (HudImage border : rightTradeDealPanel.getPanelBorders()) {
        border.render(renderer);
      }
      glEnable(GL_DEPTH_TEST);
      for (HudText dealNumber : dealNumbers) {
        dealNumber.render(textRenderer);
      }
      for (HudImage icon : icons) {
        icon.render(renderer);
      }
      acceptButton.render(renderer, textRenderer);
      declineButton.render(renderer, textRenderer);

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
      // update the hint in the HUD since we don't want to update HUD
      // only update the hint after it has been updated in the update function
      if (updatedHint) {
        Hud.renderHint(textRenderer);
      }
    }
  }

  /**
   * Update.
   *
   * @param window the window
   */
  public static void update(Window window) {
    resize();
    acceptButton.update(window);
    declineButton.update(window);
    if (!updatedHint) {
      updatedHint = true;
    }
    if (!updateTradeDeal) {
      leftFoodAmount.setText(new Text(String.valueOf(tradeDeal.getFoodReceived()),
          DEFAULT_FONT_SIZE));
      leftRawMatsAmount.setText(new Text(String.valueOf(tradeDeal.getRawMatsReceived()),
          DEFAULT_FONT_SIZE));
      rightFoodAmount.setText(new Text(String.valueOf(tradeDeal.getFoodGiven()),
          DEFAULT_FONT_SIZE));
      rightRawMatsAmount.setText(new Text(String.valueOf(tradeDeal.getRawMatsGiven()),
          DEFAULT_FONT_SIZE));
      updateTradeDeal = true;
    }
    Hud.updateHint();
    if (!leftSocietyPanel.isActive()) {
      leftSocietyPanel.setActive(true);
      // set the text for the panel
      String leftPanelString = calculateSocietyPanelString(tradeDeal.getSocietyB());
      leftSocietyPanel.getPanelText().setString(leftPanelString);
      leftSocietyPanel.getPanelText().setShouldWrap(true);
      leftSocietyPanel.getPanel().updateText(leftSocietyPanel.getPanelText());
    }
    if (!rightSocietyPanel.isActive()) {
      rightSocietyPanel.setActive(true);
      // set the text for the panel
      String rightPanelString = calculateSocietyPanelString(tradeDeal.getSocietyA());
      rightSocietyPanel.getPanelText().setString(rightPanelString);
      rightSocietyPanel.getPanelText().setShouldWrap(true);
      rightSocietyPanel.getPanel().updateText(rightSocietyPanel.getPanelText());
    }
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
        && Game.canClick()
        && acceptButton.isMouseOver(window)) {
      tradeDeal.setEndTurnOfDeal(Hud.getTurn() + DEFAULT_LENGTH_OF_TRADE_DEAL_IN_TURNS);
      tradeDeal.getSocietyA().activateTradeDeal(tradeDeal);
      tradeDeal.getSocietyB().activateTradeDeal(tradeDeal);
      reset();
      tradeDeal.getSocietyA().setEndTurn(true);
    } else if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
        && Game.canClick()
        && declineButton.isMouseOver(window)) {
      reset();
      tradeDeal.getSocietyA().setEndTurn(true);
    }
  }

  /**
   * Reset.
   */
  public static void reset() {
    for (HudText amountText : dealNumbers) {
      amountText.setText(new Text(String.valueOf(0), DEFAULT_FONT_SIZE));
    }
    updateTradeDeal = false;
    rightSocietyPanel.setActive(false);
    leftSocietyPanel.setActive(false);
  }

  /**
   * Destroy.
   */
  public static void destroy() {
    leftSocietyPanel.destroyPanel();
    rightTradeDealPanel.destroyPanel();
    leftTradeDealPanel.destroyPanel();
    rightSocietyPanel.destroyPanel();
    for (HudImage icon : icons) {
      icon.destroy();
    }
    icons.clear();
    leftFoodIcon.destroy();
    for (HudText dealNumber : dealNumbers) {
      dealNumber.destroy();
    }
    dealNumbers.clear();
    acceptButton.destroy();
    declineButton.destroy();
  }
}
