package Simulation;

import java.util.Random;

import Graph.Graph;

/**
 * This class is responsible for defining a pheromone evaporation event.
 */
public class PheromoneEvaporationEvent extends Event {
    private double evaporationRate; // Evaporation rate (ρ)
    private double meanValue; // Mean value for time between evaporation events (η)

    /**
     * Constructs a PheromoneEvaporationEvent object to evaporate the pheromone on
     * each edge of the graph.
     * 
     * @param evaporationRate Determines the amount of pheromone that evaporates.
     * @param meanValue       Sets the mean value of the pheromone.
     * @param time            Determines when the event will occur.
     * @param pec             The PECInterface object for event scheduling.
     */
    public PheromoneEvaporationEvent(double evaporationRate, double meanValue, double time, PECInterface pec) {
        super(time, pec);
        this.evaporationRate = evaporationRate;
        this.meanValue = meanValue;
    }

    /**
     * Executes the event by generating a random time between evaporation events
     * using an exponential distribution,
     * waiting for this determined time, decreasing the pheromone level of all edges
     * in the graph by a factor of the
     * evaporationRate, incrementing the numberOfEvaporations variable, and
     * scheduling another event to occur after
     * simulationTime seconds have passed since the start of execution
     * (simulationTime).
     */
    @Override
    public void run() {
        Graph graph = Parameters.getGraph();
        Random random = new Random();
        double timeBetweenEvaporation = -meanValue * Math.log(1 - random.nextDouble());
        graph.decreasePheromoneLevel(evaporationRate);

        // Schedule the next pheromone evaporation event
        eventTime += timeBetweenEvaporation;
        PheromoneEvaporationEvent nextEvent = new PheromoneEvaporationEvent(evaporationRate, meanValue, eventTime, pec);
        pec.addEvent(nextEvent);
    }
}
