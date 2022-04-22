package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

import java.util.function.Supplier;
import java.util.List;

abstract class Event {

    private final int serverID;
    private final Customer cust;
    private final double eventTime;
    private final boolean realEvent;

    Event(int serverID, Customer cust, double eventTime, boolean realEvent) {
        this.serverID = serverID;
        this.cust = cust;
        this.eventTime = eventTime;
        this.realEvent = realEvent;
    }

    protected Customer getCustomer() {
        return this.cust;
    }

    protected int getServerID() {
        return this.serverID;
    }

    protected double getEventTime() {
        return this.eventTime;
    }

    protected boolean isRealEvent() {
        return this.realEvent;
    }

    protected String getEvent() {
        return "event";
    }

    abstract Pair<Optional<Event>, Shop> execute(Shop shop);

}
