package AntColonyOpt;

import java.util.ArrayList;

import Graph.Node;

/**
 * The decision to create this java class was made in such a way that in the
 * future,
 * if someone wants to change the way the Ant class is implemented, they can do
 * it by
 * modifying only the necessary lines of code, while the rest of the code will
 * remain intact.
 */
public interface AntInterface {
    /**
     * Moves the ant to the next node in its path.
     *
     * @param nextNode the next node to move to
     */
    public void antMove(Node nextNode);

    /**
     * Returns the path of the ant.
     *
     * @return the path of the ant
     */
    public ArrayList<Integer> getPath();

    /**
     * Returns the current node of the ant.
     *
     * @return the current node of the ant
     */
    public Node getCurrentNode();

    /**
     * Returns the nest node of the ant.
     *
     * @return the nest node of the ant
     */
    public Node getNestNode();

    /**
     * Resets the ant's path.
     */
    public void resetAnt();

    /**
     * Sets the ant's path.
     *
     * @param newPath the new path to set for the ant
     */
    public void setPath(ArrayList<Integer> newPath);

    /**
     * Updates the current node of the ant after visiting a node.
     *
     * @param visitedNode the node that was visited
     * @return the updated current node of the ant
     */
    public Node updateCurrentNode(Node visitedNode);
}
