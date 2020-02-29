package engine.objects.world;

import engine.graphics.Mesh;
import map.tiles.Tile;
import math.Vector3f;

public class TileObject extends GameObject {
  private Tile tile;

  public TileObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh, Tile tile) {
    super(position, rotation, scale, mesh);
    this.tile = tile;
    this.getMesh().getMaterial().setTexture(tile.getImage());
  }

  public Tile getTile() {
    return tile;
  }
}
