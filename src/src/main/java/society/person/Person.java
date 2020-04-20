package society.person;

import game.Moves;
import society.Society;

public class Person {
  private static final float MAX_HEALTH = 100;
  private static final float MIN_HEALTH = 1;
  private static final float MAX_INDEX = 1.0f;
  private static final float MIN_INDEX = 0f;
  private static final float PRIME_AGE = 30;
  private static final float DEFAULT_INDEX = (MAX_INDEX + MIN_INDEX) / 2;
  private float health;
  private int age;
  private Society citizenOf;
  private float aggressiveness;
  private float attractiveness;
  private float opinionOfLeader = 0.5f;
  private float productiveness;

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
  public Person(int age, float aggressiveness, float attractiveness, float productiveness, Society society) {
    this.health = MAX_HEALTH;
    this.age = age;
    this.aggressiveness = aggressiveness;
    this.attractiveness = attractiveness;
    this.productiveness = productiveness;
    this.citizenOf = society;
    calOpinionOfLeader();
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

  public float getOpinionOfLeader() {
    return opinionOfLeader;
  }

  public void setOpinionOfLeader(float opinionOfLeader) {
    this.opinionOfLeader = opinionOfLeader;
  }

  public void calOpinionOfLeader() {
    float opinionModifier = 1;
    if ((float) citizenOf.getTotalRawMaterialResource() / citizenOf.getPopulation().size() < Society.getMaterialPerPerson()) {
      opinionModifier -= 0.1;
    } else {
      opinionModifier += 0.1;
    }
    if ((float) citizenOf.getTotalFoodResource() / citizenOf.getPopulation().size() < Society.getFoodPerPerson()) {
      opinionModifier -= 0.1;
    } else {
      opinionModifier += 0.1;
    }
    if (citizenOf.getLastMove() == Moves.War) {
      opinionModifier -= 0.2;
    } else if (citizenOf.getLastMove() == Moves.ClaimTile) {
      opinionModifier += 0.1;
    } else if (citizenOf.getLastMove() == Moves.Trade) {
      opinionModifier += 0.2;
    }
    setOpinionOfLeader(opinionOfLeader + opinionModifier);
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
