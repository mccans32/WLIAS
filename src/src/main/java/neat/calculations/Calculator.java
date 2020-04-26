package neat.calculations;

import java.util.ArrayList;
import java.util.HashMap;
import neat.genomes.ConnectionGene;
import neat.genomes.Genome;
import neat.genomes.NodeGene;
import neat.genomes.RandomHashSet;

public class Calculator {

  private ArrayList<Node> inputNodes = new ArrayList<>();
  private ArrayList<Node> hiddenNodes = new ArrayList<>();
  private ArrayList<Node> outputNodes = new ArrayList<>();

  /**
   * Instantiates a new Calculator.
   *
   * @param g the g
   */
  public Calculator(Genome g) {
    RandomHashSet<NodeGene> nodes = g.getNodes();
    RandomHashSet<ConnectionGene> cons = g.getConnections();

    HashMap<Integer, Node> nodeHashMap = new HashMap<>();

    for (NodeGene n : nodes.getData()) {

      Node node = new Node(n.getX());
      nodeHashMap.put(n.getInnovationNumber(), node);

      if (n.getX() <= 0.1) {
        inputNodes.add(node);
      } else if (n.getX() >= 0.9) {
        outputNodes.add(node);
      } else {
        hiddenNodes.add(node);
      }
    }

    hiddenNodes.sort(Node::compareTo);

    for (ConnectionGene c : cons.getData()) {
      NodeGene from = c.getFrom();
      NodeGene to = c.getTo();

      Node nodeFrom = nodeHashMap.get(from.getInnovationNumber());
      Node nodeTo = nodeHashMap.get(to.getInnovationNumber());

      Connection con = new Connection(nodeFrom, nodeTo);
      con.setWeight(c.getWeight());
      con.setEnabled(c.isEnabled());

      nodeTo.getConnections().add(con);
    }
  }

  /**
   * Calculate the output for the given nodes given the input.
   *
   * @param input the input
   * @return the double [ ]
   */
  public double[] calculate(double... input) {

    if (input.length != inputNodes.size()) {
      throw new RuntimeException("Data doesnt fit");
    }
    for (int i = 0; i < inputNodes.size(); i++) {
      inputNodes.get(i).setOutput(input[i]);
    }
    for (Node n : hiddenNodes) {
      n.calculate();
    }

    double[] output = new double[outputNodes.size()];
    for (int i = 0; i < outputNodes.size(); i++) {
      outputNodes.get(i).calculate();
      output[i] = outputNodes.get(i).getOutput();
    }
    return output;
  }

}