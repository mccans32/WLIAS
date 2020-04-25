package structures;

import java.util.ArrayList;
import java.util.HashMap;

public class Neat {
  public static final int GENE_MIN = 20;
  public static final int MAX_NODES = (int) Math.pow(2, 20);
  public static final double PROBABILITY_MUTATE_LINK = 0.4;
  public static final double PROBABILITY_MUTATE_NODE = 0.4;
  public static final double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.4;
  public static final double PROBABILITY_MUTATE_WEIGHT_RANDOM = 0.4;
  public static final double PROBABILITY_MUTATE_TOGGLE_LINK = 0.4;
  public static final int C1 = 1;
  public static final int C2 = 1;
  public static final int C3 = 1;
  public static final double WEIGHT_SHIFT_STRENGTH = 0.3;
  public static final double WEIGHT_RANDOM_STRENGTH = 1;
  private HashMap<ConnectionGene, ConnectionGene> allConnections = new HashMap<>();
  private ArrayList<NodeGene> allNodes = new ArrayList<>();
  private int inputSize;
  private int outputSize;
  private Object maxClients;

  public Object getMaxClients() {
    return maxClients;
  }

  public Neat(int inputSize, int outputSize, int clients) {
    this.reset(inputSize, outputSize, clients);
  }

  /**
   * Gets a connection that is a copy of the provided connection.
   *
   * @param connectionGene1 the connection gene 1
   * @return the connection
   */
  public static ConnectionGene getConnection(ConnectionGene connectionGene1) {
    ConnectionGene connectionGene2 = new ConnectionGene(connectionGene1.getFromGene(),
        connectionGene1.getToGene());
    connectionGene2.setWeight(connectionGene1.getWeight());
    connectionGene2.setEnabled(connectionGene1.isEnabled());
    return connectionGene2;
  }

  /**
   * Gets a connection and adds to allConnections if it does nto already exist.
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

  /**
   * Create an empty genome genome.
   *
   * @return the genome
   */
  public Genome createEmptyGenome() {
    Genome genome = new Genome(this);
    for (int i = 0; i < inputSize + outputSize; i++) {
      genome.getNodeGenes().add(getNode(i + 1));
    }
    return genome;
  }

  /**
   * Resets everything.
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

    // Create the input nodes
    for (int i = 0; i < inputSize; i++) {
      NodeGene inputNode = getNode();
      inputNode.setX(0.1);
      inputNode.setY((i + 1) / (double) (inputSize + 1));
    }
    // Create the output nodes
    for (int i = 0; i < outputSize; i++) {
      NodeGene outputNode = getNode();
      outputNode.setX(0.9);
      outputNode.setY((i + 1) / (double) (outputSize + 1));
    }
  }

  /**
   * Gets a new node.
   *
   * @return the node
   */
  public NodeGene getNode() {
    NodeGene node = new NodeGene(allNodes.size() + 1);
    allNodes.add(node);
    return node;
  }

  /**
   * Gets a node form data based off of its index.
   *
   * @param id the id
   * @return the node
   */
  public NodeGene getNode(int id) {
    if (id >= 0 && id < allNodes.size()) {
      return allNodes.get(id);
    }
    return getNode();
  }
}
