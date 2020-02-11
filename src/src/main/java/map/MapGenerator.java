package map;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import map.tiles.AridTile;
import map.tiles.FertileTile;
import map.tiles.PlainTile;
import map.tiles.WaterTile;

/**
 * The type Map generator.
 */
public class MapGenerator {

  final int landMassSizeX;
  final int landMassSizeY;
  final int numberOfLandMasses;

  private final int amountOfAridTiles;
  private final int amountOfFertileTiles;
  private final int amountOfWaterTiles;
  private final int amountOfPlainTiles;

  /**
   * The List of tiles.
   */
  public ArrayList<Tile> listOfTiles = new ArrayList<Tile>();
  public Tile[][] landMass;
  public Tile[][] mapOfOrderedTiles;
  public ArrayList<Tile[][]> landMassMaps = new ArrayList<Tile[][]>();

  /**
   * Instantiates a new Map generator.
   *
   */
  public MapGenerator(int landMassSizeX, int landMassSizeY, int amountOfAridTiles, int amountOfFertileTiles,
                      int amountOfWaterTiles, int amountOfPlainTiles,
                      int numberOfLandMasses) throws UnexpectedException {
    this.landMassSizeX = landMassSizeX;
    this.landMassSizeY = landMassSizeY;
    this.amountOfAridTiles = amountOfAridTiles;
    this.amountOfFertileTiles = amountOfFertileTiles;
    this.amountOfPlainTiles = amountOfPlainTiles;
    this.amountOfWaterTiles = amountOfWaterTiles;
    this.numberOfLandMasses = numberOfLandMasses;
    generateTiles();
    for(int landMassCounter = 0; landMassCounter < numberOfLandMasses; landMassCounter++){
      Collections.shuffle(this.listOfTiles);
      landMassMaps.add(generateLandMass());
    }
  }

  public Tile[][] getMapOfOrderedTiles() {
    return mapOfOrderedTiles;
  }

  public void setMapOfOrderedTiles(Tile[][] mapOfOrderedTiles) {
    this.mapOfOrderedTiles = mapOfOrderedTiles;
  }

  private Tile[][] generateLandMass() {
    int tileCounter = 0;
    Tile [][] currentLandMass = new Tile[landMassSizeX][landMassSizeY];
    for(int i = 0; i < landMassSizeY; i++){
      for (int j = 0; j < landMassSizeX; j++){
        currentLandMass[i][j] = listOfTiles.get(tileCounter);
        tileCounter ++;
      }
    }
    System.out.println(Arrays.deepToString(currentLandMass));
    return currentLandMass;

  }

  private void generateTiles() throws UnexpectedException {
    int reservedNumberOfTiles = this.landMassSizeX * this.landMassSizeY;

    int actualNumberOfTiles = this.amountOfAridTiles + this.amountOfFertileTiles
        + this.amountOfPlainTiles + this.amountOfWaterTiles;

    if ((reservedNumberOfTiles != actualNumberOfTiles)) {
      throw new AssertionError("Number of reserved Tiles does not equal the Actual "
          + "number of tiles provided.\nActual Tiles = " + actualNumberOfTiles + "\nReserved Tiles = " + reservedNumberOfTiles);
    }
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


}
