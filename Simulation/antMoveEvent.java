package Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import AntColonyOpt.AntInterface;
import Graph.Edge;
import Graph.Graph;
import Graph.Node;

/**
 * This class represents an event for ant movement in the simulation.
 * It extends the Event class and implements the specific logic for ant
 * movement.
 */
public class antMoveEvent extends Event {
    private AntInterface ant;
    private int numberOfNodes;
    private double traverseTime;
    private double delta;

    /**
     * Constructs an AntMoveEvent object.
     *
     * @param time          The time of the event.
     * @param pec           The PECInterface object.
     * @param ant           The ant to move.
     * @param numberOfNodes The number of nodes in the graph.
     * @param delta         The time it takes for an ant to move from one node to
     *                      another.
     */
    public antMoveEvent(double time, PECInterface pec, AntInterface ant, int numberOfNodes, double delta) {
        super(time, pec);
        this.ant = ant;
        this.numberOfNodes = numberOfNodes;
        this.delta = delta;
    }

    /**
     * Executes the ant movement event.
     */
    @Override
    public void run() {
        Node nextNode = selectNextNode(ant.getCurrentNode());
        traverseTime = calculateTraverseTime(ant.getCurrentNode(), nextNode);

        ant.antMove(nextNode);

        incrementNumberOfAntEvents();

        setSimTime(eventTime + traverseTime);

        // Add a move event to the PEC for this ant
        antMoveEvent nextEvent = new antMoveEvent(simulationTime, pec, ant, numberOfNodes, delta);
        pec.addEvent(nextEvent);
    }

    /**
     * Selects the next node for the ant to move to.
     *
     * @param currentNode The current node of the ant.
     * @return The next node to move to.
     */
    private Node selectNextNode(Node currentNode) {
        List<Node> neighbors = currentNode.getNeighbors();
        double totalProbability = 0.0;
        List<Node> unvisitedNeighbors = new ArrayList<>();
        List<Node> visitedNeighbors = new ArrayList<>();

        // Separate unvisited and visited neighbors
        for (Node neighbor : neighbors) {
            if (!ant.getPath().contains(neighbor.getNodeId())) {
                unvisitedNeighbors.add(neighbor);
            } else {
                visitedNeighbors.add(neighbor);
            }
        }

        // If there are unvisited neighbors, make a random decision based on
        // probabilities
        if (!unvisitedNeighbors.isEmpty()) {
            for (Node neighbor : unvisitedNeighbors) {
                double moveProbability = calculateMoveProbability(currentNode, neighbor);
                totalProbability += moveProbability;
            }

            double randomValue = Math.random() * totalProbability;
            double cumulativeProbability = 0.0;

            for (Node neighbor : unvisitedNeighbors) {
                double moveProbability = calculateMoveProbability(currentNode, neighbor);
                cumulativeProbability += moveProbability;
                if (cumulativeProbability >= randomValue) {
                    return neighbor;
                }
            }
        }

        // If no unvisited neighbors are available, randomly select one of the visited
        // neighbors and correct the path of the ant
        if (unvisitedNeighbors.isEmpty()) {
            int randomIndex = (int) (Math.random() * visitedNeighbors.size());
            Node visitedNode = visitedNeighbors.get(randomIndex);
            if (visitedNode != ant.getNestNode() || ant.getPath().size() != numberOfNodes) {
                int indexOfVisitedNode = ant.getPath().indexOf(visitedNode.getNodeId());
                ArrayList<Integer> newPath = new ArrayList<>(ant.getPath().subList(0, indexOfVisitedNode + 1));
                ant.setPath(newPath);
                visitedNode = ant.updateCurrentNode(visitedNode);
                return selectNextNode(visitedNode);
            } else {
                return ant.getNestNode();
            }
        }

        // No neighbors available (both unvisited and visited), return null or throw an
        // exception
        return null;
    }

    /**
     * Calculates the probability of an ant moving from one node to another.
     *
     * @param currentNode The current node of the ant.
     * @param nextNode    The next node to move to.
     * @return The probability of moving from the current node to the next node.
     */
    private double calculateMoveProbability(Node currentNode, Node nextNode) {
        Graph graph = Parameters.getGraph();
        Edge edge = graph.getEdgeFromNode(currentNode, nextNode);
        double pheromone = edge.getPheromoneLevel();
        int weight = edge.getWeight();

        // Implement probability formula according to the project description
        double c_ijk = (Parameters.getAlpha() + pheromone) / (Parameters.getBeta() + weight);
        double c_i = calculateSumOfCij(currentNode);
        double probability = c_ijk / c_i;
        return probability;
    }

    /**
     * Calculates the sum of all Cis for a given node.
     *
     * @param currentNode The current node.
     * @return The sum of the Cis.
     */
    private double calculateSumOfCij(Node currentNode) {
        List<Node> neighbors = currentNode.getNeighbors();
        double sumOfCij = 0;

        for (Node neighbor : neighbors) {
            Edge edge = Parameters.getGraph().getEdgeFromNode(currentNode, neighbor);
            if (edge != null) {
                sumOfCij += (Parameters.getAlpha() + edge.getPheromoneLevel())
                        / (Parameters.getBeta() + edge.getWeight());
            }
        }

        return sumOfCij;
    }

    /**
     * Calculates the time it takes to traverse from one node to another.
     *
     * @param currentNode The current node.
     * @param nextNode    The next node.
     * @return The traverse time of the edge between the current node and the next
     *         node.
     */
    private double calculateTraverseTime(Node currentNode, Node nextNode) {
        Graph graph = Parameters.getGraph();
        Edge edge = graph.getEdgeFromNode(currentNode, nextNode);
        int weight = edge.getWeight();

        // Calculate the mean value for the exponential distribution
        double mean = Parameters.getDelta() * weight;

        // Create a random number generator
        Random random = new Random();

        // Generate a random time from the exponential distribution
        double traverseTime = -mean * Math.log(1 - random.nextDouble());

        return traverseTime;
    }
}
