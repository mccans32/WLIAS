package map.tiles;

import map.Tile;

public class AridTile extends Tile {
  public static final boolean isPassable = false;
  public float rateOfDecay;

  public AridTile() {
  }

  public float getRateOfDecay() {
    return rateOfDecay;
  }

  public void setRateOfDecay(float rateOfDecay) {
    this.rateOfDecay = rateOfDecay;
  }

  public boolean isPassable() {
    return isPassable;
  }
}
