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
import engine.objects.gui.SocietyButton;
import engine.utils.ColourUtils;
import game.Game;
import game.menu.dataObjects.TradeDeal;
import game.world.Hud;
import game.world.World;
import java.awt.Color;
import java.util.ArrayList;
import math.Vector4f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class TradingMenu {
  private static final int DEFAULT_LENGTH_OF_TRADE_DEAL_IN_TURNS = 3;
  private static final Image PLUS_TEXTURE = new Image("/images/plusTexture.png");
  private static final Image MINUS_TEXTURE = new Image("/images/minusTexture.png");
  private static final Image FOOD_ICON = new Image("/images/foodIcon.png");
  private static final Image RAW_MATS_ICON = new Image("/images/rawMaterials.png");
  private static final float DEFAULT_FONT_SIZE = 2f;
  private static final Text DEFAULT_DEAL_AMOUNT = new Text("0", DEFAULT_FONT_SIZE);
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
  private static TradeDeal tradeDeal;
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
  private static HudText leftFoodAmount;
  private static HudText leftRawMatsAmount;
  private static HudText rightFoodAmount;
  private static HudText rightRawMatsAmount;
  private static ButtonObject acceptButton;
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
  private static ArrayList<HudText> dealNumbers = new ArrayList<>();
  private static boolean societiesChosen = false;

  public static void setTradeDeal(TradeDeal tradeDeal) {
    TradingMenu.tradeDeal = tradeDeal;
  }

  public static boolean isSocietiesChosen() {
    return societiesChosen;
  }

  public static void setSocietiesChosen(boolean societiesChosen) {
    TradingMenu.societiesChosen = societiesChosen;
  }

  public static HudImage getLeftFoodIcon() {
    return leftFoodIcon;
  }

  public static HudImage getRightFoodIcon() {
    return rightFoodIcon;
  }

  public static HudImage getRightRawMaterialsIcon() {
    return rightRawMaterialsIcon;
  }

  public static HudImage getLeftRawMaterialsIcon() {
    return leftRawMaterialsIcon;
  }

  public static HudText getLeftFoodAmount() {
    return leftFoodAmount;
  }

  public static HudText getLeftRawMatsAmount() {
    return leftRawMatsAmount;
  }

  public static HudText getRightFoodAmount() {
    return rightFoodAmount;
  }

  public static HudText getRightRawMatsAmount() {
    return rightRawMatsAmount;
  }

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
  }

  private static void createObjects() {
    createTradingPanels();
    createLeftDealButtons();
    createRightDealButtons();
    createRightDealAmounts();
    createLeftDealAmount();
    createAcceptButton();
  }

  private static void createAcceptButton() {
    RectangleModel acceptButtonModel = new RectangleModel(0.5f, 0.1f);
    Material acceptButtonMat = new Material(new Image("/images/buttonTexture.png"));
    RectangleMesh acceptButtonMesh = new RectangleMesh(acceptButtonModel, acceptButtonMat);
    Text acceptButtonText = new Text("Send Deal", 0.8f, ColourUtils.convertColor(Color.WHITE), true, true, false);
    acceptButton = new ButtonObject(acceptButtonMesh, acceptButtonText, TRADE_PANEL_EDGE_X, 0.005f, 0, -0.5f);
    acceptButton.setInactiveColourOffset(new Vector4f(ColourUtils.convertColor(Color.GREEN), 1.0f));
    acceptButton.setActiveColourOffset(new Vector4f(ColourUtils.convertColor(ChartColor.DARK_GREEN), 1.0f));
    acceptButton.create();
    acceptButton.disable();
  }

  private static void createLeftDealAmount() {
    // create left food amount
    leftFoodAmount = new HudText(DEFAULT_DEAL_AMOUNT, -TRADE_PANEL_EDGE_X, 0.05f, EDGE_Y, 0.11f);
    leftFoodAmount.create();
    dealNumbers.add(leftFoodAmount);
//    dealNumbers.add(leftFoodAmount);
    // create left raw materials amount
    leftRawMatsAmount = new HudText(DEFAULT_DEAL_AMOUNT, -TRADE_PANEL_EDGE_X, 0.05f, EDGE_Y - 0.19f, 0);
    leftRawMatsAmount.create();
    dealNumbers.add(leftRawMatsAmount);
    // create right food amount
    rightFoodAmount = new HudText(DEFAULT_DEAL_AMOUNT, TRADE_PANEL_EDGE_X, 0.05f, EDGE_Y, 0.11f);
    rightFoodAmount.create();
    dealNumbers.add(rightFoodAmount);
    // create left raw materials amount
    rightRawMatsAmount = new HudText(DEFAULT_DEAL_AMOUNT, TRADE_PANEL_EDGE_X, 0.05f, EDGE_Y - 0.19f, 0);
    rightRawMatsAmount.create();
    dealNumbers.add(rightRawMatsAmount);
  }

  private static void createRightDealAmounts() {
  }

  private static void createLeftDealButtons() {
    Text dealButtonText = new Text("", 0.6f,
        ColourUtils.convertColor(Color.BLACK), true, true, true);
    RectangleMesh FoodAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    leftAddRawMatButton = new ButtonObject(FoodAddMesh, dealButtonText, -TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    leftAddRawMatButton.create();
    dealButtons.add(leftAddRawMatButton);

    RectangleMesh foodMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    leftMinusRawMatButton = new ButtonObject(foodMinusMesh, dealButtonText, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    leftMinusRawMatButton.create();
    dealButtons.add(leftMinusRawMatButton);

    RectangleMesh rawMatAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    leftAddFoodButton = new ButtonObject(rawMatAddMesh, dealButtonText, -TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    leftAddFoodButton.create();
    dealButtons.add(leftAddFoodButton);

    RectangleMesh rawMatMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    leftMinusFoodButton = new ButtonObject(rawMatMinusMesh, dealButtonText, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    leftMinusFoodButton.create();
    dealButtons.add(leftMinusFoodButton);
  }

  private static void createRightDealButtons() {
    Text dealButtonText = new Text("", 0.6f,
        ColourUtils.convertColor(Color.BLACK), true, true, true);
    RectangleMesh FoodAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    rightAddRawMatButton = new ButtonObject(FoodAddMesh, dealButtonText, TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    rightAddRawMatButton.create();
    dealButtons.add(rightAddRawMatButton);

    RectangleMesh foodMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    rightMinusRawMatButton = new ButtonObject(foodMinusMesh, dealButtonText, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.1f, OFFSET_Y);
    rightMinusRawMatButton.create();
    dealButtons.add(rightMinusRawMatButton);

    RectangleMesh rawMatAddMesh = new RectangleMesh(dealButtonModel,
        new Material(PLUS_TEXTURE));
    rightAddFoodButton = new ButtonObject(rawMatAddMesh, dealButtonText, TRADE_PANEL_EDGE_X, 0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    rightAddFoodButton.create();
    dealButtons.add(rightAddFoodButton);

    RectangleMesh rawMatMinusMesh = new RectangleMesh(dealButtonModel,
        new Material(MINUS_TEXTURE));
    rightMinusFoodButton = new ButtonObject(rawMatMinusMesh, dealButtonText, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.2f, OFFSET_Y);
    rightMinusFoodButton.create();
    dealButtons.add(rightMinusFoodButton);
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
    leftRawMaterialsIcon = new HudImage(rawMatsIcon, -TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.25f, 0);
    leftRawMaterialsIcon.create();
    icons.add(leftRawMaterialsIcon);
    // create Right food Icon
    rightFoodIcon = new HudImage(foodIcon, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y + 0.06f, 0);
    leftFoodIcon.create();
    icons.add(rightFoodIcon);
    // create Right Raw Materials Icon
    rightRawMaterialsIcon = new HudImage(rawMatsIcon, TRADE_PANEL_EDGE_X, -0.1f, EDGE_Y - 0.25f, 0);
    leftRawMaterialsIcon.create();
    icons.add(rightRawMaterialsIcon);
  }

  public static void update(Window window) {
    resize();
    Hud.updateHint();
    if (!leftSocietyPanel.isActive()) {
      leftSocietyPanel.setActive(true);
      // set the text for the panel
      Society leftSociety = World.getActiveSocieties().get(0);
      String leftPanelString = calculateSocietyPanelString(leftSociety);
      leftSocietyPanel.getPanelText().setString(leftPanelString);
      leftSocietyPanel.getPanelText().setShouldWrap(true);
      leftSocietyPanel.getPanel().updateText(leftSocietyPanel.getPanelText());
    }
    for (ButtonObject dealButton : dealButtons) {
      dealButton.update(window);
      // check if mouse click
      if (societiesChosen) {
        acceptButton.update(window);
        tradeDeal.setSocietyA(World.getActiveSocieties().get(0));
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
            && Game.canClick()
            && dealButton.isMouseOver(window)) {


          if (dealButton == leftAddFoodButton) {
            int currentFoodGiven = tradeDeal.getFoodGiven();
            int newFoodAmount = selectMinimum(currentFoodGiven + 1, tradeDeal.getSocietyA().getTotalFoodResource());
            tradeDeal.setFoodGiven(newFoodAmount);
            leftFoodAmount.setText(new Text(String.valueOf(newFoodAmount), DEFAULT_FONT_SIZE));

          } else if (dealButton == leftMinusFoodButton) {
            int currentFoodGiven = tradeDeal.getFoodGiven();
            int newFoodAmount = selectMinimum(currentFoodGiven - 1, tradeDeal.getSocietyA().getTotalFoodResource());
            tradeDeal.setFoodGiven(newFoodAmount);
            leftFoodAmount.setText(new Text(String.valueOf(newFoodAmount), DEFAULT_FONT_SIZE));

          } else if (dealButton == leftAddRawMatButton) {
            int currentRawMatsGiven = tradeDeal.getRawMarsGiven();
            int newRawMatsAmt = selectMinimum(currentRawMatsGiven + 1, tradeDeal.getSocietyA().getTotalRawMaterialResource());
            tradeDeal.setRawMarsGiven(newRawMatsAmt);
            leftRawMatsAmount.setText(new Text(String.valueOf(newRawMatsAmt), DEFAULT_FONT_SIZE));

          } else if (dealButton == leftMinusRawMatButton) {
            int currentRawMatsGiven = tradeDeal.getRawMarsGiven();
            int newRawMatsAmt = selectMinimum(currentRawMatsGiven - 1, tradeDeal.getSocietyA().getTotalRawMaterialResource());
            tradeDeal.setRawMarsGiven(newRawMatsAmt);
            leftRawMatsAmount.setText(new Text(String.valueOf(newRawMatsAmt), DEFAULT_FONT_SIZE));


          } else if (dealButton == rightAddFoodButton) {
            int foodReceived = tradeDeal.getFoodReceived();
            int newFoodAmount = selectMinimum(foodReceived + 1, tradeDeal.getSocietyB().getTotalFoodResource());
            tradeDeal.setFoodReceived(newFoodAmount);
            rightFoodAmount.setText(new Text(String.valueOf(newFoodAmount), DEFAULT_FONT_SIZE));

          } else if (dealButton == rightMinusFoodButton) {
            int foodReceived = tradeDeal.getFoodReceived();
            int newFoodAmount = selectMinimum(foodReceived - 1, tradeDeal.getSocietyB().getTotalFoodResource());
            tradeDeal.setFoodReceived(newFoodAmount);
            rightFoodAmount.setText(new Text(String.valueOf(newFoodAmount), DEFAULT_FONT_SIZE));

          } else if (dealButton == rightAddRawMatButton) {
            int rawMatsReceived = tradeDeal.getRawMatsReceived();
            int rawMatsAmount = selectMinimum(rawMatsReceived + 1, tradeDeal.getSocietyB().getTotalRawMaterialResource());
            tradeDeal.setRawMatsReceived(rawMatsAmount);
            rightRawMatsAmount.setText(new Text(String.valueOf(rawMatsAmount), DEFAULT_FONT_SIZE));

          } else if (dealButton == rightMinusRawMatButton) {
            int rawMatsReceived = tradeDeal.getRawMatsReceived();
            int rawMatsAmount = selectMinimum(rawMatsReceived - 1, tradeDeal.getSocietyB().getTotalRawMaterialResource());
            tradeDeal.setRawMatsReceived(rawMatsAmount);
            rightRawMatsAmount.setText(new Text(String.valueOf(rawMatsAmount), DEFAULT_FONT_SIZE));
          }
          Game.resetButtonLock();
        }
      }
    }
    for (int i = 0; i < societyButtons.size(); i++) {
      if (!societiesChosen) {
        if (!World.getActiveSocieties().get(0).getPossibleTradingSocieties().contains(societyButtons.get(i).getSociety())) {
          societyButtons.get(i).disable();
        }
        societyButtons.get(i).update(window);
        // check if mouse click
        if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
            && societyButtons.get(i).isMouseOver(window) && societyButtons.get(i).isEnabled()) {
          if (!(societyButtons.get(i).getSociety() == World.getActiveSocieties().get(0))) {
            societiesChosen = true;
            acceptButton.enable();
            rightSocietyPanel.setActive(true);
            // set the text for the panel
            Society rightSociety = World.getActiveSocieties().get(i);
            tradeDeal.setSocietyB(rightSociety);
            String rightPanelString = calculateSocietyPanelString(rightSociety);
            rightSocietyPanel.getPanelText().setString(rightPanelString);
            rightSocietyPanel.getPanelText().setShouldWrap(true);
            rightSocietyPanel.getPanel().updateText(rightSocietyPanel.getPanelText());
          }
        }
      }
    }
    if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
        && Game.canClick()
        && acceptButton.isMouseOver(window)) {
      boolean accepted = tradeDeal.getSocietyB().examineTradeDeal(tradeDeal);
      if (accepted) {
        tradeDeal.setEndTurnOfDeal(Hud.getTurn() + DEFAULT_LENGTH_OF_TRADE_DEAL_IN_TURNS);
        tradeDeal.getSocietyA().activateTradeDeal(tradeDeal);
        tradeDeal.getSocietyB().activateTradeDeal(tradeDeal);
        for (SocietyButton socButton : societyButtons) {
          socButton.enable();
        }
        reset(window);
        tradeDeal.getSocietyA().setEndTurn(true);
      }
    }
  }

  public static void reset(Window window) {
    for (HudText amountText : dealNumbers) {
      amountText.setText(new Text(String.valueOf(0), DEFAULT_FONT_SIZE));
    }
    for (SocietyButton societyButton : societyButtons) {
      societyButton.enable();
      societyButton.update(window);
    }
    societiesChosen = false;
    acceptButton.disable();
    rightSocietyPanel.setActive(false);
    leftSocietyPanel.setActive(false);
  }

  private static void resize() {
    Hud.resize();
    for (ButtonObject dealButton : dealButtons) {
      dealButton.reposition();
    }
    for (HudImage icon : icons) {
      icon.reposition();
    }
    acceptButton.reposition();
    leftSocietyPanel.reposition();
    rightSocietyPanel.reposition();
    leftTradeDealPanel.reposition();
    rightTradeDealPanel.reposition();
    for (HudText dealAmount : dealNumbers) {
      dealAmount.reposition();
    }
  }

  private static int selectMinimum(int currentResource, int societyMaximumResource) {
    if (currentResource < 0) {
      return 0;
    } else if (currentResource == societyMaximumResource) {
      return currentResource;
    } else {
      return Math.min(currentResource, societyMaximumResource);
    }
  }

  public static void render(GuiRenderer renderer, TextRenderer textRenderer) {
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
    if (acceptButton.isEnabled()) {
      acceptButton.render(renderer, textRenderer);
    }
    for (HudText dealNumber : dealNumbers) {
      dealNumber.render(textRenderer);
    }
    for (ButtonObject dealButton : dealButtons) {
      dealButton.render(renderer, textRenderer);
    }
    for (SocietyButton societyButton : societyButtons) {
      societyButton.render(renderer, textRenderer);
    }
    for (HudImage icon : icons) {
      icon.render(renderer);
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
    Hud.renderHint(textRenderer);
  }

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
    for (ButtonObject dealButton : dealButtons) {
      dealButton.destroy();
    }
    dealButtons.clear();
    for (HudText dealNumber : dealNumbers) {
      dealNumber.destroy();
    }
    dealNumbers.clear();
    acceptButton.destroy();
  }
}
