package engine.objects.world;

import engine.graphics.Material;
import engine.graphics.image.Image;
import engine.graphics.mesh.Mesh;
import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.model.dimension.two.RectangleModel;
import map.tiles.Tile;
import math.Vector3f;
import math.Vector4f;
import society.Society;

public class TileWorldObject extends GameObject {
  private static final float BORDER_ALPHA = 1.5f;
  private static final Image borderImage = new Image("/images/tileBorder2.png");
  private Tile tile;
  private boolean isClaimed = false;
  private GameObject borderObject;
  private RectangleMesh borderMesh;
  private int row;
  private int column;
  private int foodResource;
  private int rawMaterialResource;
  private Society claimedBy;

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
      Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh, Tile tile, int row,
      int column) {
    super(position, rotation, scale, mesh);
    this.tile = tile;
    this.getMesh().getMaterial().setImage((tile.getImage()));
    this.row = row;
    this.column = column;
  }

  public int getFoodResource() {
    return foodResource;
  }

  public void setFoodResource(int foodResource) {
    this.foodResource = foodResource;
  }

  public int getRawMaterialResource() {
    return rawMaterialResource;
  }

  public void setRawMaterialResource(int rawMaterialResource) {
    this.rawMaterialResource = rawMaterialResource;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
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
    this.borderObject = new GameObject(this.getPosition(), borderMesh);
    borderObject.setScale(this.getScale());
    borderObject.setRotation(this.getRotation());
    borderObject.create();
  }

  public Tile getTile() {
    return tile;
  }

  public Society getClaimedBy() {
    return claimedBy;
  }

  public void setClaimedBy(Society claimedBy) {
    this.claimedBy = claimedBy;
  }

  /**
   * Calculate the desirability of this to be attacked by a given society.
   *
   * @param society the society
   * @return the float
   */
  public float getDefendingDesirability(Society society) {
    int popSize = society.getPopulation().size();
    float foodRatio = society.getTotalFoodResource() / (popSize * Society.getFoodPerPerson());
    float matRatio = society.getTotalRawMaterialResource()
        / (popSize * Society.getMaterialPerPerson());
    return ((foodResource * foodRatio) + (rawMaterialResource * matRatio))
        / tile.getAttackModifier();
  }


  /**
   * Gets the desirability of attacking with this tile.
   *
   * @return the attacking desirability
   */
  public float getAttackingDesirability() {
    float bestScore = 0;
    claimedBy.calculateDefendingTiles(this);
    for (TileWorldObject tile : claimedBy.getDefendingTiles()) {
      if (tile.getDefendingDesirability(claimedBy) > bestScore) {
        bestScore = tile.getDefendingDesirability(claimedBy);
      }
    }
    return (bestScore * tile.getAttackModifier());
  }
}
