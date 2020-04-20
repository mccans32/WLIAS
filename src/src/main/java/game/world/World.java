package game.world;

import engine.Window;
import engine.audio.AudioMaster;
import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import engine.graphics.renderer.WorldRenderer;
import engine.io.Input;
import engine.objects.world.Camera;
import engine.objects.world.GameObject;
import engine.objects.world.TileWorldObject;
import engine.tools.MousePicker;
import engine.utils.ArrayUtils;
import engine.utils.ColourUtils;
import game.Game;
import game.GameState;
import game.Moves;
import game.menu.ChoiceMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import map.MapGeneration;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.Tile;
import map.tiles.WaterTile;
import math.Vector2f;
import math.Vector3f;
import math.Vector4f;
import org.jfree.chart.ChartColor;
import org.lwjgl.glfw.GLFW;
import society.Society;

public class World {
  private static final float LOWER_VERTEX_BAND = -0.5f;
  private static final float UPPER_VERTEX_BAND = 0.5f;
  private static final float DEFAULT_Z = 0;
  private static final Vector3f DEFAULT_ROTATION = new Vector3f(0, 0, 0);
  private static final Vector3f DEFAULT_SCALE = new Vector3f(1f, 1f, 1f);
  private static final int DEFAULT_NUMBER_OF_SOCIETIES = 4;
  private static final Vector3f[] BASIC_SOCIETY_COLORS = new Vector3f[] {
      ColourUtils.convertColor(ChartColor.RED),
      ColourUtils.convertColor(ChartColor.LIGHT_YELLOW),
      ColourUtils.convertColor(ChartColor.LIGHT_GREEN),
      ColourUtils.convertColor(ChartColor.LIGHT_CYAN)};
  private static final int FERTILE_MIN_FOOD_RESOURCE = 3;
  private static final int FERTILE_MAX_FOOD_RESOURCE = 5;
  private static final int FERTILE_MAX_RAW_MATERIALS = 1;
  private static final int ARID_MAX_FOOD_RESOURCE = 1;
  private static final int ARID_MAX_RAW_MATERIALS = 5;
  private static final int ARID_MIN_RAW_MATERIALS = 3;
  private static final int PLAIN_MAX_FOOD_RESOURCE = 3;
  private static final int PLAIN_MAX_RAW_MATERIALS = 2;
  private static final int WATER_MAX_FOOD_RESOURCE = 1;
  private static final int WATER_MAX_RAW_MATERIALS = 1;
  private static TileWorldObject[][] worldMap;
  private static ArrayList<GameObject> fertileTiles = new ArrayList<>();
  private static ArrayList<GameObject> aridTiles = new ArrayList<>();
  private static ArrayList<GameObject> plainTiles = new ArrayList<>();
  private static ArrayList<GameObject> waterTiles = new ArrayList<>();
  private static GameObject selectOverlay;
  private static Image selectOverlayImage = new Image("/images/blankFace.png");
  private static Vector4f overlayColour = new Vector4f(new Vector3f(1, 1, 1), 0.5f);
  private static Material selectOverlayMaterial = new Material(selectOverlayImage, overlayColour);
  private static boolean bordersAltered = false;
  private static Society[] societies = new Society[] {};
  private static ArrayList<Society> activeSocieties = new ArrayList<>();
  private static RectangleModel tileModel;
  private static TileWorldObject attackingTile;
  private static TileWorldObject opponentTile;
  private static TileWorldObject claimedTile;
  private static Society activeSociety;

  public static RectangleModel getTileModel() {
    return tileModel;
  }

  public static Society[] getSocieties() {
    return societies.clone();
  }

  /**
   * Create.
   *
   * @param camera the camera
   */
  public static void create(Window window, Camera camera) {
    create(window, camera, DEFAULT_NUMBER_OF_SOCIETIES);
  }

