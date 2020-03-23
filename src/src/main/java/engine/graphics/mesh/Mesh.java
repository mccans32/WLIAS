package engine.graphics.mesh;

import engine.graphics.Material;
import engine.graphics.model.Model;

/**
 * The type Mesh.
 */
public class Mesh {
  private Model model;
  private Material material;

  public Mesh(Model model, Material material) {
    this.model = model;
    this.material = material;
  }

  protected Mesh(Material material) {
    this.material = material;
  }

  public Material getMaterial() {
    return material;
  }

  /**
   * Create function for Text Objects.
   */
  public void createText() {
    // Create Vertex Array Object and Bind it
    model.create();
  }

  /**
   * Create function for non-Text Objects.
   */
  public void create() {
    //Create the Material
    material.create();
    // Create Vertex Array Object and Bind it
    model.create();
  }

  public Model getModel() {
    return model;
  }

  public void setModel(Model model) {
    this.model = model;
  }

  /**
   * Destroy.
   */
  public void destroy() {
    model.destroy();
    material.destroy();
  }
}