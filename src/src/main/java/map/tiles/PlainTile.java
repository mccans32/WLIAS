package map.tiles;

public class PlainTile extends Tile {
  private String tileImage = "/images/plainTile.jpg";

  @Override
  public String getImage() {
    return tileImage;
  }

}
