package neat;

import java.util.HashMap;
import neat.genomes.ConnectionGene;
import neat.genomes.Genome;
import neat.genomes.NodeGene;
import neat.genomes.RandomHashSet;
import neat.genomes.RandomSelector;

public class Neat {

  public static final int MAX_NODES = (int) Math.pow(2, 20);


  private static final double C1 = 1;
  private static final double C2 = 1;
  private static final double C3 = 1;
  private static final double CP = 4;

  private static final double WEIGHT_SHIFT_STRENGTH = 0.3;
  private static final double WEIGHT_RANDOM_STRENGTH = 1;

  private static final double SURVIVOR_PERCENTAGE = 0.8;

  private static final double PROBABILITY_MUTATE_LINK = 0.01;
  private static final double PROBABILITY_MUTATE_NODE = 0.1;
  private static final double PROBABILITY_MUTATE_WEIGHT_SHIFT = 0.02;
  private static final double PROBABILITY_MUTATE_WEIGHT_RANDOM = 0.02;
  private static final double PROBABILITY_MUTATE_TOGGLE_LINK = 0;

  private HashMap<ConnectionGene, ConnectionGene> allConnections = new HashMap<>();
  private RandomHashSet<NodeGene> allNodes = new RandomHashSet<>();

  private RandomHashSet<Client> clients = new RandomHashSet<>();
  private RandomHashSet<Species> species = new RandomHashSet<>();

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
    c.setInnovationNumber(con.getInnovationNumber());
    c.setWeight(con.getWeight());
    c.setEnabled(con.isEnabled());
    return c;
  }

  /**
   * Gets a new connection with two provided nodes.
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
   * Creates a new empty genome.
   *
   * @return the genome
   */
  public Genome emptyGenome() {
    Genome g = new Genome(this);
    for (int i = 0; i < inputSize + outputSize; i++) {
      g.getNodes().add(getNode(i + 1));
    }
    return g;
  }

  /**
   * Reset the structure.
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
    this.clients.clear();

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

    for (int i = 0; i < maxClients; i++) {
      Client c = new Client();
      c.setGenome(emptyGenome());
      c.generate_calculator();
      this.clients.add(c);
    }
  }

  public Client getClient(int index) {
    return clients.get(index);
  }

  public void setReplaceIndex(NodeGene node1, NodeGene node2, int index) {
    allConnections.get(new ConnectionGene(node1, node2)).setReplaceIndex(index);
  }

  /**
   * Gets replace index.
   * This is used when we are replacing where an existing node is.
   *
   * @param node1 the node 1
   * @param node2 the node 2
   * @return the replace index
   */
  public int getReplaceIndex(NodeGene node1, NodeGene node2) {
    ConnectionGene con = new ConnectionGene(node1, node2);
    ConnectionGene data = allConnections.get(con);
    if (data == null) {
      return 0;
    }
    return data.getReplaceIndex();
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

  /**
   * Evolves the network.
   */
  public void evolve() {

    genSpecies();
    kill();
    removeExtinctSpecies();
    reproduce();
    mutate();
    for (Client c : clients.getData()) {
      c.generate_calculator();
    }
  }

  /**
   * Prints the species information.
   */
  public void printSpecies() {
    System.out.println("##########################################");
    for (Species s : this.species.getData()) {
      System.out.println(s + "  " + s.getScore() + "  " + s.size());
    }
  }

  private void reproduce() {
    RandomSelector<Species> selector = new RandomSelector<>();
    for (Species s : species.getData()) {
      selector.add(s, s.getScore());
    }

    for (Client c : clients.getData()) {
      if (c.getSpecies() == null) {
        Species s = selector.random();
        c.setGenome(s.breed());
        s.forcePut(c);
      }
    }
  }

  /**
   * Mutates the clients.
   */
  public void mutate() {
    for (Client c : clients.getData()) {
      c.mutate();
    }
  }

  private void removeExtinctSpecies() {
    for (int i = species.size() - 1; i >= 0; i--) {
      if (species.get(i).size() <= 1) {
        species.get(i).goExtinct();
        species.remove(i);
      }
    }
  }

  private void genSpecies() {
    for (Species s : species.getData()) {
      s.reset();
    }

    for (Client c : clients.getData()) {
      if (c.getSpecies() != null) {
        continue;
      }


      boolean found = false;
      for (Species s : species.getData()) {
        if (s.put(c)) {
          found = true;
          break;
        }
      }
      if (!found) {
        species.add(new Species(c));
      }
    }

    for (Species s : species.getData()) {
      s.evaluateScore();
    }
  }

  private void kill() {
    for (Species s : species.getData()) {
      s.kill(1 - SURVIVOR_PERCENTAGE);
    }
  }

  public double getCP() {
    return CP;
  }

  public double getC1() {
    return C1;
  }

  public double getC2() {
    return C2;
  }

  public double getC3() {
    return C3;
  }


  public double getWeightShiftStrength() {
    return WEIGHT_SHIFT_STRENGTH;
  }

  public double getWeightRandomStrength() {
    return WEIGHT_RANDOM_STRENGTH;
  }

  public double getProbabilityMutateLink() {
    return PROBABILITY_MUTATE_LINK;
  }

  public double getProbabilityMutateNode() {
    return PROBABILITY_MUTATE_NODE;
  }

  public double getProbabilityMutateWeightShift() {
    return PROBABILITY_MUTATE_WEIGHT_SHIFT;
  }

  public double getProbabilityMutateWeightRandom() {
    return PROBABILITY_MUTATE_WEIGHT_RANDOM;
  }

  public double getProbabilityMutateToggleLink() {
    return PROBABILITY_MUTATE_TOGGLE_LINK;
  }

  public int getOutputSize() {
    return outputSize;
  }

  public int getInputSize() {
    return inputSize;
  }


  public RandomHashSet<Species> getSpecies() {
    return species;
  }

  public RandomHashSet<Client> getClients() {
    return clients;
  }
}