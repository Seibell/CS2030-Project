package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class DoneEvent extends Event {

    DoneEvent(int serverID, Customer cust, double eventTime, boolean realEvent) {
        super(serverID, cust, eventTime, realEvent);
    }

    public String getEvent() {
        return "done";
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<Server> servers = shop.getServers();

        int serverID = super.getServerID();

        // resting
        double restTime = servers.get(serverID - 1).getRestTime().get();

        if (restTime > 0.0) {

            servers = servers.set(serverID - 1,
                    servers.get(serverID - 1)
                            .setNextAvailableTime(super.getEventTime() + restTime));

            RestEvent rest = new RestEvent(serverID,
                    super.getCustomer(), super.getEventTime(), true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(rest),
                    shop.update(servers));
        }

        servers = servers.set(serverID - 1,
                servers.get(serverID - 1).makeAvailable());

        return Pair.<Optional<Event>, Shop>of(Optional.empty(),
                shop.update(servers));
    }

    public String toString() {
        return String.format("%.3f %d done serving by %d",
                super.getEventTime(),
                super.getCustomer().getID(),
                super.getServerID());
    }
}
