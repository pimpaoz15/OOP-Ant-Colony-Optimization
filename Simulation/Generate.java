package Simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Graph.Graph;
import Graph.Node;

/**
 * Generates a random graph with n nodes and randomly connects each node to 1 to
 * n-i other nodes, where i is the current node index. The weight of each edge
 * is randomly chosen between 0 and a (inclusive). If an edge already exists, it
 * will not be added again.
 */
public class Generate {

    /**
     * The constructor for the Generate class.
     */
    public Generate() {
    }

    /**
     * Generates a random graph with n nodes and randomly connects each node to
     * 1 to n-i other nodes, where i is the current node index. The weight of each
     * edge is randomly chosen between 0 and a (inclusive). If an edge already
     * exists,
     * it will not be added again.
     * 
     * @param nestNode The node that will be the nest
     * @param n        The number of nodes in the graph
     * @param a        The maximum weight of edges
     * @return A graph with n nodes and edges with weights up to a
     */
    public static Graph generateRandomGraph(Node nestNode, int n, int a) {
        if (a <= 0) {
            throw new IllegalArgumentException(
                    "Invalid value for 'maximum weight of edges'. Weight must be greater than 0.");
        }
        Graph graph = new Graph();

        // Create nodes
        for (int i = 1; i <= n; i++) {
            if (i == Parameters.getNestNode().getNodeId()) {
                graph.addNode(nestNode);
            } else {
                Node node = new Node(i);
                graph.addNode(node);
                // System.out.println("Added node: " + node.getNodeId());
            }
        }

        // Create edges
        Random random = new Random();
        List<Node> nodes = graph.getNodes();
        Collections.shuffle(nodes, random); // Shuffle the list
        for (int i = 0; i < n; i++) {
            Node node1 = nodes.get(i);
            Node node2 = nodes.get((i + 1) % n);
            int weight = random.nextInt(a) + 1;
            if (!graph.hasEdge(node1, node2)) { // Check if edge already exists
                graph.addEdge(node1, node2, weight);
            }
            // Create edge between last and first node to complete the cycle
            if (i == n - 1) {
                node2 = nodes.get(0);
                if (!graph.hasEdge(node1, node2)) { // Check if edge already exists
                    graph.addEdge(node1, node2, weight);
                }
            }
            // Create more edges between the nodes
            int remainingNodes = n - i;
            int numEdges = random.nextInt(remainingNodes); // Random number of edges between 1 and remainingNodes
            for (int j = 0; j < numEdges; j++) {
                if (node1 != node2) { // Check if node1 and node2 are different nodes
                    weight = random.nextInt(a) + 1; // Random weight between 1 and a (inclusive)
                    if (!graph.hasEdge(node1, node2)) { // Check if edge already exists
                        graph.addEdge(node1, node2, weight);
                    }
                }
                node2 = getNextRandomNode(node2, nodes); // Move to a randomly chosen next node for the next edge
            }
        }

        return graph;
    }

    /**
     * Returns the next random node to visit based on the current node and the list
     * of nodes.
     * 
     * @param currentNode The current node
     * @param nodes       The list of nodes in the graph
     * @return A random node from the list of nodes
     */
    private static Node getNextRandomNode(Node currentNode, List<Node> nodes) {
        Random random = new Random();
        List<Node> availableNodes = new ArrayList<>(nodes);
        availableNodes.remove(currentNode);
        return availableNodes.get(random.nextInt(availableNodes.size()));
    }

    /**
     * Generates a graph by reading a file containing the adjacency matrix of the
     * graph.
     *
     * @param inputFile The file from which to read the graph
     * @return A graph with nodes and edges based on the adjacency matrix
     * @throws IOException If an error occurs while reading the file
     */
    public static Graph generateGraphFromFile(String inputFile) throws IOException {
        Graph graph = new Graph();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            // Read the first line containing the parameters
            String line = reader.readLine();
            String[] params = line.trim().split("\\s+");
            Node nestNode = new Node(Integer.parseInt(params[1]));
            Parameters.setNestNode(nestNode);

            int n = Integer.parseInt(params[0]);

            // Create nodes
            for (int i = 1; i <= n; i++) {
                if (i == Parameters.getNestNode().getNodeId()) {
                    graph.addNode(nestNode);
                } else {
                    Node node = new Node(i);
                    graph.addNode(node);
                }
            }

            List<Node> nodes = graph.getNodes();

            // Read the adjacency matrix and create edges
            for (int i = 0; i < n; i++) {
                line = reader.readLine();
                String[] weights = line.trim().split("\\s+");
                Node node1 = nodes.get(i);

                for (int j = 0; j < n; j++) {
                    int weight = Integer.parseInt(weights[j]);
                    if (weight != 0) {
                        Node node2 = nodes.get(j);
                        graph.addEdge(node1, node2, weight);
                    }
                }
            }
        }

        return graph;
    }

}
