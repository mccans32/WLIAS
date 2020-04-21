package society;

import engine.graphics.model.dimension.two.RectangleModel;
import engine.objects.world.TileWorldObject;
import game.menu.data.TradeDeal;
import game.world.Hud;
import game.world.World;
import java.util.ArrayList;
import java.util.Random;
import math.Vector3f;
import society.person.Person;

public class Society {
  private static final float MINIMUM_ARMY_AGE = 18;
  private static final int DEFAULT_POPULATION_SIZE = 10;
  private static final float FOOD_PER_PERSON = 1;
  private static final float MATERIAL_PER_PERSON = 1;
  private static final int DEFAULT_AGE = 20;
  private static final int DEFAULT_REPRODUCTION_AGE = 18;
  private static final float MIN_REPRODUCTION_RATIO = 0.05f;
  private static final float MAX_REPRODUCTION_RATIO = 0.75f;
  private static final int OFFSPRING_AMOUNT = 2;
  private static final float DEFAULT_POPULATION_REPRODUCTION_RATIO = 0.25f;
  private static final float MUTATION_PROBABILITY = 0.4f;
  private static final float MAX_MUTATION_FACTOR = 0.25f;
  private static final float MIN_MUTATION_FACTOR = 0.10f;
  public ArrayList<TradeDeal> activeTradeDeals = new ArrayList<>();
  private Vector3f societyColor;
  private ArrayList<Person> population;
  private int societyId;
  private int totalFoodResource = 0;
  private int totalRawMaterialResource = 0;
  private ArrayList<TileWorldObject> territory = new ArrayList<>();
  private ArrayList<TileWorldObject> claimableTerritory = new ArrayList<>();
  private ArrayList<TileWorldObject> defendingTiles = new ArrayList<>();
  private ArrayList<TileWorldObject> attackingTiles = new ArrayList<>();
  private int score;
  private boolean endTurn = false;
  private ArrayList<Society> possibleTradingSocieties = new ArrayList<>();
  private boolean madeMove = false;
  private int foodFromDeals;
  private int rawMatsFromDeals;
  private ArrayList<Person> army = new ArrayList<>();
  private float happiness = 0.5f;

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

  public static float getFoodPerPerson() {
    return FOOD_PER_PERSON;
  }

  public static float getMaterialPerPerson() {
    return MATERIAL_PER_PERSON;
  }

  public static int getDefaultPopulationSize() {
    return DEFAULT_POPULATION_SIZE;
  }

  public float getHappiness() {
    return happiness;
  }

  /**
   * Sets happiness.
   *
   * @param happiness the happiness
   */
  public void setHappiness(float happiness) {
    if (happiness < 0.01f) {
      happiness = 0.01f;
    } else if (happiness > 1.0f) {
      happiness = 1.0f;
    }
    this.happiness = happiness;
  }

  public ArrayList<Person> getArmy() {
    return army;
  }

  public ArrayList<TradeDeal> getActiveTradeDeals() {
    return activeTradeDeals;
  }

  public void setActiveTradeDeals(ArrayList<TradeDeal> activeTradeDeals) {
    this.activeTradeDeals = activeTradeDeals;
  }

  public int getFoodFromDeals() {
    return foodFromDeals;
  }

  public void setFoodFromDeals(int foodFromDeals) {
    this.foodFromDeals = foodFromDeals;
  }

  public int getRawMatsFromDeals() {
    return rawMatsFromDeals;
  }

  public void setRawMatsFromDeals(int rawMatsFromDeals) {
    this.rawMatsFromDeals = rawMatsFromDeals;
  }

  public boolean hasMadeMove() {
    return madeMove;
  }

  public void setMadeMove(boolean madeMove) {
    this.madeMove = madeMove;
  }

  public ArrayList<TileWorldObject> getClaimableTerritory() {
    return claimableTerritory;
  }

  public ArrayList<TileWorldObject> getDefendingTiles() {
    return defendingTiles;
  }

  public ArrayList<TileWorldObject> getAttackingTiles() {
    return attackingTiles;
  }

