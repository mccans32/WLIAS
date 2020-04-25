package structures;

public class NodeGene extends Gene {
  private double valueX;
  private double valueY;

  public NodeGene(int innovationNumber) {
    super(innovationNumber);
  }

  public double getX() {
    return valueX;
  }

  public void setX(double x) {
    this.valueX = x;
  }

  public double getY() {
    return valueY;
  }

  public void setY(double y) {
    this.valueY = y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NodeGene nodeGene = (NodeGene) o;
    return Double.compare(nodeGene.valueX, valueX) == 0
        && Double.compare(nodeGene.valueY, valueY) == 0;
  }

  @Override
  public int hashCode() {
    return innovationNumber;
  }
}
