package cs2030.simulator;

import java.util.Optional;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class WaitEvent extends Event {

    WaitEvent(int serverID, Customer cust,
            double nextEventTime, boolean realEvent) {
        super(serverID, cust, nextEventTime, realEvent);
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<Server> servers = shop.getServers();

        int serverID = super.getServerID();

        servers = servers.set(serverID - 1,
                servers.get(serverID - 1).addToQueue(super.getCustomer()));

        servers = servers.set(serverID - 1,
                servers.get(serverID - 1)
                        .setNextAvailableTime(servers.get(serverID - 1)
                                .getNextAvailableTime()));

        double nextAvailableTime = servers.get(serverID - 1)
                .getNextAvailableTime();
        Customer c = super.getCustomer();

        ServeEvent serveEvent = new ServeEvent(serverID,
                c, nextAvailableTime, false);

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serveEvent),
                shop.update(servers));
    }

    public String getEvent() {
        return "wait";
    }

    public String toString() {
        return String.format("%.3f %d waits at %d", super.getEventTime(),
                super.getCustomer().getID(), super.getServerID());
    }
}
