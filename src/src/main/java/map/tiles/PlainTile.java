package map.tiles;

import engine.graphics.image.Image;

public class PlainTile extends Tile {
  private static Image tileImage = new Image("/images/plainTile.jpg");

  public Image getImage() {
    return tileImage;
  }


}
