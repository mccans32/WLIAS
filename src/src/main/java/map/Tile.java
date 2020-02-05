package map;

public class Tile {

  public float positionX;
  public float positionY;
  public float terrainHealth;

  public Tile() {
  }

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

  public float getTerrainHealth() {
    return terrainHealth;
  }

  public void setTerrainHealth(float terrainHealth) {
    this.terrainHealth = terrainHealth;
  }
}
