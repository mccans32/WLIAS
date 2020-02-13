package map;

import java.util.ArrayList;
import java.util.Collections;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.WaterTile;

/**
 * The type Map generator.
 */
public class MapGenerator {

  /**
   * The Land mass size x.
   */
  final int landMassSizeX;
  /**
   * The Land mass size y.
   */
  final int landMassSizeY;
  /**
   * The Number of land masses.
   */
  final int numberOfLandMasses;
  private final int amountOfAridTiles;
  private final int amountOfFertileTiles;
  private final int amountOfWaterTiles;
  private final int amountOfPlainTiles;
  /**
   * The Map size x.
   */
  public int mapSizeX;
  /**
   * The Map size y.
   */
  public int mapSizeY;
  /**
   * The List of tiles.
   */
  public ArrayList<Tile> listOfTiles = new ArrayList<Tile>();
  /**
   * The Map of ordered tiles.
   */
  public Tile[][] simulationMap;
  /**
   * The Land mass maps.
   */
  public ArrayList<Tile[][]> landMassMaps = new ArrayList<Tile[][]>();
  /**
   * Instantiates a new Map generator.
   *
   * @param landMassSizeX        the land mass size x
   * @param landMassSizeY        the land mass size y
   * @param amountOfAridTiles    the amount of arid tiles
   * @param amountOfFertileTiles the amount of fertile tiles
   * @param amountOfWaterTiles   the amount of water tiles
   * @param amountOfPlainTiles   the amount of plain tiles
   * @param numberOfLandMasses   the number of land masses
   */
  public MapGenerator(int landMassSizeX, int landMassSizeY, int amountOfAridTiles,
                      int amountOfFertileTiles, int amountOfWaterTiles, int amountOfPlainTiles,
                      int numberOfLandMasses) {
    this.landMassSizeX = landMassSizeX;
    this.landMassSizeY = landMassSizeY;
    this.amountOfAridTiles = amountOfAridTiles;
    this.amountOfFertileTiles = amountOfFertileTiles;
    this.amountOfPlainTiles = amountOfPlainTiles;
    this.amountOfWaterTiles = amountOfWaterTiles;
    this.numberOfLandMasses = numberOfLandMasses;
  }

  public int getLandMassSizeX() {
    return landMassSizeX;
  }

  public int getLandMassSizeY() {
    return landMassSizeY;
  }

  /**
   * Create map.
   * Generates landMass tiles in accordance to the tiles provided.
   * Shuffles them to create random terrain.
   * sets the simulationMap to the joining of the landMasses.
   */
  public void createMap() {
    generateTiles();
    for (int landMassPointer = 0; landMassPointer < numberOfLandMasses; landMassPointer++) {
      Collections.shuffle(this.listOfTiles);
      this.landMassMaps.add(generateLandMass());
    }
    setSimulationMap();
  }

  private void generateTiles() {
    // stores the amount of tiles that are suppose to be in a land mass
    int reservedNumberOfTiles = this.landMassSizeX * this.landMassSizeY;
    // stores the amount of tiles that are provided to us.
    int actualNumberOfTiles = this.amountOfAridTiles + this.amountOfFertileTiles
        + this.amountOfPlainTiles + this.amountOfWaterTiles;
    // Check if the amounts are equal
    if ((reservedNumberOfTiles != actualNumberOfTiles)) {
      throw new AssertionError("Number of reserved Tiles does not equal the Actual "
          + "number of tiles provided.\nActual Tiles = " + actualNumberOfTiles + "\n"
          + "Reserved Tiles = " + reservedNumberOfTiles);
    }
    // initialise the tiles and add them to a single array
    for (int i = 0; i < this.amountOfAridTiles; i++) {
      Tile tile = new AridTile();
      this.listOfTiles.add(tile);
    }

    for (int i = 0; i < this.amountOfFertileTiles; i++) {
      Tile tile = new FertileTile();
      this.listOfTiles.add(tile);
    }

    for (int i = 0; i < this.amountOfPlainTiles; i++) {
      Tile tile = new PlainTile();
      this.listOfTiles.add(tile);
    }

    for (int i = 0; i < this.amountOfWaterTiles; i++) {
      Tile tile = new WaterTile();
      this.listOfTiles.add(tile);
    }

  }

  /* Generates a list of LandMasses
  LandMass is a 2d array of tiles which represent the different land masses */
  private Tile[][] generateLandMass() {
    int tileCounter = 0;
    Tile[][] currentLandMass = new Tile[landMassSizeX][landMassSizeY];
    for (int i = 0; i < landMassSizeY; i++) {
      for (int j = 0; j < landMassSizeX; j++) {
        currentLandMass[i][j] = listOfTiles.get(tileCounter);
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
  public Tile[][] getSimulationMap() {
    return simulationMap;
  }

  /**
   * Sets the mapSizeX.
   * Sets the mapSizeY.
   */
  public void setSimulationMap() {
    /* This function adds all the previously made terrain onto a single canvas
    it does so by adding the terrain one by one to a newly constructed 2d array
    each landmass has the same dimensions so we know that each land mass will be
    a distance equal to the previous landmass length away from the leftmost edge of the 2d array
    to create some padding we also add a layer of water between each landmass */

    // Sets the overall size of the Canvas(Map)
    this.mapSizeX = this.landMassSizeX * numberOfLandMasses + (numberOfLandMasses + 1);
    this.mapSizeY = this.landMassSizeY + 2;
    this.simulationMap = new Tile[this.mapSizeY][this.mapSizeX];

    /* Since its a 2d array we will consider each row to be y co-ord and each column to be x
    This means the top right corner is the point (y0,x0)
    y comes first as when accessing the array we will be accessing the row first
    and column second. */

    // this for loop creates the outer ridge of water
    for (int y = 0; y < mapSizeY; y++) {
      for (int x = 0; x < mapSizeX; x++) {
        if (x == 0 | x == mapSizeX - 1 | y == 0 | y == mapSizeY - 1) {
          Tile edgeTile = new WaterTile();
          this.simulationMap[y][x] = edgeTile;
        }
      }
    }

    /* This is an algorithm that places each landMass next to each other
    i is a counter of which tile we're working with(essentially landMassPointer + 1)
    this is to accommodate the watter padding */
    int i = 1;
    /* landMassPointer points to the current land mass we want to place and helps calculate the
    position */

    int landMassPointer = 0;
    // for each landmass
    for (Tile[][] landMass : this.landMassMaps) {
      // y position of the landmass
      int landMassYPos = 0;
      for (int y = 1; y < landMassSizeY + 1; y++) {
        // x position of the landmass
        int landMassXPos = 0;
        for (int x = landMassSizeY * landMassPointer + i; x < landMassSizeY + 1; x++) {
          /* places a tile from a landMass in the appropriate y,x co-ords
          the x co-ord is calculated depending on which land mass we're working with
          if were working with land mass 1 the x co-ord will be
          length of landmass * landMassPointer(0) + i
          i = (landMassPointer(0) + 1 (accommodate for water padding)) */
          this.simulationMap[y][x] = landMass[landMassYPos][landMassXPos];
          landMassXPos++;
        }
        landMassYPos++;
      }
    }
  }


}
