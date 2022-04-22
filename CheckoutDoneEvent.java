package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;

class CheckoutDoneEvent extends Event {

    CheckoutDoneEvent(int serverID, Customer cust, double eventTime, boolean realEvent) {
        super(serverID, cust, eventTime, realEvent);
    }

    public String getEvent() {
        return "done";
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<SelfCheckout> checkoutServers = shop.getCheckoutServers();

        int serverID = super.getServerID();

        checkoutServers = checkoutServers.set(serverID - 1,
                checkoutServers.get(serverID - 1).makeAvailable());

        return Pair.<Optional<Event>, Shop>of(Optional.empty(),
                shop.updateCheckoutServers(checkoutServers));
    }

    public String toString() {
        return String.format("%.3f %d done serving by self-check %d",
                super.getEventTime(),
                super.getCustomer().getID(),
                super.getServerID());
    }
}
