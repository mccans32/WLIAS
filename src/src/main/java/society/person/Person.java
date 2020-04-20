package society.person;

import game.world.World;
import java.util.Random;
import society.Society;
import society.person.dataobjects.SocietyOpinion;

public class Person {
  private static final float MIN_DEFAULT_INDEX = 0.35f;
  private static final int AGE_AMOUNT = 9;
  private static final float MAX_HEALTH = 100;
  private static final float MIN_HEALTH = 1;
  private static final float MAX_INDEX = 1.0f;
  private static final float MIN_INDEX = 0f;
  private static final float PRIME_AGE = 30;
  private static final float MAX_DEFAULT_INDEX = 0.65f;
  private static Society[] defaultSocieties = World.getSocieties();
  private float health;
  private int age;
  private float aggressiveness;
  private float attractiveness;
  private float opinionOfLeader;
  private float productiveness;
  private SocietyOpinion[] opinionsOfSocieties;

  /**
   * Instantiates a new Person.
   */
  public Person() {
    this.health = MAX_HEALTH;
    this.age = 0;
    this.productiveness = GenerateRandomFloatInRange(MAX_DEFAULT_INDEX, MIN_DEFAULT_INDEX);
    this.aggressiveness = GenerateRandomFloatInRange(MAX_DEFAULT_INDEX, MIN_DEFAULT_INDEX);
    this.attractiveness = GenerateRandomFloatInRange(MAX_DEFAULT_INDEX, MIN_DEFAULT_INDEX);
  }

  /**
   * Instantiates a new Person.
   *
   * @param age            the age
   * @param aggressiveness the aggressiveness
   * @param attractiveness the fertility
   */
  public Person(int age, float aggressiveness, float attractiveness, float productiveness) {
    this.health = MAX_HEALTH;
    this.age = age;
    this.aggressiveness = aggressiveness;
    this.attractiveness = attractiveness;
    this.productiveness = productiveness;
    if (defaultSocieties != null) {
      setOpinions(World.getSocieties());
    }
  }

  public static Society[] getDefaultSocieties() {
    return defaultSocieties.clone();
  }

  public static float getMaxHealth() {
    return MAX_HEALTH;
  }

  public static float getMinHealth() {
    return MIN_HEALTH;
  }

  public static float getMaxIndex() {
    return MAX_INDEX;
  }

  public static float getMinIndex() {
    return MIN_INDEX;
  }

  public static float getMaxDefaultIndex() {
    return MAX_DEFAULT_INDEX;
  }

  public static float getPrimeAge() {
    return PRIME_AGE;
  }

  private float GenerateRandomFloatInRange(float maxNo, float minNo) {
    Random r = new Random();
    return minNo + r.nextFloat() * (maxNo - minNo);
  }

  public float getProductiveness() {
    return productiveness;
  }

  public void setProductiveness(float productiveness) {
    this.productiveness = productiveness;
  }

  public void setOpinions(Society[] listOfSocieties) {
    setOpinionOfLeader();
    setOpinionsOfSocieties(listOfSocieties);
  }

  public float getOpinionOfLeader() {
    return opinionOfLeader;
  }

  public void setOpinionOfLeader(float opinionOfLeader) {
    this.opinionOfLeader = opinionOfLeader;
  }

  public void setOpinionOfLeader() {
    this.opinionOfLeader = calculateOpinionOfLeader();
  }

  private float calculateOpinionOfLeader() {
    return 0;
  }

  public SocietyOpinion[] getOpinionsOfSocieties() {
    return opinionsOfSocieties.clone();
  }

  public void setOpinionsOfSocieties(SocietyOpinion[] opinionsOfSocieties) {
    this.opinionsOfSocieties = opinionsOfSocieties.clone();
  }

  /**
   * Sets opinions of societies.
   *
   * @param listOfSocieties the list of societies
   */
  public void setOpinionsOfSocieties(Society[] listOfSocieties) {
    this.opinionsOfSocieties =
        new SocietyOpinion[listOfSocieties.length > 0 ? listOfSocieties.length - 1 : 0];
    for (int i = 0; i < listOfSocieties.length - 1; i++) {
      SocietyOpinion opinion = new SocietyOpinion(listOfSocieties[i].getSocietyId(),
          calculateOpinionOfSociety(listOfSocieties[i]));
      this.opinionsOfSocieties[i] = opinion;
    }
  }

  private float calculateOpinionOfSociety(Society society) {
    return 0;
  }

  public float getHealth() {
    return health;
  }

  public void setHealth(float health) {
    this.health = health;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public float getAggressiveness() {
    return aggressiveness;
  }

  public void setAggressiveness(float aggressiveness) {
    this.aggressiveness = aggressiveness;
  }

  public float getAttractiveness() {
    return attractiveness;
  }

  public void setAttractiveness(float attractiveness) {
    this.attractiveness = attractiveness;
  }

  /**
   * Fitness score for the person.
   *
   * @return the float
   */
  public float fitnessScore() {
    float ageDifference = Math.abs(age - PRIME_AGE);
    float ageSubtraction = ageDifference / PRIME_AGE;
    float healthWeight = health / MAX_HEALTH;
    float attractivenessWeight = attractiveness;

    return healthWeight + attractivenessWeight - ageSubtraction;
  }

  /**
   * Age a person a return whether they are still alive.
   *
   * @return the boolean
   */
  public boolean age() {
    age += AGE_AMOUNT;
    // Check for death probabilities from http://www.bandolier.org.uk/booth/Risk/dyingage.html
    // Get the probability based on age bracket
    float probability = 0;
    if (age <= 10) {
      probability = 1 / 10000f;
    } else if (age <= 25) {
      probability = 1 / 2000f;
    } else if (age <= 35) {
      probability = 1 / 700f;
    } else if (age <= 55) {
      probability = 1 / 300f;
    } else if (age <= 65) {
      probability = 1 / 120f;
    } else if (age <= 75) {
      probability = 1 / 40f;
    } else if (age <= 85) {
      probability = 1 / 6f;
    }
    // Check if the person has died, compensate for the age amount by checking that many times
    boolean hasDied = false;
    for (int i = 0; i < AGE_AMOUNT; i++) {
      hasDied = checkProbability(probability);
      if (hasDied) {
        break;
      }
    }

    return hasDied;
  }

  private boolean checkProbability(float probability) {
    Random r = new Random();
    float randomFloat = r.nextFloat() * 100f;
    return randomFloat <= probability * 100f;
  }
}
