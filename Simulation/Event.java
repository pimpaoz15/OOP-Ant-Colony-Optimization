package Simulation;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract class representing an event in the simulation.
 */
public abstract class Event {
    /** The current simulation time */
    protected static double simulationTime;
    /** The number of evaporations that have occurred */
    protected static int numberOfEvaporations = 0;
    /** The number of ant events that have occurred */
    protected static int numberOfAntEvents = 0;
    /** A list of lists, where each sublist */
    protected static List<ArrayList<Integer>> cycles = new ArrayList<>();
    /** The event time */
    protected double eventTime;
    /** The PECInterface object */
    protected PECInterface pec;

    /**
     * Constructor for the Event class.
     *
     * @param eventTime The event time
     * @param pec       The PECInterface object
     */
    Event(double eventTime, PECInterface pec) {
        this.eventTime = eventTime;
        this.pec = pec;
    }

    /**
     * Returns the current simulation time.
     *
     * @return The current simulation time
     */
    public static double getSimTime() {
        return simulationTime;
    }

    /**
     * Returns the event time.
     *
     * @return The event time
     */
    public double getEventTime() {
        return eventTime;
    }

    /**
     * Returns the number of evaporations that have occurred.
     *
     * @return The number of evaporations that have occurred
     */
    public static int getNumberOfEvaporations() {
        return numberOfEvaporations;
    }

    /**
     * Returns the number of ant events that have occurred.
     *
     * @return The number of ant events that have occurred
     */
    public static int getNumberOfAntEvents() {
        return numberOfAntEvents;
    }

    /**
     * Increments the numberOfAntEvents variable by 1.
     */
    public static void incrementNumberOfAntEvents() {
        numberOfAntEvents += 1;
    }

    /**
     * Increments the numberOfEvaporations variable by 1.
     */
    public static void incrementNumberOfEvaporations() {
        numberOfEvaporations += 1;
    }

    /**
     * Returns a list of lists, where each sublist represents a cycle in the graph.
     *
     * @return A list of lists, where each sublist is a cycle
     */
    public static ArrayList<ArrayList<Integer>> getCycles() {
        return (ArrayList<ArrayList<Integer>>) cycles;
    }

    /**
     * Sets the simulation time to a given value.
     *
     * @param time The simulation time to set
     */
    public void setSimTime(double time) {
        simulationTime = time;
    }

    /**
     * Abstract method to be implemented in the subclasses.
     */
    public abstract void run();
}
