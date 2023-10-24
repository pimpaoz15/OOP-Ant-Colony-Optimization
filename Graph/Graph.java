package Graph;

import java.util.ArrayList;
import java.util.List;
//import Parameters.*;

import Simulation.Event;

/**
 * This class is responsible for defining a graph and its properties - nodes and
 * edges.
 */
public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private static int totalEdgeWeight;

    /**
     * Creates a new graph with nodes and edges.
     */
    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Adds a node to the list of nodes.
     * 
     * @param node The node to be added
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Adds an edge to the graph.
     * 
     * @param node1  The first node of the edge
     * @param node2  The second node of the edge
     * @param weight The weight of the edge
     */
    public void addEdge(Node node1, Node node2, int weight) {
        Edge edge = new Edge(node1, node2, weight);
        edges.add(edge);
        node1.addNeighbor(node2);
        node2.addNeighbor(node1);
        totalEdgeWeight += weight;
    }

    /**
     * Returns a list of all the nodes in the graph.
     * 
     * @return A list of nodes
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Returns a list of edges that are connected to the current node.
     * 
     * @return A list of edges
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns the weight of an edge between two nodes.
     * 
     * @param n1 The first node of the edge
     * @param n2 The second node of the edge
     * @return The weight of the edge between the two nodes
     */
    public int getEdgeWeight(Node n1, Node n2) {
        for (int i = 0; i < edges.size(); i++) {
            Edge edge = edges.get(i);
            if (edge.getOtherNode(n1) == n2) {
                return edge.getWeight();
            }
        }
        return 0;
    }

    /**
     * Increases the pheromone level of each edge in a given path.
     * 
     * @param gamma The pheromone increase factor
     * @param path  The path for which to increase the pheromone level
     */
    public void increasePheromoneLevel(double gamma, ArrayList<Integer> path) {
        int totalPathWeight = 0;
        List<Edge> pathEdges = new ArrayList<>();
        for (Edge edge : edges) {
            Node n1 = edge.getSource();
            Node n2 = edge.getOtherNode(n1);
            if (isEdgeInPath(n1, n2, path)) {
                totalPathWeight += edge.getWeight();
                pathEdges.add(edge);
            }
        }
        for (Edge e : pathEdges) {
            double currentPheromone = e.getPheromoneLevel();
            double addedPheromoneLevel = 0.0;
            if (totalPathWeight != 0) {
                addedPheromoneLevel = gamma * (totalEdgeWeight / totalPathWeight);
            }
            double newPheromoneLevel = currentPheromone + addedPheromoneLevel;
            e.setPheromoneLevel(newPheromoneLevel);
        }
    }

    /**
     * Returns the edge that connects two given nodes.
     *
     * @param currentNode  The current node of the edge
     * @param neighborNode The neighboring node of the edge
     * @return The edge that connects the two nodes
     */
    public Edge getEdgeFromNode(Node currentNode, Node neighborNode) {
        for (Edge edge : edges) {
            if (edge.containsNode(currentNode) && edge.containsNode(neighborNode)) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Decreases the pheromone level of each edge in the graph.
     *
     * @param rho The pheromone evaporation rate
     */
    public void decreasePheromoneLevel(double rho) {
        for (Edge edge : edges) {
            double currentPheromoneLevel = edge.getPheromoneLevel();
            double newPheromoneLevel = currentPheromoneLevel - rho;
            if (currentPheromoneLevel != 0) {
                Event.incrementNumberOfEvaporations();
                if (newPheromoneLevel > 0) {
                    edge.setPheromoneLevel(newPheromoneLevel);
                } else {
                    edge.setPheromoneLevel(0.0);
                }

            }

        }
    }

    /**
     * Checks if an edge is in a path.
     *
     * @param n1   The first node of the edge
     * @param n2   The second node of the edge
     * @param path The path to check
     * @return True if the edge is in the path, false otherwise
     */
    public boolean isEdgeInPath(Node n1, Node n2, ArrayList<Integer> path) {
        int nodeId1 = n1.getNodeId();
        int nodeId2 = n2.getNodeId();

        for (int i = 0; i < path.size() - 1; i++) {
            int currentId1 = path.get(i);
            int currentId2 = path.get(i + 1);

            if ((currentId1 == nodeId1 && currentId2 == nodeId2) ||
                    (currentId1 == nodeId2 && currentId2 == nodeId1)) {
                // Found the edge in the path
                return true;
            }
        }

        return false; // Edge not found in the path
    }

    /**
     * Returns the total weight of all edges in the graph.
     * 
     * @return The total weight of all edges in the graph
     */
    public int getTotalEdgeWeight() {
        int totalWeight = 0;
        for (Edge edge : edges) {
            totalWeight += edge.getWeight();
        }
        return totalWeight;
    }

    /**
     * Clears the graph of all nodes and edges.
     */
    public void clear() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    /**
     * Checks if an edge exists between two nodes.
     * 
     * @param node1 The first node
     * @param node2 The second node
     * @return True if an edge exists between the two nodes, false otherwise
     */
    public boolean hasEdge(Node node1, Node node2) {
        for (Edge edge : edges) {
            if ((edge.getSource() == node1 && edge.getDestination() == node2)
                    || (edge.getSource() == node2 && edge.getDestination() == node1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Prints the graph in a matrix format, with the weights between each node.
     */
    public void printGraph() {
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                Node node1 = nodes.get(i);
                Node node2 = nodes.get(j);
                int weight = getEdgeWeight(node1, node2);
                System.out.print(weight + " ");
            }
            System.out.println();
        }
    }
}
