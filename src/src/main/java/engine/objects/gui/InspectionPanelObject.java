package engine.objects.gui;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.graphics.text.Text;
import game.world.Hud;
import java.util.ArrayList;

public class InspectionPanelObject extends Hud {

  private static final float PANEL_ALPHA = 1f;
  private static final Image PANEL_IMAGE = new Image("/images/blankFace.png");
  private ArrayList<HudImage> panelBorders = new ArrayList<>();
  private float borderSize;
  private float width;
  private float height;
  private float edgeX;
  private float offsetX;
  private float edgeY;
  private float offsetY;
  private Text panelText;
  private HudObject panel;
  private HudText panelTitle;
  private boolean isActive = false;

  public InspectionPanelObject(float borderSize, float width, float height, float edgeX, float offsetX, float edgeY, float offsetY, Text panelText, Text panelTitleText) {
    this.borderSize = borderSize;
    this.width = width;
    this.height = height;
    this.edgeX = edgeX;
    this.offsetX = offsetX;
    this.edgeY = edgeY;
    this.offsetY = offsetY;
    this.panelText = panelText;
    createObject();
    createBorders();
    createPanelTitle(panelTitleText);
  }

  public void destroyPanel() {
    for (HudImage panelBorder : panelBorders) {
      panelBorder.destroy();
    }
    panelBorders.clear();
    panelTitle.destroy();
    panel.destroy();
  }

  public ArrayList<HudImage> getPanelBorders() {
    return panelBorders;
  }

  public Text getPanelText() {
    return panelText;
  }

  public void setPanelText(Text panelText) {
    this.panelText = panelText;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public HudText getPanelTitle() {
    return panelTitle;
  }

  private void createPanelTitle(Text panelTitleText) {
    // create the Panel Title
    panelTitle = new HudText(panelTitleText, edgeX, offsetX - width / 2, edgeY, offsetY + height / 2);
    panelTitle.create();
  }

  private void createBorders() {
    // create Borders
    Image borderImage = new Image("/images/hudBorder.png");
    Material borderMaterial = new Material(borderImage);
    //create top border
    float horizontalWidth = width + (borderSize * 2);
    RectangleModel horizontalModel = new RectangleModel(horizontalWidth, borderSize);
    RectangleMesh horizontalMesh = new RectangleMesh(horizontalModel, borderMaterial);
    HudImage borderTop = new HudImage(horizontalMesh, edgeX, offsetX, 0,
        offsetY + height / 2f + borderSize / 2f);
    borderTop.create();
    panelBorders.add(borderTop);
    // create bottom border
    HudImage borderBottom = new HudImage(horizontalMesh, edgeX, offsetX, 0,
        offsetY - height / 2f - borderSize / 2f);
    borderBottom.create();
    panelBorders.add(borderBottom);
    // create left border
    float verticalHeight = height + (borderSize * 2);
    RectangleModel verticalModel = new RectangleModel(borderSize, verticalHeight);
    RectangleMesh verticalMesh = new RectangleMesh(verticalModel, borderMaterial);
    HudImage borderLeft = new HudImage(verticalMesh, edgeX, offsetX - (width / 2) - (borderSize / 2), edgeY, offsetY);
    borderLeft.create();
    panelBorders.add(borderLeft);
    //create right border
    HudImage borderRight = new HudImage(verticalMesh, edgeX, offsetX + (width / 2) + (borderSize / 2), edgeY, offsetY);
    borderRight.create();
    panelBorders.add(borderRight);
    // set the alpha for the borders
    for (HudImage border : panelBorders) {
      border.getMesh().getMaterial().setAlpha(PANEL_ALPHA);
    }
  }

  public HudObject getPanel() {
    return panel;
  }

  public void setPanel(HudObject panel) {
    this.panel = panel;
  }

  private void createObject() {
    RectangleModel panelModel = new RectangleModel(width, height);
    // Create the society Inspection Panel
    RectangleMesh societyPanelMesh = new RectangleMesh(panelModel, new Material(PANEL_IMAGE));
    societyPanelMesh.getMaterial().setAlpha(PANEL_ALPHA);
    panel = new HudObject(societyPanelMesh, panelText, edgeX, offsetX,
        edgeY, offsetY);
    panel.create();
  }

  public void reposition() {
    panelTitle.reposition();
    panel.reposition();
    for (HudImage border : panelBorders) {
      border.reposition();
    }
  }

  public float getBorderSize() {
    return borderSize;
  }

  public void setBorderSize(float borderSize) {
    this.borderSize = borderSize;
  }

  public float getWidth() {
    return width;
  }

  public void setWidth(float width) {
    this.width = width;
  }

  public float getHeight() {
    return height;
  }

  public void setHeight(float height) {
    this.height = height;
  }

  public float getEdgeX() {
    return edgeX;
  }

  public void setEdgeX(float edgeX) {
    this.edgeX = edgeX;
  }

  public float getOffsetX() {
    return offsetX;
  }

  public void setOffsetX(float offsetX) {
    this.offsetX = offsetX;
  }

  public float getEdgeY() {
    return edgeY;
  }

  public void setEdgeY(float edgeY) {
    this.edgeY = edgeY;
  }

  public float getOffsetY() {
    return offsetY;
  }

  public void setOffsetY(float offsetY) {
    this.offsetY = offsetY;
  }
}
