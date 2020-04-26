package neat.calculations;

import java.util.ArrayList;
import java.util.Objects;

public class Node implements Comparable<Node> {

  private double valueX;
  private double output;
  private ArrayList<Connection> connections = new ArrayList<>();

  public Node(double valueX) {
    this.valueX = valueX;
  }

  /**
   * Calculate the output for this node.
   */
  public void calculate() {
    double s = 0;
    for (Connection c : connections) {
      if (c.isEnabled()) {
        s += c.getWeight() * c.getFrom().getOutput();
      }
    }
    output = activation_function(s);
  }

  private double activation_function(double x) {
    return 1d / (1 + Math.exp(-x));
  }

  public double getX() {
    return valueX;
  }

  public void setX(double x) {
    this.valueX = x;
  }

  public double getOutput() {
    return output;
  }

  public void setOutput(double output) {
    this.output = output;
  }

  public ArrayList<Connection> getConnections() {
    return connections;
  }

  public void setConnections(ArrayList<Connection> connections) {
    this.connections = connections;
  }

  @Override
  public int compareTo(Node o) {
    return Double.compare(o.valueX, this.valueX);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Node node = (Node) o;
    return Double.compare(node.valueX, valueX) == 0
        && Double.compare(node.output, output) == 0
        && Objects.equals(connections, node.connections);
  }

  @Override
  public int hashCode() {
    return Objects.hash(valueX, output, connections);
  }
}
