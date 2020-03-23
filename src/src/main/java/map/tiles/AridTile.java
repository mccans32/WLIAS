package map.tiles;

import engine.graphics.image.Image;

public class AridTile extends Tile {
  private static Image image = new Image("/images/aridTile.jpg");

  public Image getImage() {
    return image;
  }
}
