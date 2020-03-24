package engine.objects.world;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import map.tiles.Tile;
import math.Vector3f;
import math.Vector4f;

public class TileWorldObject extends GameObject {
  private static final float BORDER_ALPHA = 1.5f;
  private static final float BORDER_RAISE = 0.05f;
  private static final Image borderImage = new Image("/images/tileBorder.png");
  private Tile tile;
  private boolean isClaimed = false;
  private GameObject borderObject;
  private RectangleMesh borderMesh;
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
    this.getMesh().getMaterial().setImage((tile.getImage()));
  }

  public GameObject getBorderObject() {
    return this.borderObject;
  }

  public RectangleMesh getBorderMesh() {
    return borderMesh;
  }


  public void applyColorOffset(Vector3f societyColor) {
    this.getMesh().getMaterial().setColorOffset(societyColor);
  }

  public boolean isClaimed() {
    return isClaimed;
  }

  public void setClaimed(boolean claimed) {
    isClaimed = claimed;
  }

  /**
   * Sets border mesh which will indicate which society owns which tile.
   *
   * @param societyColor the society color
   * @param model        the model
   */
  public void setBorderMesh(Vector3f societyColor, RectangleModel model) {
    Material borderMaterial = new Material(borderImage, new Vector4f(societyColor, BORDER_ALPHA));
    borderMesh = new RectangleMesh(model, borderMaterial);
    Vector3f borderPositions = new Vector3f(
        this.getPosition().getX(),
        this.getPosition().getY(),
        this.getPosition().getZ() + BORDER_RAISE);
    this.borderObject = new GameObject(borderPositions, borderMesh);
    borderObject.setScale(this.getScale());
    borderObject.setRotation(this.getRotation());
    borderObject.create();
  }

  public Tile getTile() {
    return tile;
  }

  public String getTileType() {
    return this.tile.getTileType();
  }


}
