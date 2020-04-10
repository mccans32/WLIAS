package society;

import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
import game.world.World;
import java.util.ArrayList;
import math.Vector3f;
import society.person.Person;

public class Society {
  private static final int DEFAULT_POPULATION_SIZE = 10;
  public int personIdCounter;
  private Vector3f societyColor;
  private ArrayList<Person> population;
  private int societyId;
  private float averageLifeExpectancy;
  private int totalFoodResource = 0;
  private int totalRawMaterialResource = 0;
  private ArrayList<TileWorldObject> territory = new ArrayList<>();
  private ArrayList<TileWorldObject> claimableTerritory = new ArrayList<>();
  private ArrayList<TileWorldObject> opponentWarringTiles = new ArrayList<>();
  private ArrayList<TileWorldObject> societyWarringTiles = new ArrayList<>();
  private float averageAggressiveness;
  private float averageProductivity;
  private int score;
  private boolean endTurn = false;
  private ArrayList<Society> possibleTradingSocieties = new ArrayList<>();

  /**
   * Instantiates a new Society.
   *
   * @param initialPopulationSize the initial population
   * @param societyId             the society id
   */
  public Society(int initialPopulationSize, int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
    this.score = 0;
    generateInitialPopulation(initialPopulationSize);

  }

  /**
   * Instantiates a new Society with a unique ID and BorderColour.
   *
   * @param societyId    the society id
   * @param societyColor the society color
   */
  public Society(int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
    generateInitialPopulation(DEFAULT_POPULATION_SIZE);
  }

  public static int getDefaultPopulationSize() {
    return DEFAULT_POPULATION_SIZE;
  }

  public ArrayList<TileWorldObject> getOpponentWarringTiles() {
    return opponentWarringTiles;
  }

  public void setOpponentWarringTiles(ArrayList<TileWorldObject> opponentWarringTiles) {
    this.opponentWarringTiles = opponentWarringTiles;
  }

  public ArrayList<TileWorldObject> getSocietyWarringTiles() {
    return societyWarringTiles;
  }

  public void setSocietyWarringTiles(ArrayList<TileWorldObject> societyWarringTiles) {
    this.societyWarringTiles = societyWarringTiles;
  }

  public ArrayList<Society> getPossibleTradingSocieties() {
    return possibleTradingSocieties;
  }

  public void setPossibleTradingSocieties(ArrayList<Society> possibleTradingSocieties) {
    this.possibleTradingSocieties = possibleTradingSocieties;
  }

  public boolean isEndTurn() {
    return endTurn;
  }

