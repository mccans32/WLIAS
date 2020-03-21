package game.world;

import engine.graphics.Material;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.objects.gui.HudObject;

public class Hud {
  private static HudObject tempObject;

  public static void create() {
    createObjects();
  }

  public static void update() {
    resize();
  }

  private static void createObjects() {
    RectangleMesh mesh = new RectangleMesh(0.1f, 0.1f,
        new Material("/images/hudElementBackground.png"));
    Text text = new Text("0");
    text.setCentreHorizontal(true);
    text.setCentreVertical(true);
    tempObject = new HudObject(mesh, text, -1f, 0.05f, 1f, -0.05f);
    tempObject.create();
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

  }

  private static void renderObjects(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    tempObject.render(guiRenderer, textRenderer);
  }

  public static void resize() {
    tempObject.reposition();
  }
}