package map.tiles;

import map.Tile;

public class FertileTile extends Tile {
  public static final boolean isPassable = true;
  public float rateOfDecay;

  public FertileTile() {
  }

  public boolean isPassable() {
    return isPassable;
  }

  public float getRateOfDecay() {
    return rateOfDecay;
  }

  public void setRateOfDecay(float rateOfDecay) {
    this.rateOfDecay = rateOfDecay;
  }
}
