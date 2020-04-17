package engine.objects.gui;

import engine.graphics.mesh.dimension.two.RectangleMesh;
import engine.graphics.text.Text;
import society.Society;

public class SocietyButton extends ButtonObject {
  private Society society;

  public SocietyButton(RectangleMesh backgroundMesh, Text text, float edgeX,
                       float offsetX, float edgeY, float offsetY, Society society) {
    super(backgroundMesh, text, edgeX, offsetX, edgeY, offsetY);
    this.society = society;
  }

  public Society getSociety() {
    return society;
  }

  public void setSociety(Society society) {
    this.society = society;
  }
}
