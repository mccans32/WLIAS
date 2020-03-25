package map;

import java.util.ArrayList;
import java.util.Collections;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.Tile;
import map.tiles.WaterTile;

/**
 * The type Map generator.
 */
public class MapGeneration {

  private static final int HORIZONTAL_WATER_PADDING = 2;
  private static final int VERTICAL_WATER_PADDING = 2;
  /**
   * The Land mass size x.
   */
  private static final int DEFAULT_LANDMASS_SIZE_X = 5;
  /**
   * The Land mass size y.
   */
  private static final int DEFAULT_LANDMASS_SIZE_Y = 5;
  /**
   * The Number of land masses.
   */
  private static final int DEFAULT_AMOUNT_ARID_TILES = 4;
  private static final int DEFAULT_AMOUNT_FERTILE_TILES = 4;
  private static final int DEFAULT_AMOUNT_WATER_TILES = 2;
  private static final int DEFAULT_AMOUNT_PLAIN_TILES = 15;
  /**
   * The List of tiles.
   */
  static final ArrayList<Tile> LIST_OF_TILES = new ArrayList<>();
  /**
   * The Map of ordered tiles.
   */
  static Tile[][] simulationMap;
  /**
   * The Map size x.
   */
  private static int mapSizeX;
  /**
   * The Map size y.
   */
  private static int mapSizeY;
  /**
   * The Land mass maps.
   */
  private static Tile[][] landMassMap;
  private static int landMassSizeX = DEFAULT_LANDMASS_SIZE_X;
  private static int landMassSizeY = DEFAULT_LANDMASS_SIZE_Y;
  private static int amountOfAridTiles = DEFAULT_AMOUNT_ARID_TILES;
  private static int amountOfFertileTiles = DEFAULT_AMOUNT_FERTILE_TILES;
  private static int amountOfWaterTiles = DEFAULT_AMOUNT_WATER_TILES;
  private static int amountOfPlainTiles = DEFAULT_AMOUNT_PLAIN_TILES;

  public static int getDefaultLandmassSizeX() {
    return DEFAULT_LANDMASS_SIZE_X;
  }

  public static int getDefaultLandmassSizeY() {
    return DEFAULT_LANDMASS_SIZE_Y;
  }

  public static int getDefaultAmountAridTiles() {
    return DEFAULT_AMOUNT_ARID_TILES;
  }

  public static int getDefaultAmountFertileTiles() {
    return DEFAULT_AMOUNT_FERTILE_TILES;
  }

  public static int getDefaultAmountWaterTiles() {
    return DEFAULT_AMOUNT_WATER_TILES;
  }

  public static int getDefaultAmountPlainTiles() {
    return DEFAULT_AMOUNT_PLAIN_TILES;
  }

  public static ArrayList<Tile> getListOfTiles() {
    return LIST_OF_TILES;
  }

  public static Tile[][] getlandMassMap() {
    return landMassMap.clone();
  }

  public static int getAmountOfAridTiles() {
    return amountOfAridTiles;
  }

  public static void setAmountOfAridTiles(int amountOfAridTiles) {
    MapGeneration.amountOfAridTiles = amountOfAridTiles;
  }

  public static int getAmountOfFertileTiles() {
    return amountOfFertileTiles;
  }

  public static void setAmountOfFertileTiles(int amountOfFertileTiles) {
    MapGeneration.amountOfFertileTiles = amountOfFertileTiles;
  }

  public static int getAmountOfWaterTiles() {
    return amountOfWaterTiles;
  }

  public static void setAmountOfWaterTiles(int amountOfWaterTiles) {
    MapGeneration.amountOfWaterTiles = amountOfWaterTiles;
  }

  public static int getAmountOfPlainTiles() {
    return amountOfPlainTiles;
  }

  public static void setAmountOfPlainTiles(int amountOfPlainTiles) {
    MapGeneration.amountOfPlainTiles = amountOfPlainTiles;
  }

  public static int getMapSizeX() {
    return mapSizeX;
  }

  public static void setMapSizeX(int mapSizeX) {
    MapGeneration.mapSizeX = mapSizeX;
  }

  public static int getMapSizeY() {
    return mapSizeY;
  }

  public static void setMapSizeY(int mapSizeY) {
    MapGeneration.mapSizeY = mapSizeY;
  }

  public static int getLandMassSizeX() {
    return landMassSizeX;
  }

  public static void setLandMassSizeX(int landMassSizeX) {
    MapGeneration.landMassSizeX = landMassSizeX;
  }

  public static int getLandMassSizeY() {
    return landMassSizeY;
  }

  public static void setLandMassSizeY(int landMassSizeY) {
    MapGeneration.landMassSizeY = landMassSizeY;
  }

  /**
   * Create map.
   * Generates landMass tiles in accordance to the tiles provided.
   * Shuffles them to create random terrain.
   * sets the simulationMap to the joining of the landMasses.
   */
  public static void createMap() {
    generateTiles();
    Collections.shuffle(LIST_OF_TILES);
    landMassMap = generateLandMass();
    setSimulationMap();
  }

