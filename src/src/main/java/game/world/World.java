package game.world;

import engine.Window;
import engine.graphics.Mesh;
import engine.graphics.Vertex3D;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import engine.utils.ColourUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import map.MapGeneration;
import map.tiles.Tile;
import math.Vector2f;
import math.Vector3f;
import org.jfree.chart.ChartColor;

public class World {
  private static final float LOWER_VERTEX_BAND = -0.5f;
  private static final float UPPER_VERTEX_BAND = 0.5f;
  private static final float DEFAULT_Z = 0;
  private static final int[] DEFAULT_INDICES = new int[] {0, 1, 2, 3};
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_SCALE = new Vector3f(1f, 1f, 1f);
  private static final Vector3f BACKGROUND_COLOUR = ColourUtils.convertColor(
      ChartColor.VERY_LIGHT_CYAN.brighter());
  private static TileWorldObject[][] worldMap;
  private static ArrayList<TileWorldObject> tiles = new ArrayList<>();

  public static TileWorldObject[][] getWorldMap() {
    return worldMap.clone();
  }

  /**
   * Create.
   *
   * @param window the window
   * @param camera the camera
   */
  public static void create(Window window, Camera camera) {
    MapGeneration.createMap();
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
    // calculate tile size based on vertex values
    float tileSize = UPPER_VERTEX_BAND + Math.abs(LOWER_VERTEX_BAND);
    createTileObjects(tileSize, camera);
  }

  private static void createTileObjects(float tileSize, Camera camera) {
    // left edge = the position of the first tile in the X axis. Starting left most edge
    float leftXEdge = calcLeftPos(MapGeneration.getLandMassSizeX(), tileSize);
    // top edge = the position of the first tile in the Y axis. Starting at the top most edge
    float topYEdge = calcTopPos(MapGeneration.getLandMassSizeY(), tileSize);
    // initialise 2d representation of the map

    worldMap = new TileWorldObject[MapGeneration.getMapSizeX()][MapGeneration.getMapSizeY()];
    List<Vertex3D> vertexList = new ArrayList<Vertex3D>();
    for (Tile[] tiles : MapGeneration.getSimulationMap()) {
      System.out.println(Arrays.toString(tiles));
    }
    for (int row = 0; row < MapGeneration.getMapSizeY(); row++) {
      for (int column = 0; column < MapGeneration.getMapSizeX(); column++) {
        // calculates the vertex positions and adds them to a List
        addToVertexList(vertexList);
        // create a a mesh for a tile
        Mesh tileMesh = new Mesh(Vertex3D.listToArray(vertexList), DEFAULT_INDICES);
        // create a tileWorldObject
        TileWorldObject tempTileWorldObject = new TileWorldObject(
            new Vector3f(leftXEdge + (tileSize * (float) column),
                topYEdge - (tileSize * (float) row), 0f),
            DEFAULT_ROTATION, DEFAULT_SCALE, tileMesh,
            MapGeneration.getSimulationMap()[row][column]);
        // assign tile ot the world map
        worldMap[row][column] = tempTileWorldObject;
        // Create the Object
        tempTileWorldObject.create();
        // Add to Current Tile List
        tiles.add(tempTileWorldObject);
      }
    }
    // calculate the positions for the camera borders based on tiles in appropriate corners
    Vector2f botLeft = calcCentre(worldMap[worldMap.length - 1][0]);
    Vector2f topRight = calcCentre(worldMap[0][worldMap.length - 1]);
    System.out.println(botLeft + ", " + topRight);
    // set camera borders
    camera.setCameraBorder(botLeft, topRight);
  }

  // function that returns the a Vector2f positions which is centre of a tile
  private static Vector2f calcCentre(TileWorldObject tile) {
    return new Vector2f(tile.getPosition().getX(), tile.getPosition().getY());
  }

  // calculates the left most position of the first tile
  private static float calcLeftPos(int mapDimension, float tileSize) {
    float value = 0;
    for (int i = 0; i < mapDimension / 2; i++) {
      value -= tileSize;
    }
    return value;
  }

  // calculates the top most position of the first tile
  private static float calcTopPos(int mapDimension, float tileSize) {
    float value = 0;
    for (int i = 0; i < mapDimension / 2; i++) {
      value += tileSize;
    }
    return value;
  }

  // creates the Vectors for a tile and assigns them to a list.
  protected static void addToVertexList(List<Vertex3D> vertexList) {
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
