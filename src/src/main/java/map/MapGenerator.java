package map;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.WaterTile;

/**
 * The type Map generator.
 */
public class MapGenerator {

  /**
   * The Size x.
   */
  final int sizeX;
  /**
   * The Size y.
   */
  final int sizeY;
  final int numberOfWaterSources;

  private final int amountOfAridTiles;
  private final int amountOfFertileTiles;
  private final int amountOfWaterTiles;
  private final int amountOfPlainTiles;

  /**
   * The List of tiles.
   */
  public ArrayList<Tile> listOfLandTiles = new ArrayList<Tile>();
  public ArrayList<ArrayList<Tile>> listOfWaterTiles = new ArrayList<ArrayList<Tile>>();
  public Tile[][] mapOfOrderedTiles;

  /**
   * Instantiates a new Map generator.
   *
   * @param sizeX the size x
   * @param sizeY the size y
   */
  public MapGenerator(int sizeX, int sizeY, int amountOfAridTiles, int amountOfFertileTiles,
                      int amountOfWaterTiles, int amountOfPlainTiles,
                      int numberOfWaterSources) throws UnexpectedException {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.amountOfAridTiles = amountOfAridTiles;
    this.amountOfFertileTiles = amountOfFertileTiles;
    this.amountOfPlainTiles = amountOfPlainTiles;
    this.amountOfWaterTiles = amountOfWaterTiles;
    this.numberOfWaterSources = numberOfWaterSources;
    generateTiles();
    generateOrderedListOfTile();
  }

  public Tile[][] getMapOfOrderedTiles() {
    return mapOfOrderedTiles;
  }

  public void setMapOfOrderedTiles(Tile[][] mapOfOrderedTiles) {
    this.mapOfOrderedTiles = mapOfOrderedTiles;
  }

  private void generateOrderedListOfTile() {
    // Initialise a fixed length 2d Matrix representation of the map
    Tile[][] mapOfTiles = new Tile[this.sizeX][this.sizeY];

    // add in the water Tiles first
    // Rules for generating water tiles
    // if one tile is in column 0 no tile can be in the last column and vice versa
    // if one tile is in row 0 no tile can be in the last row
    // no two sources of water can meet
    int numberOfTilesInASource = this.amountOfWaterTiles / this.numberOfWaterSources;
    for (int counter = 0; counter < numberOfWaterSources; counter++) {
      addWaterTiles(mapOfTiles, numberOfTilesInASource, counter);
    }

    setMapOfOrderedTiles(mapOfTiles);
  }

  private void addWaterTiles(Tile[][] mapOfTiles, int numberOfTilesInASource, int counter) {
    System.out.println(mapOfTiles.length);

  }

  private void generateTiles() throws UnexpectedException {
    int reservedNumberOfTiles = sizeX * sizeY;

    int actualNumberOfTiles = this.amountOfAridTiles + this.amountOfFertileTiles
        + this.amountOfPlainTiles + this.amountOfWaterTiles;

    if ((reservedNumberOfTiles != actualNumberOfTiles)) {
      throw new AssertionError("Number of reserved Tiles does not equal the Actual "
          + "number of tiles provided");
    }
    for (int i = 0; i < this.amountOfAridTiles; i++) {
      Tile tile = new AridTile();
      this.listOfLandTiles.add(tile);
    }

    for (int i = 0; i < this.amountOfFertileTiles; i++) {
      Tile tile = new FertileTile();
      this.listOfLandTiles.add(tile);
    }

    for (int i = 0; i < this.amountOfPlainTiles; i++) {
      Tile tile = new PlainTile();
      this.listOfLandTiles.add(tile);
    }

    int numberOfTilesInASource = this.amountOfWaterTiles / this.numberOfWaterSources;
    for (int i = 0; i < this.numberOfWaterSources; i++) {
      ArrayList<Tile> tempListOfWaterTiles = new ArrayList<Tile>();
      for (int j = 0; j < numberOfTilesInASource; j++) {
        Tile tile = new WaterTile();
        tempListOfWaterTiles.add(tile);
      }
      this.listOfWaterTiles.add(tempListOfWaterTiles);
    }

  }


}
