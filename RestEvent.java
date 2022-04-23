package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class RestEvent extends Event {

    RestEvent(int serverID, Customer cust, double eventTime, boolean realEvent) {
        super(serverID, cust, eventTime, realEvent);
    }

    public String getEvent() {
        return "rest";
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<Server> servers = shop.getServers();

        int serverID = super.getServerID();

        servers = servers.set(serverID - 1,
                servers.get(serverID - 1).makeResting());

        DoneRestEvent doneRest = new DoneRestEvent(serverID,
                super.getCustomer(),
                servers.get(serverID - 1).getNextAvailableTime(), true);

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(doneRest),
                shop.update(servers));
    }

    public String toString() {
        return String.format("%.3f %s rest pending",
                super.getEventTime(),
                super.getServerID());
    }
}
