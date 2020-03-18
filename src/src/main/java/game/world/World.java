package game.world;

import engine.graphics.mesh.twoDimensional.RectangleMesh;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.objects.world.TileWorldObject;
import java.util.ArrayList;
import map.MapGeneration;
import math.Vector2f;
import math.Vector3f;

public class World {
  private static final float LOWER_VERTEX_BAND = -0.5f;
  private static final float UPPER_VERTEX_BAND = 0.5f;
  private static final float DEFAULT_Z = 0;
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_SCALE = new Vector3f(1f, 1f, 1f);
  private static TileWorldObject[][] worldMap;
  private static ArrayList<TileWorldObject> tiles = new ArrayList<>();

  /**
   * Create.
   *
   * @param camera the camera
   */
  public static void create(Camera camera) {
    MapGeneration.createMap();
    // reset the camera to its default position.
    camera.reset();
    // unfreeze the camera in-case it has been frozen before
    camera.unfreeze();
    createObjects(camera);
  }

  public static void render(WorldRenderer renderer, Camera camera) {
    renderTiles(renderer, camera);
  }

  public static void update() {

  }

  public static TileWorldObject[][] getWorldMap() {
    return worldMap.clone();
  }

  private static void renderTiles(WorldRenderer renderer, Camera camera) {
    for (TileWorldObject tileWorldObject : tiles) {
      renderer.renderObject(tileWorldObject, camera);
    }
  }

  private static void createObjects(Camera camera) {
    // TODO REPLACE WITH A MENU TO CHOOSE THE ATRIBUTES FOR THE MAP
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
    for (int row = 0; row < MapGeneration.getMapSizeY(); row++) {
      for (int column = 0; column < MapGeneration.getMapSizeX(); column++) {
        // create a mesh for a tile
        RectangleMesh tileMesh = new RectangleMesh(tileSize, tileSize);
        // create a tileWorldObject
        TileWorldObject tempTileWorldObject = new TileWorldObject(
            new Vector3f(leftXEdge + (tileSize * (float) column),
                topYEdge - (tileSize * (float) row), DEFAULT_Z),
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
    // set camera borders
    camera.setCameraBorder(botLeft, topRight);
  }

  // function that returns Vector2f positions which are the centres of the tiles
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
}
