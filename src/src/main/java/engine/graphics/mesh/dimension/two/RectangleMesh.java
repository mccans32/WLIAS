package engine.graphics.mesh.dimension.two;

import engine.graphics.Material;
import engine.graphics.mesh.Mesh;
import engine.graphics.model.dimension.two.RectangleModel;

public class RectangleMesh extends Mesh {

  public RectangleMesh(RectangleModel model, Material material) {
    super(model, material);
  }

  public RectangleMesh(RectangleModel model) {
    this(model, new Material());
  }
}
