package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.List;

class ArriveEvent extends Event {

    ArriveEvent(int serverID, Customer cust, double eventTime) {
        super(serverID, cust, eventTime, true);
    }

    ArriveEvent(Customer cust) {
        super(0, cust, cust.getArrivalTime(), true);
    }

    @Override
    public Pair<Optional<Event>, Shop> execute(Shop shop) {

        ImList<Server> servers = shop.getServers();

        // available server idx
        int availIdx = shop.getNextAvailableServerIdx();

        // server without waiting customer idx
        int noWaitIdx = shop.getServerWithoutWaitingCustomerIdx();

        // available checkout idx
        int checkoutAvailIdx = shop.getNextAvailableCheckoutIdx();

        if (availIdx != -1) {

            ServeEvent e = new ServeEvent(availIdx + 1,
                    super.getCustomer(), super.getEventTime(), true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(e), shop);

        } else if (checkoutAvailIdx != -1 && shop.getCheckoutServers().size() > 0) {

            CheckoutServeEvent e = new CheckoutServeEvent(checkoutAvailIdx + 1,
                    super.getCustomer(), super.getEventTime(), true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(e), shop);

        } else if (noWaitIdx != -1) {
            // wait event

            WaitEvent e = new WaitEvent(noWaitIdx + 1,
                    super.getCustomer(), super.getEventTime(), true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(e), shop);

        } else if (shop.canQueueAtCheckout() &&
                shop.getCheckoutServers().size() > 0) {

            int id = shop.getCheckoutServers().get(0).getID();

            CheckoutWaitEvent e = new CheckoutWaitEvent(id,
                    super.getCustomer(), super.getEventTime(), true);

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(e), shop);

        } else {

            LeaveEvent leave = new LeaveEvent(super.getCustomer(), super.getEventTime());

            return Pair.<Optional<Event>, Shop>of(Optional.<Event>of(leave), shop);
        }

    }

    public String getEvent() {
        return "arrive";
    }

    @Override
    public String toString() {
        return String.format("%.3f %d arrives",
         super.getEventTime(),
          super.getCustomer().getID());
    }
}
