package map.tiles;

import engine.graphics.image.Image;

public class PlainTile extends Tile {
  private static Image tileImage = new Image("/images/plainTile.jpg");

  private static float attackModifier = 1.1f;

  @Override
  public float getAttackModifier() {
    return attackModifier;
  }

  public static void setAttackModifier(float attackModifier) {
    PlainTile.attackModifier = attackModifier;
  }

  public Image getImage() {
    return tileImage;
  }


}
