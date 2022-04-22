package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class EventStub extends Event {

    EventStub(Customer cust, double eventTime) {
        super(0, cust, eventTime, true);
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {
        return Pair.<Optional<Event>, Shop>of(Optional.empty(), shop);
    }

    @Override
    public String toString() {
        return String.format("%.3f", super.getEventTime());
    }
}
