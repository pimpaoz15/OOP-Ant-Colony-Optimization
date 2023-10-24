package Simulation;

import java.util.PriorityQueue;

/**
 * This class is responsible for defining the Priority Event Collection (PEC)
 * and its properties.
 */
public class PEC implements PECInterface {
    private static PriorityQueue<Event> pec; // Priority Event Collection (PEC) queue
    private double currentTime; // Current time of the simulation

    /**
     * The PEC constructor initializes a priority queue that stores events in the
     * order of their time. It enables real-time simulation and allows events to be
     * added at any point during the simulation. The PEC ensures that events are
     * processed in the order of their arrival times, preventing conflicts between
     * them.
     * 
     * @param parameters The simulation parameters
     */
    public PEC(Parameters parameters) {
        int numberOfAnts = Parameters.getNumberOfAnts();
        pec = new PriorityQueue<Event>(numberOfAnts, new TimeComparator());
        currentTime = 0.0;
    }

    /**
     * Adds an event to the Priority Event Collection (PEC).
     *
     * @param event The event to be added to the PEC
     */
    public void addEvent(Event event) {
        pec.add(event);
    }

    /**
     * Runs the next event in the Priority Event Collection (PEC). It retrieves the
     * next event from the PEC, updates the current time to match the event's
     * simulation time, and executes the event's run function.
     */
    public void nextPECEvent() {
        Event event = pec.poll();
        currentTime = Event.getSimTime();
        event.run();
    }

    /**
     * The main loop of the simulation. It continues running until there are no more
     * events in the Priority Event Collection (PEC), and then it prints a summary
     * of all events that occurred during the simulation.
     */
    public void run() {
        while (!pec.isEmpty()) {
            nextPECEvent();
        }
    }

    /**
     * Returns the current time of the simulation.
     * 
     * @return The current time
     */
    public double getCurrentTime() {
        return currentTime;
    }

    /**
     * Returns the Priority Event Collection (PEC).
     * 
     * @return The Priority Event Collection
     */
    public static PriorityQueue<Event> getPec() {
        return pec;
    }
}
