package game.world;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex2D;
import engine.graphics.renderer.GuiRenderer;
import engine.objects.gui.GuiObject;
import engine.utils.ColourUtils;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;

import java.awt.*;

public class GUI {

    private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
            ChartColor.VERY_LIGHT_GREEN.brighter());
    private static GuiObject tempGui;

    public static void create(Window window) {
        createObjects(window);
    }

    private static void createObjects(Window window) {
        createTempGui(window);
    }

    private static void createTempGui(Window window) {
        Mesh guiMesh = new Mesh(
                new Vertex2D[]{
                        new Vertex2D(new Vector2f(-0.1f, 0.1f),
                                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
                        new Vertex2D(new Vector2f(-0.1f, -0.1f),
                                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
                        new Vertex2D(new Vector2f(0.1f, -0.1f),
                                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
                        new Vertex2D(new Vector2f(0.1f, 0.1f),
                                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))},
                new int[]{0, 3, 1, 2},
                new Material("/images/button_texture.jpg"));

        tempGui = new GuiObject(
                new Vector2f(-1 * window.getxSpan() + 0.1f, 1 * window.getySpan() - 0.1f),
                new Vector2f(1, 1),
                guiMesh);
        tempGui.create();
    }

    public static void render(GuiRenderer renderer) {
        renderer.renderObject(tempGui);
    }

    public static void resize(Window window) {
        tempGui.reposition(-1f, 0.1f, window.getxSpan(), 1f, -0.1f, window.getySpan());
    }
}