package society.person;

import game.world.World;
import society.Society;
import society.person.dataobjects.SocietyOpinion;

public class Person {
  private static final float MAX_HEALTH = 100;
  private static final float MIN_HEALTH = 1;
  private static final float MAX_INDEX = 1.0f;
  private static final float MIN_INDEX = 0f;
  private static final float PRIME_AGE = 30;
  private static final float DEFAULT_INDEX = (MAX_INDEX + MIN_INDEX) / 2;
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
    this.productiveness = DEFAULT_INDEX;
    this.aggressiveness = DEFAULT_INDEX;
    this.attractiveness = DEFAULT_INDEX;
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

  public static float getDefaultIndex() {
    return DEFAULT_INDEX;
  }

  public static float getPrimeAge() {
    return PRIME_AGE;
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
    this.opinionsOfSocieties = new SocietyOpinion[listOfSocieties.length - 1];
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
}
