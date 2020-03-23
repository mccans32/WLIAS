package game.world;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.io.Input;
import engine.objects.gui.HudObject;
import engine.objects.gui.HudText;
import engine.objects.world.Camera;
import math.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Hud {
  // Sets a number of game cycles so when we press a button to toggle it is not as sensitive
  private static final int BUTTON_LOCK_CYCLES = 5;
  private static HudObject tempObject;
  private static Text coordText = new Text("", 0.7f, new Vector3f(1, 1, 1));
  private static HudText coordinates;
  private static Boolean devHudActive = false;
  private static int hudCycleLock = 0;

  public static void create() {
    createObjects();
  }

  public static void update(Camera camera) {
    updateDevHud(camera);
    resize();
  }

  private static void updateDevHud(Camera camera) {
    // check for key press to toggle
    hudCycleLock--;
    hudCycleLock = Math.max(hudCycleLock, 0);
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
    RectangleMesh mesh = new RectangleMesh(new RectangleModel(0.1f, 0.1f),
        new Material(new Image("/images/hudElementBackground.png")));
    Text text = new Text("0");
    text.setCentreHorizontal(true);
    text.setCentreVertical(true);
    tempObject = new HudObject(mesh, text, -1f, 0.05f, 1f, -0.05f);
    tempObject.create();
    coordinates = new HudText(coordText, -1, 0, 1, -1.95f);
    coordinates.create();
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
  }

  private static void renderImages(GuiRenderer renderer) {
  }

  private static void renderTexts(TextRenderer renderer) {
    if (devHudActive) {
      coordinates.render(renderer);
    }
  }

  private static void renderObjects(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    tempObject.render(guiRenderer, textRenderer);
  }

  public static void resize() {
    tempObject.reposition();
    coordinates.reposition();
  }

  private static void calculateCoordText(Camera camera) {
    Vector3f pos = camera.getPosition();
    String coordString = String.format("x: %f, y: %f, z: %f", pos.getX(), pos.getY(), pos.getZ());
    coordText.setString(coordString);
  }
}