package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

import java.util.function.Supplier;
import java.util.List;

class ServeEvent extends Event {

    ServeEvent(int serverID, Customer cust, double eventTime, boolean realEvent) {
        super(serverID, cust, eventTime, realEvent);
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<Server> servers = shop.getServers();

        int serverID = super.getServerID();

        if (servers.get(serverID - 1).isAvailable() &&
                servers.get(serverID - 1).isResting() == false) {

            if (servers.get(serverID - 1).getCustomerList().size() > 0) {
                servers = servers.set(serverID - 1,
                        servers.get(serverID - 1).removeFromQueue());
            }

            double nextAvailableTime = super.getEventTime() +
                    super.getCustomer().getServiceTime().get();
            servers = servers.set(serverID - 1,
                    servers.get(serverID - 1).makeBusy()
                            .setNextAvailableTime(nextAvailableTime));
            DoneEvent done = new DoneEvent(serverID,
                    super.getCustomer(), nextAvailableTime, true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(done),
                    shop.update(servers));
        }

        double nextAvailableTime = servers.get(serverID - 1).getNextAvailableTime();

        servers = servers.set(serverID - 1,
                servers.get(serverID - 1).setNextAvailableTime(nextAvailableTime));
        ServeEvent s = new ServeEvent(serverID,
                super.getCustomer(), nextAvailableTime, true);

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(s),
                shop.update(servers));
    }

    public String getEvent() {
        return "serve";
    }

    public String toString() {
        return String.format("%.3f %d serves by %d", this.getEventTime(),
                super.getCustomer().getID(), super.getServerID());
    }
}
