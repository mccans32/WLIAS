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

    // For each node in our genome
    for (NodeGene n : nodes.getData()) {
      // Make a new generic node
      Node node = new Node(n.getX());
      // map this nodes "layer" to itself for reference
      nodeHashMap.put(n.getInnovationNumber(), node);

      if (n.getX() <= 0.1) {
        inputNodes.add(node);
      } else if (n.getX() >= 0.9) {
        outputNodes.add(node);
      } else {
        hiddenNodes.add(node);
      }
    }

    // Sort the hidden nodes by their layer
    hiddenNodes.sort(Node::compareTo);

    // For each connection
    for (ConnectionGene c : cons.getData()) {
      // Get the node coming from
      NodeGene from = c.getFrom();
      // Get the node going to
      NodeGene to = c.getTo();

      // Make two reference nodes for coming form and to
      Node nodeFrom = nodeHashMap.get(from.getInnovationNumber());
      Node nodeTo = nodeHashMap.get(to.getInnovationNumber());

      // Set up a new connection reference
      Connection con = new Connection(nodeFrom, nodeTo);
      con.setWeight(c.getWeight());
      con.setEnabled(c.isEnabled());

      // Add connection reference to connection to
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
    // Set the output for the input nodes to be themselves
    for (int i = 0; i < inputNodes.size(); i++) {
      inputNodes.get(i).setOutput(input[i]);
    }

    // calculate the output for each hidden node
    for (Node n : hiddenNodes) {
      n.calculate();
    }

    // Calculate the outputs
    double[] output = new double[outputNodes.size()];
    for (int i = 0; i < outputNodes.size(); i++) {
      outputNodes.get(i).calculate();
      output[i] = outputNodes.get(i).getOutput();
    }
    return output;
  }

}