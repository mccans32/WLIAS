package structures;

import java.util.Objects;

public class ConnectionGene extends Gene {

  private NodeGene fromGene;
  private NodeGene toGene;

  private double weight;
  private boolean enabled = true;

  public ConnectionGene(NodeGene fromGene, NodeGene toGene) {
    this.fromGene = fromGene;
    this.toGene = toGene;
  }

  public NodeGene getFromGene() {
    return fromGene;
  }

  public void setFromGene(NodeGene fromGene) {
    this.fromGene = fromGene;
  }

  public NodeGene getToGene() {
    return toGene;
  }

  public void setToGene(NodeGene toGene) {
    this.toGene = toGene;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConnectionGene that = (ConnectionGene) o;
    return Objects.equals(fromGene, that.fromGene)
        && Objects.equals(toGene, that.toGene);
  }

  @Override
  public int hashCode() {
    return fromGene.getInnovationNumber() * Neat.MAX_NODES + toGene.getInnovationNumber();
  }
}
