package map.tiles;

import engine.graphics.image.Image;

public class WaterTile extends Tile {

  private static final String TILE_TYPE = "Water";
  private static Image image = new Image("/images/waterTile.png");

  public Image getImage() {
    return image;
  }

  @Override
  public String getTileType() {
    return TILE_TYPE;
  }
}
