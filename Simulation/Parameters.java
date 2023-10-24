package Simulation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Graph.Graph;
import Graph.Node;

/**
 * This class is responsible for reading the parameters from the input file and
 * storing them in global variables.
 */
public class Parameters {
    /** Number of nodes in the graph (n) */
    private static int numberOfNodes;
    /** Nest node (n1) */
    private static Node nestNode;
    /** Maximum weight for the edges in the adjacency matrix of the graph (a) */
    private static int maxWeight;
    /** Parameter for ant movement event (alpha) */
    private static double alpha;
    /** Parameter for ant movement event (beta) */
    private static double beta;
    /** Parameter for ant movement event (delta) */
    private static double delta;
    /** Parameter for pheromone evaporation event (gamma) */
    private static double gamma;
    /** Parameter for pheromone evaporation event (rho) */
    private static double rho;
    /** Parameter for pheromone level (eta) */
    private static double eta;
    /** Number of ants in the colony (nu) */
    private static int numberOfAnts;
    /** Number of iterations (k) */
    private static double finalInstant;
    /** Graph generated from the parameters */
    private static Graph generatedGraph;

    /**
     * The Parameters constructor.
     */
    public Parameters() {
    }

    /**
     * Reads the parameters from the input file and stores them in global variables.
     *
     * @param args The command line arguments
     */
    public void readParameters(String[] args) {
        if (args.length == 0) {
            System.out.println("No parameters provided.");
            return;
        }

        if (args[0].equals("-r")) {
            if (args.length != 12) {
                System.out.println("Invalid number of parameters for random graph generation.");
                return;
            }
            try {
                numberOfNodes = Integer.parseInt(args[1]);
                if (numberOfNodes < 0) {
                    System.out.println("Invalid value for 'number of nodes'. Number of nodes must be non-negative.");
                    return;
                }

                maxWeight = Integer.parseInt(args[2]);
                if (maxWeight < 0) {
                    System.out.println("Invalid value for 'maximum weight'. Maximum weight must be non-negative.");
                    return;
                }

                nestNode = new Node(Integer.parseInt(args[3]));
                if (((Integer.parseInt(args[3])) < 0) || ((Integer.parseInt(args[3])) > numberOfNodes)) {
                    System.out.println(
                            "Invalid value for 'Nest Node'. Value must be non-negative and less than total number of nodes.");
                    return;
                }
                alpha = Double.parseDouble(args[4]);
                beta = Double.parseDouble(args[5]);
                delta = Double.parseDouble(args[6]);
                gamma = Double.parseDouble(args[7]);
                rho = Double.parseDouble(args[8]);
                eta = Double.parseDouble(args[9]);
                numberOfAnts = Integer.parseInt(args[10]);
                if (numberOfAnts < 0) {
                    System.out.println("Invalid value for 'number of ants'. Number of ants must be non-negative.");
                    return;
                }

                finalInstant = Double.parseDouble(args[11]);
                if (finalInstant < 0) {
                    System.out.println("Invalid value for 'final instant'. Final instant must be non-negative.");
                    return;
                }

                // Generate random graph
                generatedGraph = Generate.generateRandomGraph(nestNode, numberOfNodes, maxWeight);

            } catch (NumberFormatException e) {
                System.out.println("Invalid parameter format.");
            }
        } else if (args[0].equals("-f")) {
            if (args.length != 2) {
                System.out.println("Invalid number of parameters for file input.");
                return;
            }

            String filename = args[1];
            if (!filename.endsWith(".txt")) {
                System.out.println("Invalid file extension. Only .txt files are supported.");
                return;
            }

            try {
                readParametersFromFile(filename);
            } catch (IOException e) {
                System.out.println("Error reading input file: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid command format.");
        }
    }

    /**
     * Reads the parameters from a file.
     * 
     * @param filename The name of the file to read the parameters from
     * @throws IOException If there is an error reading the file
     */
    public void readParametersFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read the first line containing the parameters
            String line = reader.readLine();
            String[] params = line.trim().split("\\s+");
            if (params.length != 10) {
                throw new IOException("Invalid number of parameters in input file.");
            }

            try {
                numberOfNodes = Integer.parseInt(params[0]);
                if (numberOfNodes < 0) {
                    System.out.println("Invalid value for 'number of nodes'. Number of nodes must be non-negative.");
                    return;
                }
                nestNode = new Node(Integer.parseInt(params[1]));
                if (((Integer.parseInt(params[1])) < 0) || ((Integer.parseInt(params[1])) > numberOfNodes)) {
                    System.out.println(
                            "Invalid value for 'Nest Node'. Value must be non-negative and less than total number of nodes.");
                    return;
                }
                alpha = Double.parseDouble(params[2]);
                beta = Double.parseDouble(params[3]);
                delta = Double.parseDouble(params[4]);
                eta = Double.parseDouble(params[5]);
                rho = Double.parseDouble(params[6]);
                gamma = Double.parseDouble(params[7]);
                numberOfAnts = Integer.parseInt(params[8]);
                if (numberOfAnts < 0) {
                    System.out.println("Invalid value for 'number of ants'. Number of ants must be non-negative.");
                    return;
                }
                finalInstant = Double.parseDouble(params[9]);
                if (finalInstant < 0) {
                    System.out.println("Invalid value for 'final instant'. Final instant must be non-negative.");
                    return;
                }
            } catch (NumberFormatException e) {
                throw new IOException("Invalid parameter format in input file.");
            }

            generatedGraph = Generate.generateGraphFromFile(filename);
        }
    }

    /**
     * Returns the number of nodes in the graph.
     * 
     * @return The number of nodes in the graph
     */
    public static int getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * Returns the nest node.
     * 
     * @return The nest node
     */
    public static Node getNestNode() {
        return nestNode;
    }

    /**
     * Returns the maximum weight of any edge in the graph.
     *
     * @return The maximum weight
     */
    public static int getMaxWeight() {
        return maxWeight;
    }

    /**
     * Returns the value of alpha, which is a parameter concerning the ant movement.
     * 
     * @return The value of alpha
     */
    public static double getAlpha() {
        return alpha;
    }

    /**
     * Returns the value of beta, which is a parameter concerning the ant movement.
     *
     * @return The value of beta
     */
    public static double getBeta() {
        return beta;
    }

    /**
     * Returns the value of delta, which is a parameter concerning the ant movement.
     * 
     * @return The value of delta
     */
    public static double getDelta() {
        return delta;
    }

    /**
     * Returns the value of eta, which is a parameter concerning the pheromone
     * evaporation event.
     * 
     * @return The value of eta
     */
    public static double getEta() {
        return eta;
    }

    /**
     * Returns the value of rho, which is a parameter concerning the pheromone
     * evaporation event.
     *
     * @return The value of rho
     */
    public static double getRho() {
        return rho;
    }

    /**
     * Returns the value of gamma, which is a parameter concerning the pheromone
     * level.
     * 
     * @return The value of gamma
     */
    public static double getGamma() {
        return gamma;
    }

    /**
     * Returns the number of ants in the colony.
     * 
     * @return The number of ants
     */
    public static int getNumberOfAnts() {
        return numberOfAnts;
    }

    /**
     * Returns the final instant of the simulation.
     *
     * @return The final instant
     */
    public static double getFinalInstant() {
        return finalInstant;
    }

    /**
     * Returns the graph that was generated by the generateGraph function.
     *
     * @return The generated graph
     */
    public static Graph getGraph() {
        return generatedGraph;
    }

    /**
     * Sets the nest node to the specified node.
     * 
     * @param nest The nest node to set
     */
    public static void setNestNode(Node nest) {
        nestNode = nest;
    }
}
