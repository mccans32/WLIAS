package map.tiles;

import engine.graphics.image.Image;

public class FertileTile extends Tile {
  private Image image = new Image("/images/fertileTile.jpg");

  public Image getImage() {
    return image;
  }
}
