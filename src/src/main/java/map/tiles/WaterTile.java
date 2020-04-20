package map.tiles;

import engine.graphics.image.Image;

public class WaterTile extends Tile {
  private static Image image = new Image("/images/waterTile.png");
  private static float attackModifier = 0.9f;

  public Image getImage() {
    return image;
  }

  @Override
  public float getAttackModifier() {
    return attackModifier;
  }

  public static void setAttackModifier(float attackModifier) {
    WaterTile.attackModifier = attackModifier;
  }


}