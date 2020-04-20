package map.tiles;

import engine.graphics.image.Image;

public class FertileTile extends Tile {
  private static Image image = new Image("/images/fertileTile.jpg");

  private static float attackModifier = 1f;


  @Override
  public float getAttackModifier() {
    return attackModifier;
  }

  public static void setAttackModifier(float attackModifier) {
    FertileTile.attackModifier = attackModifier;
  }


  public Image getImage() {
    return image;
  }
}
