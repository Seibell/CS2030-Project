package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

class CheckoutWaitEvent extends Event {

    CheckoutWaitEvent(int serverID, Customer cust,
            double nextEventTime, boolean realEvent) {
        super(serverID, cust, nextEventTime, realEvent);
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<SelfCheckout> checkoutServers = shop.getCheckoutServers();

        shop = shop.addToCustomerList(super.getCustomer());

        int checkoutID = checkoutServers.get(0).getID();
        double lowestNextTime = checkoutServers
                .get(0).getNextAvailableTime();

        for (int i = 0; i < checkoutServers.size(); i++) {
            lowestNextTime = Math.min(lowestNextTime,
                    checkoutServers.get(i).getNextAvailableTime());
        }

        for (int i = 0; i < checkoutServers.size(); i++) {
            if (lowestNextTime == checkoutServers.get(i)
                    .getNextAvailableTime()) {
                checkoutID = checkoutServers.get(i).getID();
            }
        }

        checkoutID = checkoutID - shop.getServers().size();

        checkoutServers = checkoutServers.set(checkoutID - 1,
                checkoutServers.get(checkoutID - 1)
                        .setNextAvailableTime(lowestNextTime));

        CheckoutServeEvent serveEvent = new CheckoutServeEvent(checkoutID,
                super.getCustomer(), lowestNextTime, false);

        // System.out.println("FAKE EVENT IN WAIT: " + serveEvent.toString());

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(serveEvent),
                shop.updateCheckoutServers(checkoutServers));
    }

    public String getEvent() {
        return "wait";
    }

    public String toString() {
        return String.format("%.3f %d waits at self-check %d",
                super.getEventTime(),
                super.getCustomer().getID(),
                super.getServerID());
    }
}
