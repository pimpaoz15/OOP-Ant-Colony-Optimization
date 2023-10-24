package Simulation;

import java.util.Comparator;

/**
 * This class is responsible for comparing two events based on their time.
 */
public class TimeComparator implements Comparator<Event> {

    /**
     * The TimeComparator constructor.
     */
    public TimeComparator() {
    }

    /**
     * Compares two events based on their time.
     */
    @Override
    public int compare(Event event1, Event event2) {
        double time1 = event1.getEventTime();
        double time2 = event2.getEventTime();

        if (time1 == time2) {
            return 0;
        } else if (time1 < time2) {
            return -1;
        } else {
            return 1;
        }
    }
}
