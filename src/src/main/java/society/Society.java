package society;

import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
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

  public Society(int societyId, Vector3f societyColor) {
    this.societyId = societyId;
    this.societyColor = societyColor;
    generateInitialPopulation(DEFAULT_POPULATION_SIZE);
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

  public ArrayList<TileWorldObject> getTerritory() {
    return territory;
  }

  public void claimTile(TileWorldObject worldTile) {
    this.territory.add(worldTile);
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
