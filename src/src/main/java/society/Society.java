package society;

import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
import game.world.World;
import java.util.ArrayList;
import java.util.Random;
import math.Vector3f;
import society.person.Person;
import society.person.dataobjects.Gender;

public class Society {
  private static final int DEFAULT_POPULATION_SIZE = 10;
  public int personIdCounter;
  private Vector3f societyColor;
  private ArrayList<Person> population;
  private int societyId;
  private int averageLifeExpectancy;
  private ArrayList<TileWorldObject> territory = new ArrayList<>();
  private ArrayList<TileWorldObject> claimableTerritory;
  private float averageAggressiveness;
  private float averageProductivity;

  /**
   * Instantiates a new Society.
   *
   * @param initialPopulationSize the initial population
   * @param societyId             the society id
   */
  public Society(int initialPopulationSize, int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
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

  public Vector3f getSocietyColor() {
    return societyColor;
  }

  /**
   * Update the borders for each tile owned by this society.
   *
   * @param tileModel the tile model
   */
  public void updateBorders(RectangleModel tileModel) {
    for (TileWorldObject worldTile : this.territory) {
      if (worldTile.getBorderMesh() == null) {
        worldTile.setBorderMesh(this.societyColor, tileModel);
      }
    }
  }

  public void claimTile(TileWorldObject worldTile) {
    worldTile.setClaimed(true);
    this.territory.add(worldTile);
  }

  public ArrayList<TileWorldObject> getTerritory() {
    return territory;
  }

  /**
   * Claim tiles.
   */
  public ArrayList<TileWorldObject> calculateClaimableTerritory() {
    claimableTerritory = new ArrayList<>();
    for (TileWorldObject worldTile : territory) {
      addClaimableTiles(worldTile.getRow(), worldTile.getColumn());
    }
    return claimableTerritory;
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
    Gender[] genderList = Gender.class.getEnumConstants();
    population.add(new Person(0, Gender.FEMALE));
    population.add(new Person(1, Gender.MALE));
    for (personIdCounter = 2; personIdCounter < initialPopulationSize; personIdCounter++) {
      Random r = new Random();
      int randomIndex = r.nextInt(genderList.length);
      population.add(new Person(personIdCounter, genderList[randomIndex]));
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

  public int getAverageLifeExpectancy() {
    return averageLifeExpectancy;
  }

  public void setAverageLifeExpectancy() {
    this.averageLifeExpectancy = calculateLifeExpectancy();
  }

  private int calculateLifeExpectancy() {
    return 0;
  }
}
