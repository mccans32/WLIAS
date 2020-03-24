package map.tiles;

import engine.graphics.image.Image;

public class AridTile extends Tile {
  private static final String TILE_TYPE = "Arid";
  private static Image image = new Image("/images/aridTile.jpg");

  public Image getImage() {
    return image;
  }

  @Override
  public String getTileType() {
    return TILE_TYPE;
  }
}
