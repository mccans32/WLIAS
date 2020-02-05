package map.tiles;

import map.Tile;

public class PlainTile extends Tile {

  public static final boolean isPassable = true;

  public PlainTile() {
  }

  public boolean isPassable() {
    return isPassable;
  }
}
