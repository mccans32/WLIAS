package game.world;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.objects.world.TileObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import java.util.ArrayList;
import map.tiles.FertileTile;
import map.tiles.Tile;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;

public class World {
  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());

  private static ArrayList<TileObject> tiles = new ArrayList<>();

  /**
   * Create.
   *
   * @param window the window
   * @param camera the camera
   */
  public static void create(Window window, Camera camera) {
    // reset the camera to its default position.
    camera.reset();
    // unfreeze the camera in-case it has been frozen before
    camera.unfreeze();
    setBackgroundColour(window);
    createObjects();
  }

  public static void render(WorldRenderer renderer, Camera camera) {
    renderTiles(renderer, camera);
  }

  public static void update() {

  }

  private static void renderTiles(WorldRenderer renderer, Camera camera) {
    for (TileObject tileObject : tiles) {
      renderer.renderObject(tileObject, camera);
    }
  }

  private static void setBackgroundColour(Window window) {
    window.setBackgroundColour(
        BACKGROUND_COLOUR.getX(),
        BACKGROUND_COLOUR.getY(),
        BACKGROUND_COLOUR.getZ(),
        1f);
  }

  private static void createObjects() {
    createTileObjects();
  }

  // TODO ADD MAP TO CONTRUCTOR
  private static void createTileObjects() {
    // TODO READ EACH TILE FORM MAP AND CREATE CORRESPONDINF TILE OBJECT
    createTestObject();
  }

  //TODO REMOVE
  private static void createTestObject() {
    // Temp tile
    Tile tempTile = new FertileTile();
    // Temporary Mesh
    Mesh tempMesh = new Mesh(
        new Vertex3D[] {
            new Vertex3D(new Vector3f(-0.5f, 0.5f, 0),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)),
            new Vertex3D(new Vector3f(-0.5f, -0.5f, 0),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)),
            new Vertex3D(new Vector3f(0.5f, -0.5f, 0),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)),
            new Vertex3D(new Vector3f(0.5f, 0.5f, 0),
                ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f))
        },
        new int[] {0, 3, 1, 2});
    TileObject tempTileObject = new TileObject(
        new Vector3f(0, 0, 0f),
        new Vector3f(0, 0, 0),
        new Vector3f(1f, 1f, 1f),
        tempMesh,
        tempTile);

    // Create the Object
    tempTileObject.create();
    // Add to Current Tile List
    tiles.add(tempTileObject);
  }
}