  /**
   * Create.
   *
   * @param window            the window
   * @param camera            the camera
   * @param numberOfSocieties the number of societies
   */
  public static void create(Window window, Camera camera, int numberOfSocieties) {
    MapGeneration.createMap();
    // reset the camera to its default position.
    camera.reset();
    // unfreeze the camera in-case it has been frozen before
    camera.unfreeze();
    createObjects(camera);
    // create the Mouse Picker
    MousePicker.setCamera(camera);
    MousePicker.setProjectionMatrix(window.getProjectionMatrix());
    MousePicker.setGroundZ(DEFAULT_Z);
    generateSocieties(numberOfSocieties);
  }

  private static void generateSocieties(int numberOfSocieties) {
    societies = new Society[numberOfSocieties];
    activeSocieties.clear();
    for (int i = 0; i < numberOfSocieties; i++) {
      Society society = new Society(i, BASIC_SOCIETY_COLORS[i]);
      societies[i] = society;
      activeSocieties.add(society);
      boolean claimed = false;
      while (!claimed) {
        int row = genRandomInt(worldMap[0].length - 2, 1);
        int column = genRandomInt(worldMap[0].length - 2, 1);
        if (!worldMap[row][column].isClaimed()
            && !(worldMap[row][column].getTile() instanceof WaterTile)) {
          activeSocieties.get(i).claimTile(worldMap[row][column]);
          bordersAltered = true;
          claimed = true;
        }
      }
    }
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
    if (MousePicker.getCurrentSelected() != null && !window.isMouseLocked()) {
      renderer.renderSelectOverlay(selectOverlay, camera);
    }
    renderBorder(renderer, camera);
  }

  private static void renderBorder(WorldRenderer renderer, Camera camera) {
    ArrayList<GameObject> temp = new ArrayList<>();
    for (Society society : activeSocieties) {
      for (TileWorldObject worldTile : society.getTerritory()) {
        temp.add(worldTile.getBorderObject());
      }
    }
    renderer.renderTileBorders(temp, camera);
  }

  public static TileWorldObject getAttackingTile() {
    return attackingTile;
  }

  public static TileWorldObject getOpponentTile() {
    return opponentTile;
  }

  /**
   * Update the world including the openAl listener.
   *
   * @param window the window
   */
  public static void update(Window window, Camera camera) {
    AudioMaster.setListener(camera.getPosition());
    updateBorders(window);
    if (Game.getState() == GameState.WARRING) {
      selectWarTiles(window);
    } else if (Game.getState() == GameState.CLAIM_TILE) {
      selectClaimableTile(window);
    }
  }

  private static void selectClaimableTile(Window window) {
    if (claimedTile == null) {
      MousePicker.update(window, societies[0].getClaimableTerritory());
      updateSelectOverlay();
      claimedTile = selectWorldTile(societies[0].getClaimableTerritory());
    } else {
      societies[0].claimTile(claimedTile);
      bordersAltered = true;
      updateSocietyBorders();
      claimedTile = null;
      societies[0].setEndTurn(true);
      Game.setState(GameState.GAME_MAIN);
    }
  }

  private static void selectWarTiles(Window window) {
    if (attackingTile == null) {
      MousePicker.update(window, societies[0].getSocietyWarringTiles());
      updateSelectOverlay();
      attackingTile = selectWorldTile(societies[0].getSocietyWarringTiles());
    } else if (opponentTile == null) {
      MousePicker.update(window, societies[0].getOpponentWarringTiles());
      updateSelectOverlay();
      opponentTile = selectWorldTile(societies[0].getOpponentWarringTiles());
    } else {
      simulateBattle(societies[0], attackingTile, opponentTile);
      attackingTile = null;
      opponentTile = null;
    }
  }