  private static void generateTiles() {
    // stores the amount of tiles that are suppose to be in a land mass
    int reservedNumberOfTiles = landMassSizeX * landMassSizeY;
    // stores the amount of tiles that are provided to us.
    int actualNumberOfTiles = amountOfAridTiles + amountOfFertileTiles
        + amountOfPlainTiles + amountOfWaterTiles;
    // Check if the amounts are equal
    if ((reservedNumberOfTiles != actualNumberOfTiles)) {
      throw new AssertionError("Number of reserved Tiles does not equal the Actual "
          + "number of tiles provided.\nActual Tiles = " + actualNumberOfTiles + "\n"
          + "Reserved Tiles = " + reservedNumberOfTiles);
    }
    // initialise the tiles and add them to a single array
    for (int i = 0; i < amountOfAridTiles; i++) {
      Tile tile = new AridTile();
      LIST_OF_TILES.add(tile);
    }

    for (int i = 0; i < amountOfFertileTiles; i++) {
      Tile tile = new FertileTile();
      LIST_OF_TILES.add(tile);
    }

    for (int i = 0; i < amountOfPlainTiles; i++) {
      Tile tile = new PlainTile();
      LIST_OF_TILES.add(tile);
    }

    for (int i = 0; i < amountOfWaterTiles; i++) {
      Tile tile = new WaterTile();
      LIST_OF_TILES.add(tile);
    }

  }

  /**
   * Sets the mapSizeX.
   * Sets the mapSizeY.
   */
  public static void setSimulationMap() {
    /* This function adds all the previously made terrain onto a single canvas
    it does so by adding the terrain one by one to a newly constructed 2d array
    each landmass has the same dimensions so we know that each land mass will be
    a distance equal to the previous landmass length away from the leftmost edge of the 2d array
    to create some padding we also add a layer of water between each landmass */

    // Sets the overall size of the Canvas(Map)
    mapSizeX = landMassSizeX
        + HORIZONTAL_WATER_PADDING;
    mapSizeY = landMassSizeY + VERTICAL_WATER_PADDING;
    simulationMap = new Tile[mapSizeY][mapSizeX];

    /* Since its a 2d array we will consider each row to be y co-ord and each column to be x
    This means the top right corner is the point (y0,x0)
    y comes first as when accessing the array we will be accessing the row first
    and column second. */

    setUpPadding();
    setUpLandMass();

  }

  private static void setUpPadding() {
    // this for loop creates the outer ridge of water
    for (int y = 0; y < mapSizeY; y++) {
      for (int x = 0; x < mapSizeX; x++) {
        if (x == 0 | x == mapSizeX - 1 | y == 0 | y == mapSizeY - 1) {
          Tile edgeTile = new WaterTile();
          simulationMap[y][x] = edgeTile;
        }
      }
    }
  }

  private static void setUpLandMass() {
    // y position of the landmass
    int landMassYPos = 0;
    for (int y = 1; y < landMassSizeY + 1; y++) {
      // x position of the landmass
      int landMassXPos = 0;
      for (int x = HORIZONTAL_WATER_PADDING - 1;
           x < landMassSizeY + HORIZONTAL_WATER_PADDING - 1; x++) {
        /* places a tile from a landMass in the appropriate y,x co-ords
        the x co-ord is calculated depending on which land mass we're working with
        if were working with land mass 1 the x co-ord will be
        length of landmass * landMassPointer(0) + i
        i = (landMassPointer(0) + 1 (accommodate for water padding)) */
        simulationMap[y][x] = landMassMap[landMassYPos][landMassXPos];
        landMassXPos++;
      }
      landMassYPos++;
    }


  }

  /* Generates a list of LandMasses
  LandMass is a 2d array of tiles which represent the different land masses */
  private static Tile[][] generateLandMass() {
    int tileCounter = 0;
    Tile[][] currentLandMass = new Tile[landMassSizeX][landMassSizeY];
    for (int i = 0; i < landMassSizeY; i++) {
      for (int j = 0; j < landMassSizeX; j++) {
        currentLandMass[i][j] = LIST_OF_TILES.get(tileCounter);
        tileCounter++;
      }
    }
    return currentLandMass;

  }

  /**
   * Get map of ordered tiles tile [ ] [ ].
   *
   * @return The 2d Array representation of the map. Tile[][].
   */
  public static Tile[][] getSimulationMap() {
    return simulationMap.clone();
  }

  /**
   * Sets values.
   *
   * @param landMassSizeX        the land mass size x
   * @param landMassSizeY        the land mass size y
   * @param amountOfAridTiles    the amount of arid tiles
   * @param amountOfFertileTiles the amount of fertile tiles
   * @param amountOfPlainTiles   the amount of plain tiles
   * @param amountOfWaterTiles   the amount of water tiles
   */
  public static void setValues(int landMassSizeX, int landMassSizeY, int amountOfAridTiles,
                               int amountOfFertileTiles, int amountOfPlainTiles,
                               int amountOfWaterTiles) {
    setLandMassSizeX(landMassSizeX);
    setLandMassSizeY(landMassSizeY);
    setAmountOfAridTiles(amountOfAridTiles);
    setAmountOfFertileTiles(amountOfFertileTiles);
    setAmountOfPlainTiles(amountOfPlainTiles);
    setAmountOfWaterTiles(amountOfWaterTiles);
  }

  /**
   * Reset to default values.
   */
  public static void resetToDefaultValues() {
    setLandMassSizeX(DEFAULT_LANDMASS_SIZE_X);
    setLandMassSizeY(DEFAULT_LANDMASS_SIZE_Y);
    setAmountOfAridTiles(DEFAULT_AMOUNT_ARID_TILES);
    setAmountOfFertileTiles(DEFAULT_AMOUNT_FERTILE_TILES);
    setAmountOfPlainTiles(DEFAULT_AMOUNT_PLAIN_TILES);
    setAmountOfWaterTiles(DEFAULT_AMOUNT_WATER_TILES);

  }
}
