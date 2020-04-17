package game.menu.data;

import society.Society;

public class TradeDeal {
  private Society societyA;
  private Society societyB;
  private int foodGiven;
  private int foodReceived;
  private int rawMatsReceived;
  private int rawMarsGiven;
  private int endTurnOfDeal;

  /**
   * Instantiates a new Trade deal.
   *
   * @param foodGiven       the food given
   * @param foodReceived    the food received
   * @param rawMatsReceived the raw mats received
   * @param rawMarsGiven    the raw mars given
   */
  public TradeDeal(int foodGiven, int foodReceived, int rawMatsReceived, int rawMarsGiven) {
    this.foodGiven = foodGiven;
    this.foodReceived = foodReceived;
    this.rawMatsReceived = rawMatsReceived;
    this.rawMarsGiven = rawMarsGiven;
  }

  public int getEndTurnOfDeal() {
    return endTurnOfDeal;
  }

  public void setEndTurnOfDeal(int endTurnOfDeal) {
    this.endTurnOfDeal = endTurnOfDeal;
  }

  public Society getSocietyA() {
    return societyA;
  }

  public void setSocietyA(Society societyA) {
    this.societyA = societyA;
  }

  public Society getSocietyB() {
    return societyB;
  }

  public void setSocietyB(Society societyB) {
    this.societyB = societyB;
  }

  public int getFoodGiven() {
    return foodGiven;
  }

  public void setFoodGiven(int foodGiven) {
    this.foodGiven = foodGiven;
  }

  public int getFoodReceived() {
    return foodReceived;
  }

  public void setFoodReceived(int foodReceived) {
    this.foodReceived = foodReceived;
  }

  public int getRawMatsReceived() {
    return rawMatsReceived;
  }

  public void setRawMatsReceived(int rawMatsReceived) {
    this.rawMatsReceived = rawMatsReceived;
  }

  public int getRawMatsGiven() {
    return rawMarsGiven;
  }

  public void setRawMarsGiven(int rawMarsGiven) {
    this.rawMarsGiven = rawMarsGiven;
  }
}
