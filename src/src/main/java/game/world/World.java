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
  private static final int BORDER_CUT_OFF = 3;
  private static final float LOWER_VERTEX_BAND = -0.5f;
  private static final float UPPER_VERTEX_BAND = 0.5f;
  private static final float DEFAULT_Z = 0;
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
    createObjects(camera);
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

  private static void createObjects(Camera camera) {
    // TODO REPLACE WITH A MENU TO CHOSE THE ATRIBUTES FOR THE MAP
    MapGenerator map = new MapGenerator(20, 20, 100, 200, 0, 100, 1);
    map.createMap();
    float tileSize = UPPER_VERTEX_BAND + Math.abs(LOWER_VERTEX_BAND);
    createTileObjects(map, tileSize, camera);
  }

  private static void createTileObjects(MapGenerator map, float tileSize, Camera camera) {
    float centreX = calcCentrePos(map.getLandMassSizeX(), tileSize);
    float centreY = calcCentrePos(map.getLandMassSizeY(), tileSize);
    Vector3f botLeft = new Vector3f(0, 0, 0);
    Vector3f topRight = new Vector3f(0, 0, 0);
    List<Vertex3D> vertexList = new ArrayList<Vertex3D>();
    Tile[][] mapRepresentation = map.getSimulationMap();
    for (int row = 0; row < map.getMapSizeY(); row++) {
      for (int column = 0; column < map.getMapSizeX(); column++) {
        addToVertexList(vertexList);
        Mesh tempMesh = new Mesh(ListToArray.listVertex3DToVertex3DArray(vertexList),
            new int[] {0, 1, 2, 3});
        TileWorldObject tempTileWorldObject = new TileWorldObject(
            new Vector3f(centreX + (tileSize * (float) column),
                centreY + (tileSize * (float) row), 0f),
            new Vector3f(0, 0, 0),
            new Vector3f(1f, 1f, 1f),
            tempMesh,
            mapRepresentation[row][column]);
        if (row == 0 && column == 0) {
          botLeft = new Vector3f(centreX + (tileSize * (float) column),
              centreY + (tileSize * (float) row), 0f);
        }
        if ((row + 1) == map.getMapSizeY() && (column + 1) == map.getMapSizeX()) {
          topRight = new Vector3f(centreX + (tileSize * (float) column),
              centreY + (tileSize * (float) row), 0f);
        }
        // Create the Object
        tempTileWorldObject.create();
        // Add to Current Tile List
        tiles.add(tempTileWorldObject);
      }
    }
    camera.setCameraBorder(botLeft, topRight);
  }

  private static float calcCentrePos(int mapDimension, float tileSize) {
    float value = 0;
    for (int i = 0; i < mapDimension / 2; i++) {
      value -= tileSize;
    }
    return value;
  }

  private static void addToVertexList(List<Vertex3D> vertexList) {
    Vector3f topRightCoordinates = new Vector3f(UPPER_VERTEX_BAND, UPPER_VERTEX_BAND, DEFAULT_Z);
    Vector3f topLeftCoordinates = new Vector3f(LOWER_VERTEX_BAND, UPPER_VERTEX_BAND, DEFAULT_Z);
    Vector3f botRightCoordinates = new Vector3f(UPPER_VERTEX_BAND, LOWER_VERTEX_BAND, DEFAULT_Z);
    Vector3f botLeftCoordinates = new Vector3f(LOWER_VERTEX_BAND, LOWER_VERTEX_BAND, DEFAULT_Z);
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
