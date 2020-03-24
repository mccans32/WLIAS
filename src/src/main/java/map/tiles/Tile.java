package map.tiles;


import engine.graphics.image.Image;

public abstract class Tile {
  private static final String TILE_TYPE = "Generic";
  private static Image image = new Image();
  private float positionX;
  private float positionY;
  private float terrainHealth;
  private boolean claimed;

  public float getPositionX() {
    return positionX;
  }

  public void setPositionX(float positionX) {
    this.positionX = positionX;
  }

  public float getPositionY() {
    return positionY;
  }

  public void setPositionY(float positionY) {
    this.positionY = positionY;
  }

  public float[] getPositions() {
    return new float[] {positionX, positionY};
  }

  public void setPositions(float positionX, float positionY) {
    this.positionX = positionX;
    this.positionY = positionY;
  }

  public float getTerrainHealth() {
    return terrainHealth;
  }

  public void setTerrainHealth(float terrainHealth) {
    this.terrainHealth = terrainHealth;
  }

  public Image getImage() {
    return image;
  }

  public String getTileType() {
    return TILE_TYPE;
  }
}
