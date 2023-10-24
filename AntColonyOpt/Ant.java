package AntColonyOpt;

import java.util.ArrayList;

import Graph.Graph;
import Graph.Node;
import Simulation.Parameters;
import Simulation.SimulationReport;

/**
 * Constructs an Ant object with the specified nest node.
 * The ant is initially located at the nest node, and the path is initialized as
 * empty.
 */
public class Ant implements AntInterface {
    private Node nestNode; // The nest node for the ant
    private Node currentNode; // Current node of the ant
    private ArrayList<Integer> path; // Path taken by the ant

    /**
     * Constructs an Ant object and sets the nest node.
     *
     * @param nestNode The node that will serve as the nest for the ant
     */
    public Ant(Node nestNode) {
        this.nestNode = nestNode;
        this.currentNode = nestNode;
        this.path = new ArrayList<>();

        resetAnt();
    }

    /**
     * Moves the ant to the next node in its path.
     *
     * @param nextNode The next node that the ant will move to
     */
    public void antMove(Node nextNode) {
        if (nextNode != null) {
            // Check for Hamiltonian cycle
            if (checkHamiltonian(nextNode)) {
                SimulationReport.addCycle(new ArrayList<>(path)); // Send Hamiltonian path to SimulationReport
                Graph graph = Parameters.getGraph();
                graph.increasePheromoneLevel(Parameters.getGamma(), path);
                resetAnt();
            } else {
                path.add(nextNode.getNodeId());
                currentNode = nextNode;
            }
        }
    }

    /**
     * Checks if the path is a Hamiltonian cycle.
     *
     * @param nextNode The next node being checked
     * @return True if the path is a Hamiltonian cycle
     */
    private boolean checkHamiltonian(Node nextNode) {
        if (nextNode == nestNode && path.size() == Parameters.getNumberOfNodes()) {
            return true;
        }
        return false;
    }

    /**
     * Clears the path ArrayList of all elements.
     */
    private void resetPath() {
        path.clear();
    }

    /**
     * Resets the ant's path to an empty ArrayList and adds the nest node to it.
     * Also sets currentNode equal to nestNode.
     */
    public void resetAnt() {
        resetPath();
        path.add(nestNode.getNodeId());
        this.currentNode = nestNode;
    }

    /**
     * Returns the path of the current node.
     *
     * @return The path from the root to the current node
     */
    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    /**
     * Returns the current node of the vehicle.
     *
     * @return The current node the ant is at
     */
    public Node getCurrentNode() {
        return this.currentNode;
    }

    /**
     * Returns the nest node of the ant.
     *
     * @return The nest node of the ant
     */
    public Node getNestNode() {
        return nestNode;
    }

    @Override
    public Node updateCurrentNode(Node visitedNode) {
        this.currentNode = visitedNode;
        return this.currentNode;
    }
}
