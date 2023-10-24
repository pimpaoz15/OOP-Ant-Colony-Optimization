package AntColonyOpt;

import Graph.Node;

/**
 * This class represents the colony of ants.
 * It contains an array of Ant objects.
 * It also contains methods to initialize the colony of ants and to return the
 * ant at a specified index.
 */
public class Colony implements ColonyInterface {

    /**
     * The Colony constructor.
     */
    public Colony() {
    }

    /* Array representing the colony of ants */
    private AntInterface[] colonyOfAnts;

    /**
     * Initializes the colonyOfAnts array with a specified number of Ant objects.
     *
     * @param numberOfAnts The number of ants in the colony
     * @param nestNode     The nest node for each ant
     */
    public void initializeColony(int numberOfAnts, Node nestNode) {
        this.colonyOfAnts = new AntInterface[numberOfAnts];
        for (int i = 0; i < numberOfAnts; i++) {
            this.colonyOfAnts[i] = new Ant(nestNode);
        }
    }

    /**
     * Returns the ant at the specified index within the colonyOfAnts array.
     *
     * @param numberOfAnt The index of the ant in the colony
     * @return The ant at the specified index
     */
    public AntInterface getColonyOfAnts(int numberOfAnt) {
        return colonyOfAnts[numberOfAnt];
    }
}
