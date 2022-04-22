package cs2030.simulator;

import java.util.Comparator;

class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() < e2.getEventTime()) {
            return -1;
        } else if (e1.getEventTime() > e2.getEventTime()) {
            return 1;
        } else {
            return e1.getCustomer().getID() - e2.getCustomer().getID();
        }
    }
}
