package Graph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for defining a node and its properties - id and
 * neighbors.
 */
public class Node {
    private int nodeId;
    private List<Node> neighbors;

    /**
     * Creates a new node with the given id.
     *
     * @param nodeId The node id
     */
    public Node(int nodeId) {
        this.nodeId = nodeId;
        this.neighbors = new ArrayList<>();
    }

    /**
     * Returns the node id.
     *
     * @return The node id
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * Sets the node id.
     *
     * @param id The node id to set
     */
    public void setNodeId(int id) {
        this.nodeId = id;
    }

    /**
     * Adds a neighbor to the list of neighbors for this node.
     *
     * @param neighbor The neighbor to add
     */
    public void addNeighbor(Node neighbor) {
        if (!neighbors.contains(neighbor) && (nodeId != neighbor.getNodeId())) {
            neighbors.add(neighbor);
        }
    }

    /**
     * Returns a list of all the nodes that are connected to this node.
     *
     * @return A list of nodes
     */
    public List<Node> getNeighbors() {
        return neighbors;
    }
}
