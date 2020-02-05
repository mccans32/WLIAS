package map;

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
  final double sizeX;
  /**
   * The Size y.
   */
  final double sizeY;

  private final double percentArid;
  private final double percentFertile;
  private final double percentWater;
  private final double percentPlain;

  /**
   * The List of tiles.
   */
  public ArrayList<Tile> listOfTiles = new ArrayList<Tile>();

  /**
   * Instantiates a new Map generator.
   *
   * @param sizeX          the size x
   * @param sizeY          the size y
   * @param percentArid    the percent arid
   * @param percentFertile the percent fertile
   * @param percentWater   the percent water
   * @param percentPlain   the percent plain
   */
  public MapGenerator(double sizeX, double sizeY, double percentArid, double percentFertile,
                      double percentWater, double percentPlain) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    this.percentArid = percentArid;
    this.percentFertile = percentFertile;
    this.percentWater = percentWater;
    this.percentPlain = percentPlain;
    generateTiles();
  }

  private void generateTiles() {
    double totalAmountOfTiles = sizeX * sizeY;
    double amountOfAridTiles = totalAmountOfTiles * percentArid;
    for (double i = 0.0; i < amountOfAridTiles; i++) {
      Tile tile = new AridTile();
      listOfTiles.add(tile);
    }

    double amountOfFertileTiles = totalAmountOfTiles * percentFertile;
    for (double i = 0.0; i < amountOfFertileTiles; i++) {
      Tile tile = new FertileTile();
      listOfTiles.add(tile);
    }

    double amountOfPlainTiles = totalAmountOfTiles * percentPlain;
    for (double i = 0.0; i < amountOfPlainTiles; i++) {
      Tile tile = new PlainTile();
      listOfTiles.add(tile);
    }

    double amountOfWaterTiles = totalAmountOfTiles * percentWater;
    for (double i = 0.0; i < amountOfWaterTiles; i++) {
      Tile tile = new WaterTile();
      listOfTiles.add(tile);
    }


    System.out.println(listOfTiles);
  }


}
