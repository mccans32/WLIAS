package map.tiles;

import engine.graphics.image.Image;

public class FertileTile extends Tile {
  private static final String TILE_TYPE = "Fertile";
  private static Image image = new Image("/images/fertileTile.jpg");

  public Image getImage() {
    return image;
  }

  @Override
  public String getTileType() {
    return TILE_TYPE;
  }
}
