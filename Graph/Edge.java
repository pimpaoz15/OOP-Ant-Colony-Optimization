package Graph;

/**
 * This class is responsible for defining an edge and its properties - source
 * node, destination node, pheromone level, and weight.
 */
public class Edge {
    private Node node1;
    private Node node2;
    private double pheromoneLevel;
    private int weight;

    /**
     * Constructs an edge between two nodes with the specified weight.
     *
     * @param node1  The first node in the edge
     * @param node2  The second node in the edge
     * @param weight The weight of the edge
     */
    public Edge(Node node1, Node node2, int weight) {
        this.node1 = node1;
        this.node2 = node2;
        this.pheromoneLevel = 0.0;
        this.weight = weight;
    }

    /**
     * Returns the source node of the edge.
     *
     * @return The first node in the edge
     */
    public Node getSource() {
        return node1;
    }

    /**
     * Returns the destination node of the edge.
     *
     * @return The node that the edge is connected to
     */
    public Node getDestination() {
        return node2;
    }

    /**
     * Returns the pheromone level of the edge.
     *
     * @return The pheromone level of the edge
     */
    public double getPheromoneLevel() {
        return pheromoneLevel;
    }

    /**
     * Returns the weight of the edge.
     *
     * @return The weight of the edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Checks if the edge contains the given node.
     *
     * @param node The node to check
     * @return True if the node is equal to either of the two nodes in the edge
     */
    public boolean containsNode(Node node) {
        return node1.equals(node) || node2.equals(node);
    }

    /**
     * Returns the node that is not equal to the inputted node.
     *
     * @param node The node to compare
     * @return The other node that is not equal to the given node
     */
    public Node getOtherNode(Node node) {
        if (node1.equals(node)) {
            return node2;
        } else if (node2.equals(node)) {
            return node1;
        }
        return null;
    }

    /**
     * Sets the pheromone level of the edge to the specified value.
     *
     * @param phLevel The pheromone level to set
     */
    public void setPheromoneLevel(double phLevel) {
        this.pheromoneLevel = phLevel;
    }
}
