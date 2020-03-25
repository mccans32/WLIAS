package game.world;

import engine.Window;
import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.graphics.renderer.WorldRenderer;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.objects.world.TileWorldObject;
import engine.tools.MousePicker;
import engine.utils.ColourUtils;
import java.util.ArrayList;
import map.MapGeneration;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.Tile;
import map.tiles.WaterTile;
import math.Vector2f;
import math.Vector3f;

public class World {
  private static final float LOWER_VERTEX_BAND = -0.5f;
  private static final float UPPER_VERTEX_BAND = 0.5f;
  private static final float DEFAULT_Z = 0;
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_SCALE = new Vector3f(1f, 1f, 1f);
  private static TileWorldObject[][] worldMap;
  private static ArrayList<GameObject> fertileTiles = new ArrayList<>();
  private static ArrayList<GameObject> aridTiles = new ArrayList<>();
  private static ArrayList<GameObject> plainTiles = new ArrayList<>();
  private static ArrayList<GameObject> waterTiles = new ArrayList<>();
  private static MousePicker mousePicker;
  private static GameObject selectOverlay;
  private static Image selectOverlayImage = new Image("/images/blankFace.png");
  private static Material selectOverlayMaterial = new Material(selectOverlayImage);

  /**
   * Create.
   *
   * @param camera the camera
   */
  public static void create(Camera camera, Window window) {
    MapGeneration.createMap();
    // reset the camera to its default position.
    camera.reset();
    // unfreeze the camera in-case it has been frozen before
    camera.unfreeze();
    createObjects(camera);
    // create the Mouse Picker
    mousePicker = new MousePicker(camera, window.getProjectionMatrix(), DEFAULT_Z);
  }

  /**
   * Render the game tiles and render the select overlay if mouse is not locked.
   *
   * @param renderer the renderer
   * @param camera   the camera
   * @param window   the window
   */
  public static void render(WorldRenderer renderer, Camera camera, Window window) {
    renderTiles(renderer, camera);
    if (mousePicker.getCurrentSelected() != null && !window.isMouseLocked()) {
      renderer.renderObject(selectOverlay, camera);
    }
  }

  public static void update(Window window) {
    mousePicker.update(window, worldMap);
    updateSelectOverlay();
  }

  private static void updateSelectOverlay() {
    // Check if the picker is currently selecting an object and if it is, update it's position
    if (mousePicker.getCurrentSelected() != null) {
      // reposition selectOverlay to tile's position
      selectOverlay.setPosition(mousePicker.getCurrentSelected().getPosition().copy());
      // Set Z Value Slightly Higher so it renders on top of tile.
      selectOverlay.getPosition().setZ(DEFAULT_Z + 0.001f);
    }
  }

  public static TileWorldObject[][] getWorldMap() {
    return worldMap.clone();
  }

  private static void renderTiles(WorldRenderer renderer, Camera camera) {
    renderer.renderObjects(fertileTiles, camera);
    renderer.renderObjects(aridTiles, camera);
    renderer.renderObjects(plainTiles, camera);
    renderer.renderObjects(waterTiles, camera);
  }

  private static void createObjects(Camera camera) {
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
    RectangleModel tileModel = new RectangleModel(tileSize, tileSize);
    // create the select overlay
    selectOverlay = new GameObject(new RectangleMesh(tileModel, selectOverlayMaterial));
    selectOverlay.create();
    worldMap = new TileWorldObject[MapGeneration.getMapSizeX()][MapGeneration.getMapSizeY()];
    Tile[][] tileMap = MapGeneration.getSimulationMap();
    for (int row = 0; row < MapGeneration.getMapSizeY(); row++) {
      for (int column = 0; column < MapGeneration.getMapSizeX(); column++) {
        // create a tileWorldObject
        RectangleMesh tileMesh = new RectangleMesh(tileModel);
        Tile tile = tileMap[row][column];
        TileWorldObject tempTileWorldObject = new TileWorldObject(
            new Vector3f(leftXEdge + (tileSize * (float) column),
                topYEdge - (tileSize * (float) row), DEFAULT_Z), DEFAULT_ROTATION, DEFAULT_SCALE,
            tileMesh, tile);
        // assign tile ot the world map
        worldMap[row][column] = tempTileWorldObject;
        // Create the Object
        tempTileWorldObject.create();
        // Add to Current Tile List
        addTileObject(tempTileWorldObject);
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

  private static void addTileObject(TileWorldObject object) {
    Tile tile = object.getTile();
    if (tile instanceof FertileTile) {
      fertileTiles.add(object);
    } else if (tile instanceof AridTile) {
      aridTiles.add(object);
    } else if (tile instanceof WaterTile) {
      waterTiles.add(object);
    } else {
      plainTiles.add(object);
    }
  }
}
