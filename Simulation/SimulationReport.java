package Simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Graph.Graph;
import Graph.Node;

/**
 * The class is responsible for defining a simulation report event.
 */
public class SimulationReport extends Event {
    private static int lastObservationNumber;
    private static double reportTime;

    /**
     * The SimulationReport constructor.
     * 
     * @param time The simulation time
     * @param pec  The PECInterface instance
     */
    public SimulationReport(double time, PECInterface pec) {
        super(time, pec);
        reportTime = time;
    }

    /**
     * Runs the simulation and updates the simulation report, including the best
     * Hamiltonian path, cycles, and iteration count.
     */
    @Override
    public void run() {
        int mEvents = getNumberOfAntEvents();
        int eEvents = getNumberOfEvaporations();

        ArrayList<ArrayList<Integer>> paths = getCycles();
        if (paths != null) {
            for (ArrayList<Integer> path : paths) {
                addCycle(path);
            }
        }

        int cycleCount;
        double reportInterval = Parameters.getFinalInstant() / 20;
        int observationNumber = (int) (reportTime / reportInterval);

        if ((lastObservationNumber < observationNumber) || (observationNumber == 20)) {
            System.out.println("Observation number: " + observationNumber);
            System.out.println("Present instant: " + reportTime);
            System.out.println("Number of move events: " + mEvents);
            System.out.println("Number of evaporation events: " + eEvents);
            System.out.println("Top candidate cycles:");

            cycleCount = Math.min(cycles.size(), 6);
            for (int i = 1; i < cycleCount; i++) {
                ArrayList<Integer> cycle = cycles.get(i);
                System.out.println(printCycle(cycle));
            }
            lastObservationNumber = observationNumber;

            cycleCount = Math.min(cycles.size(), 6);

            if (cycles.size() != 0) {
                System.out.println("Best Hamiltonian cycle: " + printCycle(cycles.get(0)));
            }
            System.out.println();

            reportTime += reportInterval;
            SimulationReport nextReport = new SimulationReport(reportTime, pec);
            pec.addEvent(nextReport);
        }
    }

    /**
     * Returns a string representation of a cycle.
     *
     * @param cycle The cycle to be represented as a string
     * @return A string representation of the cycle
     */
    private String printCycle(ArrayList<Integer> cycle) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (cycle.isEmpty()) {
            sb.append("}");
        } else {
            for (int i = 0; i < cycle.size(); i++) {
                sb.append(cycle.get(i));
                if (i != cycle.size() - 1) {
                    sb.append(",");
                }
            }
            sb.append("}:");
            int totalWeight = calculateCycleWeight(cycle);
            sb.append(totalWeight);
        }
        return sb.toString();
    }

    /**
     * Calculates the total weight of a cycle.
     *
     * @param cycle The cycle for which to calculate the weight
     * @return The total weight of the cycle
     */
    private static int calculateCycleWeight(ArrayList<Integer> cycle) {
        int totalWeight = 0;
        for (int i = 0; i < cycle.size() - 1; i++) {
            int node1 = cycle.get(i);
            int node2 = cycle.get(i + 1);
            totalWeight += getPathWeight(node1, node2);
        }
        return totalWeight + getPathWeight(cycle.get(cycle.size() - 1), cycle.get(0));
    }

    /**
     * Returns the weight of the edge between two nodes.
     *
     * @param node1 The first node
     * @param node2 The second node
     * @return The weight of the edge between the two nodes
     */
    private static int getPathWeight(int node1, int node2) {
        Graph graph = Parameters.getGraph();
        List<Node> nodes = graph.getNodes();
        for (Node node : nodes) {
            if (node.getNodeId() == node1) {
                List<Node> neighbors = node.getNeighbors();
                for (Node neighbor : neighbors) {
                    if (neighbor.getNodeId() == node2) {
                        return graph.getEdgeWeight(node, neighbor);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Adds a cycle to the list of cycles and sorts them based on their total edge
     * weight in descending order.
     *
     * @param cycle The cycle to be added
     */
    public static void addCycle(ArrayList<Integer> cycle) {
        if (!cycles.contains(cycle) && cycle.size() == Parameters.getNumberOfNodes()) {
            cycles.add(cycle);
            Collections.sort(cycles,
                    (cycle1, cycle2) -> Integer.compare(calculateCycleWeight(cycle1), calculateCycleWeight(cycle2)));
        }
    }

    /**
     * Returns the last observation.
     *
     * @return The last observation
     */
    public int getLastObservationNumber() {
        return lastObservationNumber;
    }
}
