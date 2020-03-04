package game.world;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import engine.utils.ColourUtils;
import engine.utils.ListToArray;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import map.MapGenerator;
import map.tiles.Tile;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;

public class World {
  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());

  private static ArrayList<TileWorldObject> tiles = new ArrayList<>();

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
    for (TileWorldObject tileWorldObject : tiles) {
      renderer.renderObject(tileWorldObject, camera);
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
    MapGenerator map = new MapGenerator(2, 2, 1, 2, 0, 1, 1);
    map.createMap();
    createTileObjects(map);
  }

  private static void createTileObjects(MapGenerator map) {
//    for (Tile[] tile : map.getSimulationMap()) {
//      System.out.println(Arrays.toString(tile));
//    }
    float centreX = calcCentrePos(map.getLandMassSizeX());
    float centreY = calcCentrePos(map.getLandMassSizeY());
    List<Vertex3D> vertexList = new ArrayList<Vertex3D>();
    Tile[][] mapRepresentation = map.getSimulationMap();
    for (int row = 0; row < map.getMapSizeY(); row++) {
      for (int column = 0; column < map.getMapSizeX(); column++) {
        addToVertexList(mapRepresentation[row][column], row, column, vertexList);
        Mesh tempMesh = new Mesh(ListToArray.ListVertex3DToVertex3DArray(vertexList), new int[] {0, 1, 2, 3});
        TileWorldObject tempTileWorldObject = new TileWorldObject(
            new Vector3f(0 + (1 * (float) column), 0 + (1 * (float) row), 0f),
            new Vector3f(0, 0, 0),
            new Vector3f(1f, 1f, 1f),
            tempMesh,
            mapRepresentation[row][column]);
        // Create the Object
        tempTileWorldObject.create();
        // Add to Current Tile List
        tiles.add(tempTileWorldObject);
      }
    }
  }

  private static float calcCentrePos(int mapSize) {
    return 0;
  }

  private static void addToVertexList(Tile currentTile, int row, int column, List<Vertex3D> vertexList) {
    Vector3f topRightCoordinates = new Vector3f(0.5f, 0.5f, 0);
    Vector3f topLeftCoordinates = new Vector3f(-0.5f, 0.5f, 0);
    Vector3f botRightCoordinates = new Vector3f(0.5f, -0.5f, 0);
    Vector3f botLeftCoordinates = new Vector3f(-0.5f, -0.5f, 0);
    vertexList.add(new Vertex3D(topLeftCoordinates, // top left
        ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 0f)));
    vertexList.add(new Vertex3D(topRightCoordinates, // top right
        ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 0f)));
    vertexList.add(new Vertex3D(botLeftCoordinates, // bot left
        ColourUtils.convertColor(Color.WHITE), new Vector2f(0f, 1f)));
    vertexList.add(new Vertex3D(botRightCoordinates, // bot right
        ColourUtils.convertColor(Color.WHITE), new Vector2f(1f, 1f)));
  }
}
