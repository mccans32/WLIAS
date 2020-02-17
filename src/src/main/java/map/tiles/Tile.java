package map.tiles;

public abstract class Tile {

  public float positionX;
  public float positionY;
  public float terrainHealth;

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
}
