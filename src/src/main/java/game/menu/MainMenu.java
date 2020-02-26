package game.menu;

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

public class MainMenu {
    private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
            ChartColor.VERY_LIGHT_CYAN.brighter());
    private static float BUTTON_WIDTH = 0.7f;
    private static float BUTTON_HEIGHT = 0.3f;
    private static String BUTTON_TEXTURE = "/images/button_texture.jpg";
    private static float[] START_REPOSITION_VALUES = {0, 0, 1, -0.6f};
    private static float[] EXIT_REPOSITION_VALUES = {0, 0, -1, 0.6f};
    private static Vector3f BUTTON_COLOUR = new Vector3f(0, 0, 0);
    private static Vertex2D TOP_LEFT_VERTEX = new Vertex2D(
            new Vector2f(-BUTTON_WIDTH / 2, BUTTON_HEIGHT / 2),
            BUTTON_COLOUR,
            new Vector2f(0, 0));
    private static Vertex2D TOP_RIGHT_VERTEX = new Vertex2D(
            new Vector2f(BUTTON_WIDTH / 2, BUTTON_HEIGHT / 2),
            BUTTON_COLOUR,
            new Vector2f(1, 0));
    private static Vertex2D BOTTOM_LEFT_VERTEX = new Vertex2D(
            new Vector2f(-BUTTON_WIDTH / 2, -BUTTON_HEIGHT / 2),
            BUTTON_COLOUR,
            new Vector2f(0, 1));
    private static Vertex2D BOTTOM_RIGHT_VERTEX = new Vertex2D(
            new Vector2f(BUTTON_WIDTH / 2, -BUTTON_HEIGHT / 2),
            BUTTON_COLOUR,
            new Vector2f(1, 1));
    private static int[] BUTTON_INDICES = {0, 1, 2, 3};
    private static GuiObject startButton;
    private static GuiObject exitButton;

    public static void create(Window window) {
        setBackgroundColour(BACKGROUND_COLOUR, window);
        createObjects(window);
    }

    private static void createObjects(Window window) {
//        createStartButton(window);
//        // Create Start Button
        startButton = initButton(
                window,
                START_REPOSITION_VALUES[0],
                START_REPOSITION_VALUES[1],
                START_REPOSITION_VALUES[2],
                START_REPOSITION_VALUES[3]);
        startButton.create();
        // Create Exit Button
        exitButton = initButton(
                window,
                EXIT_REPOSITION_VALUES[0],
                EXIT_REPOSITION_VALUES[1],
                EXIT_REPOSITION_VALUES[2],
                EXIT_REPOSITION_VALUES[3]);
        exitButton.create();
    }

    public static void render(GuiRenderer renderer) {
        renderer.renderObject(startButton);
        renderer.renderObject(exitButton);
    }

    public static void resize(Window window) {
        startButton.reposition(window.getxSpan(), window.getySpan());
    }

    private static void setBackgroundColour(Vector3f colour, Window window) {
        window.setBackgroundColour(colour.getX(), colour.getY(), colour.getZ(), 1f);
    }

    private static GuiObject initButton(Window window, float xEdge, float xOffset, float yEdge, float yOffset) {
        Mesh tempMesh = new Mesh(
                new Vertex2D[]{TOP_LEFT_VERTEX, TOP_RIGHT_VERTEX, BOTTOM_LEFT_VERTEX, BOTTOM_RIGHT_VERTEX},
                BUTTON_INDICES,
                new Material(BUTTON_TEXTURE));

        return new GuiObject(
                new Vector2f(xEdge * window.getxSpan() + xOffset, yEdge * window.getySpan() + yOffset),
                new Vector2f(1, 1),
                tempMesh,
                xEdge,
                xOffset,
                yEdge,
                yOffset);
    }
}