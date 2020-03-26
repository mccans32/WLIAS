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
  private float averageTechnology;
  private float averageMedicine;
  private float averageAgriculture;
  private int averageLifeExpectancy;
  private ArrayList<TileWorldObject> territory = new ArrayList<>();
  private ArrayList<TileWorldObject> claimableTerritory;

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

  /**
   * Select random tile index.
   *
   * @param maxIndex the max index
   * @return the int
   */
  public static int selectRandomTile(int maxIndex) {
    Random r = new Random();
    return r.nextInt(maxIndex);
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
  public void claimTiles() {
    claimableTerritory = new ArrayList<>();
    for (TileWorldObject worldTile : territory) {
      for (int row = 1; row < World.getWorldMap().length - 1; row++) {
        for (int column = 1; column < World.getWorldMap().length - 1; column++) {
          if (worldTile == World.getWorldMap()[row][column]) {
            addClaimableTiles(row, column);
          }
        }
      }
    }
    if (!claimableTerritory.isEmpty()) {
      claimTile(claimableTerritory.get(selectRandomTile(claimableTerritory.size())));
      claimableTerritory = new ArrayList<>();
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
      System.out.println("hello");
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

  public float getAverageTechnology() {
    return averageTechnology;
  }

  /**
   * Sets average technology.
   *
   * @param population the population
   */
  public void setAverageTechnology(ArrayList<Person> population) {
    float totalTechnology = 0;
    for (Person person : population) {
      totalTechnology += person.getTechnology();
    }
    this.averageTechnology = totalTechnology / population.size();
  }

  public float getAverageMedicine() {
    return averageMedicine;
  }

  /**
   * Sets average medicine.
   */
  public void setAverageMedicine(ArrayList<Person> population) {
    float totalMedicine = 0;
    for (Person person : population) {
      totalMedicine += person.getMedicine();
    }
    this.averageMedicine = totalMedicine / population.size();
  }

  public float getAverageAgriculture() {
    return averageAgriculture;
  }

  /**
   * Sets average agriculture.
   */
  public void setAverageAgriculture(ArrayList<Person> population) {
    float totalAgriculture = 0;
    for (Person person : population) {
      totalAgriculture += person.getAgriculture();
    }
    this.averageAgriculture = totalAgriculture / population.size();
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