  public void setAttackingTiles(ArrayList<TileWorldObject> attackingTiles) {
    this.attackingTiles = attackingTiles;
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

  /**
   * Create army.
   */
  public void createArmy() {
    army.clear();
    for (Person citizen : getPopulation()) {
      if (citizen.getAge() >= MINIMUM_ARMY_AGE) {
        army.add(citizen);
      }
    }
  }

  /**
   * Gets average aggressiveness.
   *
   * @return the average aggressiveness
   */
  public float getAverageAggressiveness() {
    float averageAggressiveness = 0;
    for (Person person : population) {
      averageAggressiveness += person.getAggressiveness();
    }
    return averageAggressiveness / population.size();
  }

  /**
   * Gets average productivity.
   *
   * @return the average productivity
   */
  public float getAverageProductivity() {
    float averageProductivity = 0;
    for (Person person : population) {
      averageProductivity += person.getProductiveness();
    }
    return averageProductivity / population.size();
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
    foodTotal += foodFromDeals;
    rawMaterials += rawMatsFromDeals;
    setTotalFoodResource(foodTotal);
    setTotalRawMaterialResource(rawMaterials);
    createArmy();
  }

  /**
   * This society claims a tile.
   *
   * @param claimedTile the world tile
   */
  public void claimTile(TileWorldObject claimedTile) {
    claimedTile.setClaimed(true);
    claimedTile.setClaimedBy(this);
    this.territory.add(claimedTile);
  }

  public ArrayList<TileWorldObject> getTerritory() {
    return territory;
  }

  /**
   * Claim tiles.
   */
  public void calculateClaimableTerritory() {
    claimableTerritory.clear();
    for (TileWorldObject worldTile : territory) {
      addClaimableTiles(worldTile.getRow(), worldTile.getColumn());
    }
  }

  /**
   * Calculate warring tiles.
   */
  public void calculateAttackingTiles() {
    attackingTiles.clear();
    for (TileWorldObject worldTile : territory) {
      addWarringTiles(worldTile.getRow(), worldTile.getColumn());
    }
  }

  /**
   * Calculate defending tiles.
   *
   * @param attackingTile the attacking tile
   */
  public void calculateDefendingTiles(TileWorldObject attackingTile) {
    defendingTiles.clear();
    int row = attackingTile.getRow();
    int column = attackingTile.getColumn();
    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (map[row][column - 1].isClaimed() && column - 1 != 0
        && map[row][column - 1].getClaimedBy().getSocietyId() != societyId) {
      if (!defendingTiles.add(map[row][column - 1]) && checkForPeace(map[row][column - 1])) {
        defendingTiles.add(map[row][column - 1]);
      }
    }
    // Check right side of the territory
    if (map[row][column + 1].isClaimed() && column + 1 != map.length - 1
        && map[row][column + 1].getClaimedBy().getSocietyId() != societyId) {
      if (!defendingTiles.add(map[row][column + 1]) && checkForPeace(map[row][column + 1])) {
        defendingTiles.add(map[row][column + 1]);
      }
    }
    // Check top of territory
    if (map[row - 1][column].isClaimed() && row - 1 != 0
        && map[row - 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!defendingTiles.contains(map[row - 1][column])
          && checkForPeace(map[row - 1][column])) {
        defendingTiles.add(map[row - 1][column]);
      }
    }
    // check bottom of territory
    if (map[row + 1][column].isClaimed() && row + 1 != map.length - 1
        && map[row + 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!defendingTiles.contains(map[row + 1][column])
          && checkForPeace(map[row + 1][column])) {
        defendingTiles.add(map[row + 1][column]);
      }
    }
  }

  private void addWarringTiles(int row, int column) {
    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (map[row][column - 1].isClaimed() && column - 1 != 0
        && map[row][column - 1].getClaimedBy().getSocietyId() != societyId) {
      if (!attackingTiles.contains(map[row][column])) {
        attackingTiles.add(map[row][column]);
      }
    }
    // Check right side of the territory
    if (map[row][column + 1].isClaimed() && column + 1 != map.length - 1
        && map[row][column + 1].getClaimedBy().getSocietyId() != societyId) {
      if (!attackingTiles.contains(map[row][column])) {
        attackingTiles.add(map[row][column]);
      }
    }
    // Check top of territory
    if (map[row - 1][column].isClaimed() && row - 1 != 0
        && map[row - 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!attackingTiles.contains(map[row][column])) {
        attackingTiles.add(map[row][column]);
      }
    }
    // check bottom of territory
    if (map[row + 1][column].isClaimed() && row + 1 != map.length - 1
        && map[row + 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!attackingTiles.contains(map[row][column])) {
        attackingTiles.add(map[row][column]);
      }
    }
  }

  private boolean checkForPeace(TileWorldObject warringTile) {
    for (TradeDeal tradeDeal : activeTradeDeals) {
      return warringTile.getClaimedBy() != tradeDeal.getSocietyB()
          && warringTile.getClaimedBy() != tradeDeal.getSocietyA();
    }
    return true;
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
    for (int i = 0; i < initialPopulationSize; i++) {
      Person person = new Person();
      person.setAge(DEFAULT_AGE);
      population.add(person);
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

  /**
   * Gets average age.
   *
   * @return the average age
   */
  public float getAverageAge() {
    float age = 0;
    for (Person person : population) {
      age += person.getAge();
    }
    return age / population.size();
  }

  private int calculateLifeExpectancy() {
    return 0;
  }

  /**
   * Calculate possible trading societies.
   */
  public void calculatePossibleTradingSocieties() {
    for (TileWorldObject worldTile : territory) {
      checkPossibleTrading(worldTile.getRow(), worldTile.getColumn());
    }
  }

  private void checkPossibleTrading(int row, int column) {
    TileWorldObject[][] map = World.getWorldMap();
    // Check left side of the territory
    if (map[row][column - 1].isClaimed()
        && map[row][column - 1].getClaimedBy().getSocietyId() != societyId) {
      if (!possibleTradingSocieties.contains(map[row][column - 1].getClaimedBy())) {
        possibleTradingSocieties.add(map[row][column - 1].getClaimedBy());
      }
    }
    // Check right side of the territory
    if (map[row][column + 1].isClaimed()
        && map[row][column + 1].getClaimedBy().getSocietyId() != societyId) {
      if (!possibleTradingSocieties.contains(map[row][column + 1].getClaimedBy())) {
        possibleTradingSocieties.add(map[row][column + 1].getClaimedBy());
      }
    }
    // Check top of territory
    if (map[row - 1][column].isClaimed()
        && map[row - 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!possibleTradingSocieties.contains(map[row - 1][column].getClaimedBy())) {
        possibleTradingSocieties.add(map[row - 1][column].getClaimedBy());
      }
    }
    // check bottom of territory
    if (map[row + 1][column].isClaimed()
        && map[row + 1][column].getClaimedBy().getSocietyId() != societyId) {
      if (!possibleTradingSocieties.contains(map[row + 1][column].getClaimedBy())) {
        possibleTradingSocieties.add(map[row + 1][column].getClaimedBy());
      }
    }
  }

  /**
   * Examine trade deal boolean.
   *
   * @param tradeDeal the trade deal
   * @return the boolean
   */
  public boolean examineTradeDeal(TradeDeal tradeDeal) {
    if (tradeDeal.getSocietyB() == this) {
      int totalFoodReceived = tradeDeal.getFoodGiven() - tradeDeal.getFoodReceived();
      int totalRawMatsReceived = tradeDeal.getRawMatsGiven() - tradeDeal.getRawMatsReceived();
      return checkEnoughFood(totalFoodReceived) && checkEnoughMats(totalRawMatsReceived);
    } else {
      // TODO DRAW SCREEN FOR WHEN PLAYER SOCIETY IS PROPOSED WITH A DEAL
      return false;
    }
  }

  private boolean checkEnoughMats(int totalRawMatsReceived) {
    int rawMatsResource = totalRawMaterialResource + totalRawMatsReceived;
    float rawMatsPerPerson = (float) rawMatsResource / population.size();
    float oldRawMatsPerPerson = ((float) getTotalRawMaterialResource() / population.size());
    return rawMatsPerPerson > MATERIAL_PER_PERSON || rawMatsPerPerson >= oldRawMatsPerPerson;
  }

  private boolean checkEnoughFood(int totalFoodReceived) {
    int foodResource = totalFoodResource + totalFoodReceived;
    float foodPerPerson = (float) foodResource / population.size();
    float oldFoodPerPerson = ((float) getTotalFoodResource() / population.size());
    return foodPerPerson > FOOD_PER_PERSON || foodPerPerson >= oldFoodPerPerson;
  }

  /**
   * Activate trade deal.
   *
   * @param tradeDeal the trade deal
   */
  public void activateTradeDeal(TradeDeal tradeDeal) {
    float happinessModifier = 1f;
    activeTradeDeals.add(tradeDeal);
    if (tradeDeal.getSocietyA() == this) {
      // calculate total resource received from trade deal
      float totalResourcesAdded = tradeDeal.getFoodReceived() + tradeDeal.getRawMatsReceived();
      // calculate total resource lost in trade deal
      float totalResourcesTakenAway = tradeDeal.getFoodGiven() + tradeDeal.getRawMatsGiven();
      // check if either of totals are greater than 0
      if (totalResourcesAdded > 0 || totalResourcesTakenAway > 0) {
        // add or takeaway from the modifier the ratio of total resources lost or gained
        happinessModifier += (totalResourcesAdded - totalResourcesTakenAway)
            / (getTotalFoodResource() + getTotalRawMaterialResource()
            + totalFoodResource - totalRawMaterialResource);
      }
      // set the new happiness
      setHappiness(getHappiness() * happinessModifier);
      foodFromDeals += tradeDeal.getFoodReceived() - tradeDeal.getFoodGiven();
      rawMatsFromDeals += tradeDeal.getRawMatsReceived() - tradeDeal.getRawMatsGiven();

    } else {
      // calculate the amount of resources lost in trade deal
      float totalResourcesTakenAway = tradeDeal.getFoodReceived() + tradeDeal.getRawMatsReceived();
      // calculate the amount of resources gained in trade deal
      float totalResourcesAdded = tradeDeal.getFoodGiven() + tradeDeal.getRawMatsGiven();
      // check whether either total is greater than 0
      if (totalResourcesAdded > 0 || totalResourcesTakenAway > 0) {
        // add or take away from the modifier the ratio of total resources lost or gained.
        happinessModifier += (totalResourcesAdded - totalResourcesTakenAway)
            / (getTotalFoodResource() + getTotalRawMaterialResource()
            + totalFoodResource - totalRawMaterialResource);
      }
      // set the new happiness
      setHappiness(getHappiness() * happinessModifier);
      foodFromDeals += tradeDeal.getFoodGiven() - tradeDeal.getFoodReceived();
      rawMatsFromDeals += tradeDeal.getRawMatsGiven() - tradeDeal.getRawMatsReceived();
    }
    calculateResources();
  }

  /**
   * Terminate trade deal.
   *
   * @param tradeDeal the trade deal
   */
  public void terminateTradeDeal(TradeDeal tradeDeal) {
    if (tradeDeal.getSocietyA() == this) {
      foodFromDeals -= tradeDeal.getFoodReceived() - tradeDeal.getFoodGiven();
      rawMatsFromDeals -= tradeDeal.getRawMatsReceived() - tradeDeal.getRawMatsGiven();
    } else {
      foodFromDeals -= tradeDeal.getFoodGiven() - tradeDeal.getFoodReceived();
      rawMatsFromDeals -= tradeDeal.getRawMatsGiven() - tradeDeal.getRawMatsReceived();
    }
    calculateResources();
  }

  /**
   * Check trade deal.
   */
  public void checkTradeDeal() {
    ArrayList<TradeDeal> newTradeDeals = new ArrayList<>();
    for (TradeDeal tradeDeal : activeTradeDeals) {
      if (!(tradeDeal.getEndTurnOfDeal() == Hud.getTurn())) {
        newTradeDeals.add(tradeDeal);
      } else {
        terminateTradeDeal(tradeDeal);
      }
      setActiveTradeDeals(newTradeDeals);
    }
  }

  /**
   * Check if trading boolean.
   *
   * @param society the society
   * @return the boolean
   */
  public boolean checkIfTrading(Society society) {
    if (society != this) {
      for (TradeDeal tradeDeal : activeTradeDeals) {
        if (society == tradeDeal.getSocietyB()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * The society will check if it can reproduce and will do so if able.
   */
  public void reproduce() {
    // Select the valid population
    ArrayList<Person> validPeople = new ArrayList<>();
    for (Person person : population) {
      if (person.getAge() >= DEFAULT_REPRODUCTION_AGE) {
        validPeople.add(person);
      }
    }
    if (validPeople.size() >= 2) {
      float reproductionRatio = DEFAULT_POPULATION_REPRODUCTION_RATIO;
      float foodRatio = (totalFoodResource * FOOD_PER_PERSON) / population.size();
      // Multiply the rate to get an amount based on the prosperity of the population
      reproductionRatio *= foodRatio;
      // Ensure the rate is within the boundaries
      if (!(Math.abs(reproductionRatio - MIN_REPRODUCTION_RATIO) < .0000001)) {
        reproductionRatio = Math.max(reproductionRatio, MIN_REPRODUCTION_RATIO);
      }
      if (!(Math.abs(reproductionRatio - MAX_REPRODUCTION_RATIO) < .0000001)) {
        reproductionRatio = Math.min(reproductionRatio, MAX_REPRODUCTION_RATIO);
      }
      int reproducingPopulationSize = (int) Math.floor(validPeople.size() * reproductionRatio);
      // Ensure that the reproducing population is even
      if (reproducingPopulationSize % 2 != 0) {
        reproducingPopulationSize = reproducingPopulationSize - 1;
      }
      // Ensure that it is at least 2 people this will be more likely at lower population sizes
      if (reproducingPopulationSize < 2) {
        reproducingPopulationSize = 2;
      }
      // Apply a fitness function to the valid population and sort
      validPeople.sort((person1, person2) ->
          Float.compare(person1.fitnessScore(), person2.fitnessScore()));

      // Crossover to get the initial children
      ArrayList<Person> offspring = new ArrayList<>();
      for (int i = 0; i < reproducingPopulationSize; i = i + 2) {
        offspring.addAll(crossover(validPeople.get(i), validPeople.get(i + 1)));
      }
      // Apply Mutation
      mutate(offspring);
      // Add to population
      population.addAll(offspring);
    }
  }

  private void mutate(ArrayList<Person> people) {
    for (Person person : people) {
      person.setProductiveness(calculateMutation(person.getProductiveness()));
      person.setAggressiveness(calculateMutation(person.getAggressiveness()));
      person.setAttractiveness(calculateMutation(person.getAttractiveness()));
    }
  }

  private float calculateMutation(float gene) {
    Random r = new Random();
    // Check if a mutation should occur
    int randomInt = r.nextInt(100);
    if (randomInt <= (MUTATION_PROBABILITY * 100)) {
      // Apply Mutation
      boolean mutationDirection = r.nextBoolean();
      // select a mutation between the min and the max mutation impact
      float mutationFactor = MIN_MUTATION_FACTOR + r.nextFloat()
          * (MAX_MUTATION_FACTOR - MIN_MUTATION_FACTOR);
      // if gene is zero just add the factor
      if (gene == 0) {
        gene = mutationFactor;
      } else if (mutationDirection) { // if positive add the mutation
        gene = gene + (gene * mutationFactor);
      } else { // subtract the mutation
        gene = gene - (gene * mutationFactor);
      }
      // Ensure gene is between limits
      if (gene != 1) {
        gene = Math.min(gene, 1);
      }
      if (gene != 0) {
        gene = Math.max(gene, 0);
      }
    }
    return gene;
  }

  private ArrayList<Person> crossover(Person parent1, Person parent2) {
    ArrayList<Person> offspring = new ArrayList<>();
    ArrayList<Person> parents = new ArrayList<>();
    parents.add(parent1);
    parents.add(parent2);
    Random r = new Random();
    for (int i = 0; i < OFFSPRING_AMOUNT; i++) {
      // Select the attributes for the child
      Person parentToInherit = parents.get(r.nextInt(parents.size()));
      float productiveness = parentToInherit.getProductiveness();
      parentToInherit = parents.get(r.nextInt(parents.size()));
      float aggressiveness = parentToInherit.getAggressiveness();
      parentToInherit = parents.get(r.nextInt(parents.size()));
      float attractiveness = parentToInherit.getAttractiveness();
      Person child = new Person(0, aggressiveness, attractiveness, productiveness);
      offspring.add(child);
    }
    return offspring;
  }

  /**
   * Calc army aggression float.
   *
   * @return the float
   */
  public float calcArmyAggression() {
    float totalAggression = 0;
    for (Person soldier : army) {
      totalAggression += soldier.getAggressiveness();
    }
    return totalAggression;
  }

  /**
   * Age the population.
   */
  public void agePopulation() {
    ArrayList<Person> passedAway = new ArrayList<>();
    for (Person person : population) {
      if (person.age()) {
        passedAway.add(person);
      }
    }
    // Remove everyone from the population who has died
    population.removeAll(passedAway);
  }

  /**
   * Update happiness.
   */
  public void updateHappiness() {
    // set a base modifier to 1
    float happinessModifier = 1f;
    // calculate current food per person
    float currentFoodPerPerson = (float) getTotalFoodResource() / population.size();
    if (currentFoodPerPerson != 0) {
      // apply that ratio to the happiness modifier
      happinessModifier *= currentFoodPerPerson;
    }
    // calculate current Raw materials per person
    float currentRawMatsPerPerson = (float) getTotalRawMaterialResource() / population.size();
    if (currentRawMatsPerPerson != 0) {
      // apply that ratio to the happiness modifier
      happinessModifier *= currentRawMatsPerPerson;
    }
    // check in case happiness modifier exceeded maximum limit
    if (happinessModifier > 2) {
      happinessModifier = 2f;
    }
    // apply happiness modifier to previous happiness
    setHappiness(getHappiness() * happinessModifier);
  }
}

