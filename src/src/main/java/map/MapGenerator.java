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

  private final int amountOfAridTiles;
  private final int amountOfFertileTiles;
  private final int amountOfWaterTiles;
  private final int amountOfPlainTiles;

  /**
   * The List of tiles.
   */
  public ArrayList<Tile> listOfTiles = new ArrayList<Tile>();
  public Tile[][] listOfOrderedTiles;

  /**
   * Instantiates a new Map generator.
   *
   * @param sizeX the size x
   * @param sizeY the size y
   */
  public MapGenerator(int sizeX, int sizeY, int amountOfAridTiles, int amountOfFertileTiles,
                      int amountOfWaterTiles, int amountOfPlainTiles) throws UnexpectedException {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.amountOfAridTiles = amountOfAridTiles;
    this.amountOfFertileTiles = amountOfFertileTiles;
    this.amountOfPlainTiles = amountOfPlainTiles;
    this.amountOfWaterTiles = amountOfWaterTiles;
    generateTiles();
    generateOrderedListOfTile();
  }

  public Tile[][] getListOfOrderedTiles() {
    return listOfOrderedTiles;
  }

  public void setListOfOrderedTiles(Tile[][] listOfOrderedTiles) {
    this.listOfOrderedTiles = listOfOrderedTiles;
  }

  // This should generate a list of lists/a 2 dimensional matrix of the tiles.
  // It should follow these simple rules
  // 1) no single non water Tiles can be surrounded by only watter Tiles
  private void generateOrderedListOfTile() {
    this.listOfOrderedTiles = new Tile[this.sizeX][this.sizeY];
  }

  private void generateTiles() throws UnexpectedException {
    int reservedNumberOfTiles = sizeX * sizeY;
    int actualNumberOfTiles = this.amountOfAridTiles + this.amountOfFertileTiles
        + this.amountOfPlainTiles + this.amountOfWaterTiles;

    if ((reservedNumberOfTiles != actualNumberOfTiles)) {
      throw new AssertionError("Number of reserved Tiles does not equal the Actual "
          + "number of tiles provided");
    }
    for (double i = 0.0; i < amountOfAridTiles; i++) {
      Tile tile = new AridTile();
      listOfTiles.add(tile);
    }

    for (double i = 0.0; i < amountOfFertileTiles; i++) {
      Tile tile = new FertileTile();
      listOfTiles.add(tile);
    }

    for (double i = 0.0; i < amountOfPlainTiles; i++) {
      Tile tile = new PlainTile();
      listOfTiles.add(tile);
    }
    for (double i = 0.0; i < amountOfWaterTiles; i++) {
      Tile tile = new WaterTile();
      listOfTiles.add(tile);
    }

  }


}
