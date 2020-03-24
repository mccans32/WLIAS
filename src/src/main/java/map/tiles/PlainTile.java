package map.tiles;

import engine.graphics.image.Image;

public class PlainTile extends Tile {
  private static final String TILE_TYPE = "Plain";
  private static Image tileImage = new Image("/images/plainTile.jpg");

  public Image getImage() {
    return tileImage;
  }

  @Override
  public String getTileType() {
    return TILE_TYPE;
  }

}
