package Simulation;

import AntColonyOpt.AntInterface;
import AntColonyOpt.Colony;
import AntColonyOpt.ColonyInterface;
import Graph.Graph;

/**
 * Object-Oriented Programming Course - Semester 2, Term 4, 2023
 * 
 * The Travelling Salesman Problem using Ant Colony Optimization
 * 
 * Authors:
 * - Manuel Salema
 * - Simen Domaas
 * - Kim Anh Doan
 */

/**
 * This class is responsible for defining the simulation.
 * It is also the main class of the project.
 */
public class Simulation {

        /**
         * The Simulation constructor.
         */
        public Simulation() {
        }

        /**
         * The main method for the TSP project by ACO.
         * 
         * @param args The arguments for the simulation
         */
        public static void main(String[] args) {
                double simulationTime = 0.0;

                Parameters parameters = new Parameters();
                parameters.readParameters(args);

                // Print the input parameters before starting the simulation
                printInputParameters(parameters);

                // Create a Colony of Ants
                ColonyInterface colony = new Colony();
                // Initialize the colony - based on the number of ants and the nest node
                colony.initializeColony(Parameters.getNumberOfAnts(), Parameters.getNestNode());

                PECInterface pec = new PEC(parameters);

                // Create the events and interfaces
                Event evaporationEvent = new PheromoneEvaporationEvent(Parameters.getRho(), Parameters.getEta(),
                                simulationTime, pec);
                pec.addEvent(evaporationEvent);
                SimulationReport report = new SimulationReport(Parameters.getFinalInstant() / 20, pec);
                pec.addEvent(report);

                for (int i = 0; i < Parameters.getNumberOfAnts(); i++) {
                        AntInterface ant = colony.getColonyOfAnts(i);
                        Event moveEvent = new antMoveEvent(simulationTime, pec, ant, Parameters.getNumberOfNodes(),
                                        Parameters.getDelta());
                        pec.addEvent(moveEvent);
                }

                // Run the simulation
                while (pec.getCurrentTime() <= Parameters.getFinalInstant()) {
                        pec.nextPECEvent();
                }
                SimulationReport lastReport = new SimulationReport(Parameters.getFinalInstant(), pec);
                lastReport.run();
        }

        // Function to print input parameters before starting the simulation
        private static void printInputParameters(Parameters parameters) {
                System.out.println("Input parameters:");
                System.out.println("n: number of nodes in the graph: " + Parameters.getNumberOfNodes());
                System.out.println("n1: the nest node: " + Parameters.getNestNode().getNodeId());
                System.out.println("α: alpha, ant move event: " + Parameters.getAlpha());
                System.out.println("β: beta, ant move event: " + Parameters.getBeta());
                System.out.println("δ: delta, ant move event: " + Parameters.getDelta());
                System.out.println("η: eta, pheromone evaporation event: " + Parameters.getEta());
                System.out.println("ρ: rho, pheromone evaporation event: " + Parameters.getRho());
                System.out.println("γ: pheromone level: " + Parameters.getGamma());
                System.out.println("ν: ant colony size: " + Parameters.getNumberOfAnts());
                System.out.println("τ: final instant: " + Parameters.getFinalInstant());
                System.out.println("with graph:");
                Graph graph = Parameters.getGraph();
                graph.printGraph();
        }
}
