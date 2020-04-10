package map.tiles;

import engine.graphics.image.Image;

public class AridTile extends Tile {
  private static Image image = new Image("/images/aridTile.jpg");

  private static float attackModifier = 0.3f;


  @Override
  public float getAttackModifier() {
    return attackModifier;
  }

  public static void setAttackModifier(float attackModifier) {
    AridTile.attackModifier = attackModifier;
  }


  public Image getImage() {
    return image;
  }

}
