package cs2030.simulator;

import java.util.Optional;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;

import java.util.function.Supplier;
import java.util.List;

class CheckoutServeEvent extends Event {

    CheckoutServeEvent(int serverID, Customer cust, double eventTime, boolean realEvent) {
        super(serverID, cust, eventTime, realEvent);
    }

    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<SelfCheckout> checkoutServers = shop.getCheckoutServers();

        int serverID = super.getServerID();

        if (checkoutServers.get(serverID - 1).isAvailable()) {

            if (shop.getCustomerList().size() > 0) {
                shop = shop.removeCustomerFromList();
            }
            double nextAvailableTime = super.getEventTime() +
                    super.getCustomer().getServiceTime().get();

            checkoutServers = checkoutServers.set(serverID - 1,
                    checkoutServers.get(serverID - 1)
                            .makeBusy()
                            .setNextAvailableTime(nextAvailableTime));

            CheckoutDoneEvent done = new CheckoutDoneEvent(serverID,
                    super.getCustomer(), nextAvailableTime, true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(done),
                    shop.updateCheckoutServers(checkoutServers));
        }

        int checkoutID = checkoutServers.get(0).getID();
        double lowestNextTime = checkoutServers
                .get(0).getNextAvailableTime();

        for (int i = 0; i < checkoutServers.size(); i++) {
            lowestNextTime = Math.min(lowestNextTime,
                    checkoutServers.get(i).getNextAvailableTime());
        }

        for (int i = 0; i < checkoutServers.size(); i++) {
            if (lowestNextTime == checkoutServers
                    .get(i).getNextAvailableTime()) {
                checkoutID = checkoutServers.get(i).getID();
            }
        }

        checkoutID = checkoutID - shop.getServers().size();

        checkoutServers = checkoutServers.set(checkoutID - 1,
                checkoutServers
                        .get(checkoutID - 1)
                        .setNextAvailableTime(lowestNextTime));
        CheckoutServeEvent s = new CheckoutServeEvent(checkoutID,
                super.getCustomer(), lowestNextTime, true);

        return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(s),
                shop.updateCheckoutServers(checkoutServers));

    }

    public String getEvent() {
        return "serve";
    }

    public String toString() {
        return String.format("%.3f %d serves by self-check %d",
                this.getEventTime(),
                super.getCustomer().getID(),
                super.getServerID());
    }
}
