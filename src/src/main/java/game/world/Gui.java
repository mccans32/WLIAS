package game.world;

import engine.graphics.Material;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.renderer.GuiRenderer;
import engine.graphics.renderer.TextRenderer;
import engine.graphics.text.Text;
import engine.objects.gui.GuiImage;
import engine.objects.gui.GuiText;

public class Gui {
  private static final String TEMP_SENTENCE = "0";
  private static GuiImage tempGui;
  private static GuiText tempText;
  private static float[] TEMP_GUI_VALUES = {-1, 0.1f, 1, -0.1f};

  public static void create() {
    createObjects();
  }

  public static void update() {
    resize();
  }

  private static void createObjects() {
    createTempText();
    createTempGui();
  }

  private static void createTempGui() {
    RectangleMesh guiMesh = new RectangleMesh(0.2f, 0.2f,
        new Material("/images/button_texture.jpg"));

    tempGui = new GuiImage(guiMesh, TEMP_GUI_VALUES[0], TEMP_GUI_VALUES[1], TEMP_GUI_VALUES[2],
        TEMP_GUI_VALUES[3]);
    tempGui.create();
  }

  private static void createTempText() {
    tempText = new GuiText(new Text(TEMP_SENTENCE), -1f, 0, 1f, 0);
    tempText.create();
  }

  /**
   * Render Gui Elements and Text Elements in the world setting.
   *
   * @param guiRenderer  the gui renderer
   * @param textRenderer the text renderer
   */
  public static void render(GuiRenderer guiRenderer, TextRenderer textRenderer) {
    //Render Gui Elements First to fix alpha blending on text
    renderImages(guiRenderer);
    renderTexts(textRenderer);
    renderObjects(guiRenderer, textRenderer);
  }

  private static void renderImages(GuiRenderer renderer) {
    renderer.renderObject(tempGui);
  }

  private static void renderTexts(TextRenderer renderer) {
    renderer.renderObject(tempText);
  }

  private static void renderObjects(GuiRenderer guiRenderer, TextRenderer textRenderer) {
  }

  public static void resize() {
    tempText.reposition();
    tempGui.reposition();
  }
}