  public void setEndTurn(boolean endTurn) {
    this.endTurn = endTurn;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public float getAverageAggressiveness() {
    return averageAggressiveness;
  }

  /**
   * Sets average aggressiveness.
   */
  public void setAverageAggressiveness() {
    float totalAggressiveness = 0;
    for (Person person : population) {
      totalAggressiveness += person.getAggressiveness();
    }
    this.averageAggressiveness = totalAggressiveness / population.size();
  }

  public float getAverageProductivity() {
    return averageProductivity;
  }

  /**
   * Sets average productivity.
   */
  public void setAverageProductivity() {
    float totalProductivity = 0;
    for (Person person : population) {
      totalProductivity += person.getProductiveness();
    }
    this.averageProductivity = totalProductivity / population.size();
  }

  public int getTotalFoodResource() {
    return totalFoodResource;
  }

  public void setTotalFoodResource(int totalFoodResource) {
    this.totalFoodResource = totalFoodResource;
  }

  public int getTotalRawMaterialResource() {
    return totalRawMaterialResource;
  }

  public void setTotalRawMaterialResource(int totalRawMaterialResource) {
    this.totalRawMaterialResource = totalRawMaterialResource;
  }

  public Vector3f getSocietyColor() {
    return societyColor;
  }

  /**
   * Update the borders for each tile owned by this society.
   *
   * @param tileModel the tile model
   */
  public void updateBorders(RectangleModel tileModel) {
    calculateResources();
    for (TileWorldObject worldTile : this.territory) {
      if (worldTile.getBorderMesh() == null
          || worldTile.getBorderMesh().getMaterial().getColorOffsetRgb() != societyColor) {
        worldTile.setBorderMesh(this.societyColor, tileModel);
      }
    }
  }

  /**
   * Calculate Food and Raw Material resource values.
   */
  public void calculateResources() {
    int foodTotal = 0;
    int rawMaterials = 0;
    for (TileWorldObject worldTile : this.territory) {
      foodTotal += worldTile.getFoodResource();
      rawMaterials += worldTile.getRawMaterialResource();
    }
    setTotalFoodResource(foodTotal);
    setTotalRawMaterialResource(rawMaterials);
  }

  /**
   * This society claims a tile.
   *
   * @param worldTile the world tile
   */
  public void claimTile(TileWorldObject worldTile) {
    worldTile.setClaimed(true);
    worldTile.setClaimedBy(this);
    this.territory.add(worldTile);
  }

  public ArrayList<TileWorldObject> getTerritory() {
    return territory;
  }

  /**
   * Claim tiles.
   */
  public ArrayList<TileWorldObject> calculateClaimableTerritory() {
    claimableTerritory.clear();
    for (TileWorldObject worldTile : territory) {
      addClaimableTiles(worldTile.getRow(), worldTile.getColumn());
    }
    return claimableTerritory;
  }

  /**
   * Calculate warring tiles.
   */
  public void calculateWarringTiles() {
    opponentWarringTiles.clear();
    societyWarringTiles.clear();
    for (TileWorldObject worldTile : territory) {
      addWarringTiles(worldTile.getRow(), worldTile.getColumn());
    }
  }

  private void addWarringTiles(int row, int column) {
    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (map[row][column - 1].isClaimed() && column - 1 != 0
        && map[row][column - 1].getClaimedBy().getSocietyId() != societyId) {
      if (!societyWarringTiles.contains(map[row][column])) {
        societyWarringTiles.add(map[row][column]);
      }
      if (!opponentWarringTiles.add(map[row][column - 1])) {
        opponentWarringTiles.add(map[row][column - 1]);
      }
    }
    // Check right side of the territory
    if (map[row][column + 1].isClaimed() && column + 1 != map.length - 1
        && map[row][column + 1].getClaimedBy().getSocietyId() != societyId) {
      if (!societyWarringTiles.contains(map[row][column])) {
        societyWarringTiles.add(map[row][column]);
      }
      if (!opponentWarringTiles.add(map[row][column + 1])) {
        opponentWarringTiles.add(map[row][column + 1]);
      }
    }
    // Check top of territory
    if (map[row - 1][column].isClaimed() && row - 1 != 0
        && map[row - 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!societyWarringTiles.contains(map[row][column])) {
        societyWarringTiles.add(map[row][column]);
      }
      if (!opponentWarringTiles.contains(map[row - 1][column])) {
        opponentWarringTiles.add(map[row - 1][column]);
      }
    }
    // check bottom of territory
    if (map[row + 1][column].isClaimed() && row + 1 != map.length - 1
        && map[row + 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!societyWarringTiles.contains(map[row][column])) {
        societyWarringTiles.add(map[row][column]);
      }
      if (!opponentWarringTiles.contains(map[row + 1][column])) {
        opponentWarringTiles.add(map[row + 1][column]);
      }
    }
  }

  private void addClaimableTiles(int row, int column) {

    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (!map[row][column - 1].isClaimed() & column - 1 != 0) {
      claimableTerritory.add(map[row][column - 1]);
    }
    // Check right side of the territory
    if (!map[row][column + 1].isClaimed() & column + 1 != map.length - 1) {
      claimableTerritory.add(map[row][column + 1]);
    }
    // Check top of territory
    if (!map[row - 1][column].isClaimed() & row - 1 != 0) {
      claimableTerritory.add(map[row - 1][column]);
    }
    // check bottom of territory
    if (!map[row + 1][column].isClaimed() & row + 1 != map.length - 1) {
      claimableTerritory.add(map[row + 1][column]);
    }
  }


  private void generateInitialPopulation(int initialPopulationSize) {
    population = new ArrayList<>();
    population.add(new Person(0));
    population.add(new Person(1));
    for (personIdCounter = 2; personIdCounter < initialPopulationSize; personIdCounter++) {
      population.add(new Person(personIdCounter));
    }
  }

  public ArrayList<Person> getPopulation() {
    return population;
  }

  public void setPopulation(ArrayList<Person> population) {
    this.population = population;
  }

  public int getSocietyId() {
    return societyId;
  }

  public void setSocietyId(int societyId) {
    this.societyId = societyId;
  }

  public float getAverageLifeExpectancy() {
    return averageLifeExpectancy;
  }

  public void setAverageLifeExpectancy() {
    this.averageLifeExpectancy = calculateLifeExpectancy();
  }

  private int calculateLifeExpectancy() {
    return 0;
  }

  public void calculatePossibleTradingSocieties() {
    for (TileWorldObject worldTile : territory){
      checkPossibleTrading(worldTile);
    }
  }

  private void checkPossibleTrading(TileWorldObject worldTile) {
  }
}
