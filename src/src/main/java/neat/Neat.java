package neat;

import java.util.HashMap;
import neat.genomes.ConnectionGene;
import neat.genomes.Genome;
import neat.genomes.NodeGene;
import neat.genomes.RandomHashSet;
import neat.visual.Frame;

public class Neat {

  public static final int MAX_NODES = (int) Math.pow(2, 20);
  public static final double C1 = 1;
  public static final double C2 = 1;
  public static final double C3 = 1;
  public static final double WEIGHT_SHIFT_STRENGTH = 0.3;
  public static final double WEIGHT_RANDOM_STRENGTH = 1;
  public static final double PROBABILITY_MUTATE_LINK = 0.4;
  public static final double PROBABILITY_MUTATE_NODE = 0.4;
  public static final double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.4;
  public static final double PROBABILITY_MUTATE_WEIGHT_RANDOM = 0.4;
  public static final double PROBABILITY_MUTATE_TOGGLE_LINK = 0.4;

  private HashMap<ConnectionGene, ConnectionGene> allConnections = new HashMap<>();
  private RandomHashSet<NodeGene> allNodes = new RandomHashSet<>();
  private int maxClients;
  private int outputSize;
  private int inputSize;

  public Neat(int inputSize, int outputSize, int clients) {
    this.reset(inputSize, outputSize, clients);
  }

  /**
   * Gets a new connection.
   *
   * @param con the con
   * @return the connection
   */
  public static ConnectionGene getConnection(ConnectionGene con) {
    ConnectionGene c = new ConnectionGene(con.getFrom(), con.getTo());
    c.setWeight(con.getWeight());
    c.setEnabled(con.isEnabled());
    return c;
  }

  /**
   * Gets a new connection with to given nodes.
   *
   * @param node1 the node 1
   * @param node2 the node 2
   * @return the connection
   */
  public ConnectionGene getConnection(NodeGene node1, NodeGene node2) {
    ConnectionGene connectionGene = new ConnectionGene(node1, node2);

    if (allConnections.containsKey(connectionGene)) {
      connectionGene.setInnovationNumber(allConnections.get(connectionGene).getInnovationNumber());
    } else {
      connectionGene.setInnovationNumber(allConnections.size() + 1);
      allConnections.put(connectionGene, connectionGene);
    }

    return connectionGene;
  }

  public static void main(String[] args) {
    Neat neat = new Neat(3, 2, 0);
    new Frame(neat.createEmptyGenome());
  }

  public int getMaxClients() {
    return maxClients;
  }

  /**
   * Create an empty genome.
   *
   * @return the genome
   */
  public Genome createEmptyGenome() {
    Genome g = new Genome(this);
    for (int i = 0; i < inputSize + outputSize; i++) {
      g.getNodes().add(getNode(i + 1));
    }
    return g;
  }

  /**
   * Reset.
   *
   * @param inputSize  the input size
   * @param outputSize the output size
   * @param clients    the clients
   */
  public void reset(int inputSize, int outputSize, int clients) {
    this.inputSize = inputSize;
    this.outputSize = outputSize;
    this.maxClients = clients;

    allConnections.clear();
    allNodes.clear();

    for (int i = 0; i < inputSize; i++) {
      NodeGene n = getNode();
      n.setX(0.1);
      n.setY((i + 1) / (double) (inputSize + 1));
    }

    for (int i = 0; i < outputSize; i++) {
      NodeGene n = getNode();
      n.setX(0.9);
      n.setY((i + 1) / (double) (outputSize + 1));
    }

  }

  /**
   * Gets a new node.
   *
   * @return the node
   */
  public NodeGene getNode() {
    NodeGene n = new NodeGene(allNodes.size() + 1);
    allNodes.add(n);
    return n;
  }

  /**
   * Gets a node given an index.
   *
   * @param id the id
   * @return the node
   */
  public NodeGene getNode(int id) {
    if (id <= allNodes.size()) {
      return allNodes.get(id - 1);
    }
    return getNode();
  }

  public int getOutputSize() {
    return outputSize;
  }

  public int getInputSize() {
    return inputSize;
  }
}