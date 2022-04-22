package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class LeaveEvent extends Event {

    LeaveEvent(Customer cust, double eventTime) {
        super(0, cust, eventTime, true);
    }

    public String getEvent() {
        return "leave";
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }

    public String toString() {
        return String.format("%.3f %d leaves",
                super.getEventTime(),
                super.getCustomer().getID());
    }
}
