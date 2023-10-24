package AntColonyOpt;

import Graph.Node;

/**
 * The decision to create this Colony Interface was made with the objective of
 * retrieving all the information about a specific ant within the colony.
 */
public interface ColonyInterface {

    /**
     * Initializes the colony with the specified number of ants and nest node.
     *
     * @param numberOfAnts the number of ants to initialize the colony with
     * @param nestNode     the nest node where the colony is initialized
     */
    public void initializeColony(int numberOfAnts, Node nestNode);

    /**
     * Retrieves a specific colony of ants from the colony based on the number of
     * ants.
     *
     * @param numberOfAnt the number of ants in the colony to retrieve
     * @return the colony of ants with the specified number of ants
     */
    public AntInterface getColonyOfAnts(int numberOfAnt);
}