  private static TileWorldObject selectWorldTile(ArrayList<TileWorldObject> worldTiles) {
    if (MousePicker.getCurrentSelected() != null
        && Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)
        && worldTiles.contains(MousePicker.getCurrentSelected())
        && Game.canClick()) {
      Game.resetButtonLock();
      return MousePicker.getCurrentSelected();
    } else {
      return null;
    }
  }

  /**
   * Update the world.
   *
   * @param window the window
   */
  public static void updateBorders(Window window) {
    if (worldMap != null) {
      updateSocietyBorders();
      MousePicker.update(window, worldMap);
      updateSelectOverlay();
    }
  }

  private static void updateSocietyBorders() {
    if (bordersAltered) {
      for (Society society : societies) {
        society.updateBorders(tileModel);
      }
      bordersAltered = false;
    }
  }

  private static void updateSelectOverlay() {
    // Check if the picker is currently selecting an object and if it is, update it's position
    if (MousePicker.getCurrentSelected() != null) {
      // reposition selectOverlay to tile's position
      selectOverlay.setPosition(MousePicker.getCurrentSelected().getPosition().copy());
    }
  }

  public static TileWorldObject[][] getWorldMap() {
    return worldMap.clone();
  }

  private static void renderTiles(WorldRenderer renderer, Camera camera) {
    renderer.renderTiles(fertileTiles, camera);
    renderer.renderTiles(aridTiles, camera);
    renderer.renderTiles(plainTiles, camera);
    renderer.renderTiles(waterTiles, camera);
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
    tileModel = new RectangleModel(tileSize, tileSize);
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
            tileMesh, tile, row, column);
        generateResources(tempTileWorldObject);
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

  /**
   * Generate resources.
   *
   * @param tempTileWorldObject A TileWorldObject
   */
  public static void generateResources(TileWorldObject tempTileWorldObject) {
    if (tempTileWorldObject.getTile() instanceof WaterTile) {
      tempTileWorldObject.setFoodResource(genRandomInt(WATER_MAX_FOOD_RESOURCE));
      tempTileWorldObject.setRawMaterialResource(WATER_MAX_RAW_MATERIALS);
    } else if (tempTileWorldObject.getTile() instanceof FertileTile) {
      tempTileWorldObject.setFoodResource(genRandomInt(FERTILE_MAX_FOOD_RESOURCE,
          FERTILE_MIN_FOOD_RESOURCE));
      tempTileWorldObject.setRawMaterialResource(genRandomInt(FERTILE_MAX_RAW_MATERIALS));
    } else if (tempTileWorldObject.getTile() instanceof AridTile) {
      tempTileWorldObject.setFoodResource(genRandomInt(ARID_MAX_FOOD_RESOURCE));
      tempTileWorldObject.setRawMaterialResource(genRandomInt(ARID_MAX_RAW_MATERIALS,
          ARID_MIN_RAW_MATERIALS));
    } else if (tempTileWorldObject.getTile() instanceof PlainTile) {
      tempTileWorldObject.setFoodResource(genRandomInt(PLAIN_MAX_FOOD_RESOURCE));
      tempTileWorldObject.setRawMaterialResource(genRandomInt(PLAIN_MAX_RAW_MATERIALS));
    }
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

  public static int genRandomInt(int maxValue) {
    Random r = new Random();
    return r.nextInt(maxValue);
  }

  /**
   * Generate a random integer.
   *
   * @param maxValue the max value
   * @param minValue the min value
   * @return the int
   */
  public static int genRandomInt(int maxValue, int minValue) {
    Random r = new Random();
    int val = r.nextInt(maxValue + 1);
    return (val == minValue ? minValue : Math.max(val, minValue));
  }

  public static int getDefaultNumberOfSocieties() {
    return DEFAULT_NUMBER_OF_SOCIETIES;
  }

  /**
   * Destroy the world elements and clear the arrays.
   */
  public static void destroy() {
    // Destroy tile data
    for (TileWorldObject[] row : worldMap) {
      for (TileWorldObject tile : row) {
        GameObject border = tile.getBorderObject();
        if (border != null) {
          border.destroy();
        }
        tile.destroy();
      }
    }
    worldMap = null;
    fertileTiles.clear();
    waterTiles.clear();
    aridTiles.clear();
    plainTiles.clear();
    selectOverlay.destroy();
    // Destroy Overlay
    selectOverlay.destroy();
    ChoiceMenu.destroy();
    activeSocieties.clear();
  }

  private static void simulateBattle(Society playerSociety,
                                     TileWorldObject playerTile, TileWorldObject opponentTile) {
    Society warTarget = null;
    for (Society society : societies) {
      if (society.getTerritory().contains(opponentTile)) {
        warTarget = society;
      }
    }
    if (warTarget != null) {
      float playerAttack = calcAttack(playerSociety, playerTile);
      float opponentAttack = calcAttack(warTarget, opponentTile);
      if (playerAttack > opponentAttack) {
        warTarget.getTerritory().remove(opponentTile);
        playerSociety.claimTile(opponentTile);
        bordersAltered = true;
      } else if (playerAttack < opponentAttack) {
        playerSociety.getTerritory().remove(playerTile);
        warTarget.claimTile(playerTile);
        bordersAltered = true;
      }
    }
    playerSociety.setEndTurn(true);
    purgeSocieties();
    Game.setState(GameState.GAME_MAIN);
  }

  private static void purgeSocieties() {
    List<Society> societiesToRemove = new ArrayList<>();
    // Find Societies to remove if any
    for (Society society : activeSocieties) {
      if (society.getTerritory().size() < 1) {
        societiesToRemove.add(society);
      }
    }
    // Remove the societies
    for (Society society : societiesToRemove) {
      activeSocieties.remove(society);
    }
  }

  private static float calcAttack(Society currentSociety, TileWorldObject worldTile) {
    // TODO NORMALISE THESE VALUES FOR THE PURPOSE OF BALANCING
    float populationModifier = currentSociety.getPopulation().size();
    float productionModifier = currentSociety.getAverageProductivity();
    float aggressivenessModifier = currentSociety.getAverageAggressiveness();
    float tileModifier = worldTile.getTile().getAttackModifier();
    return populationModifier + productionModifier + aggressivenessModifier + tileModifier;
  }

  public static Society getActiveSociety() {
    return activeSociety;
  }

  public static void setActiveSociety(Society activeSociety) {
    World.activeSociety = activeSociety;
  }

  /**
   * The turn calculations needed for the Ai Societies to ake their turns.
   *
   * @param society the society
   */
  // TODO GET RID OF THIS FUNCTION OR REFACTOR IT WHEN AI LOGIC IS IMPLEMENTED
  public static void aiTurn(Society society) {
    if (!society.hasMadeMove()) {
      society.calculateClaimableTerritory();
      if (!society.getClaimableTerritory().isEmpty()) {
        // calculate Most Needed Tile
        TileWorldObject claimTile = calculateClaimTile(society);
        society.claimTile(claimTile);
        bordersAltered = true;
        updateSocietyBorders();
        society.setMadeMove(true);
        Game.setState(GameState.AI_CLAIM);
        society.setLastMove(Moves.ClaimTile);
      } else {
        Game.setState(GameState.AI_NOTHING);
        society.setLastMove(Moves.Nothing);
        society.setMadeMove(true);
      }
      Game.getNotificationTimer().setDuration(2);
    }
    if (Game.getNotificationTimer().isDurationMet()) {
      Game.getNotificationTimer().clearDuration();
      society.setEndTurn(true);
      Game.setState(GameState.GAME_MAIN);
    }
  }

  private static TileWorldObject calculateClaimTile(Society society) {
    ArrayList<TileWorldObject> claimable = society.getClaimableTerritory();
    float[] scores = new float[claimable.size()];
    // Get the score for each tile
    for (int i = 0; i < claimable.size(); i++) {
      float score = calculateTileClaimScore(society, claimable.get(i));
      scores[i] = score;
    }
    // Get the highest score
    float highest = ArrayUtils.max(scores);
    int index = ArrayUtils.indexOf(scores, highest);
    return claimable.get(index);
  }

  private static float calculateTileClaimScore(Society society, TileWorldObject tile) {
    // Decides what tile the society would most want
    //Dictated by how much food they require, how much raw material they require;
    float foodWeight = (society.getPopulation().size() * Society.getFoodPerPerson())
        / (society.getTotalFoodResource() + 1);
    float materialWeight = (society.getPopulation().size() * Society.getMaterialPerPerson())
        / (society.getTotalRawMaterialResource() + 1);
    float tileAttackWeight = -(society.getAverageAge() / 100);

    return (foodWeight * tile.getFoodResource())
        + (materialWeight * tile.getRawMaterialResource())
        + (tileAttackWeight * tile.getTile().getAttackModifier());
  }

  public static ArrayList<Society> getActiveSocieties() {
    return activeSocieties;
  }
}
