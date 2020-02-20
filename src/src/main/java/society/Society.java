package society;

import java.util.ArrayList;
import java.util.Random;
import society.person.Person;
import society.person.dataobjects.Gender;

public class Society {
  public int personIdCounter;
  private ArrayList<Person> population;
  private int societyId;
  private float averageTechnology;
  private float averageMedicine;
  private float averageAgriculture;
  private int averageLifeExpectancy;


  /**
   * Instantiates a new Society.
   *
   * @param initialPopulationSize the initial population
   * @param societyId             the society id
   */
  public Society(int initialPopulationSize, int societyId) {
    this.societyId = societyId;
    this.population = generateInitialPopulation(initialPopulationSize);

  }

  private ArrayList<Person> generateInitialPopulation(int initialPopulation) {
    ArrayList<Person> pop = new ArrayList<>();
    Gender[] genderList = Gender.class.getEnumConstants();
    pop.add(new Person(0, Gender.FEMALE));
    pop.add(new Person(1, Gender.MALE));
    for (personIdCounter = 2; personIdCounter < initialPopulation; personIdCounter++) {
      Random r = new Random();
      int randomIndex = r.nextInt(genderList.length);
      pop.add(new Person(personIdCounter, genderList[randomIndex]));
    }
    return pop;
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
   *
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
