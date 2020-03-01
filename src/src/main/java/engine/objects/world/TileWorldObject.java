package engine.objects.world;

import engine.graphics.Mesh;
import map.tiles.Tile;
import math.Vector3f;

public class TileWorldObject extends GameObject {
  private Tile tile;

  /**
   * Instantiates a new Tile object.
   *
   * @param position the position
   * @param rotation the rotation
   * @param scale    the scale
   * @param mesh     the mesh
   * @param tile     the tile
   */
  public TileWorldObject(
      Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh, Tile tile) {
    super(position, rotation, scale, mesh);
    this.tile = tile;
    this.getMesh().getMaterial().setTexture(tile.getImage());
  }

  public Tile getTile() {
    return tile;
  }
}
