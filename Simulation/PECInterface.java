package Simulation;

/**
 * This interface represents the PEC (Parallel Event-based Simulation)
 * interface.
 */
public interface PECInterface {
    /**
     * Retrieves the next event from the PEC.
     */
    public void nextPECEvent();

    /**
     * Runs the simulation.
     */
    public void run();

    /**
     * Retrieves the current time of the simulation.
     *
     * @return the current time.
     */
    public double getCurrentTime();

    /**
     * Adds an event to the PEC.
     *
     * @param moveEvent the event to be added.
     */
    public void addEvent(Event moveEvent);

}
