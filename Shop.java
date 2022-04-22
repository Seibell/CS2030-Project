package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.List;

class Shop {
    private final ImList<Server> list;
    private final ImList<SelfCheckout> checkoutServers;
    private final ImList<Customer> custList;
    private final int maxQLength;

    public Shop(List<Server> list) {
        this.list = ImList.<Server>of(list);
        this.checkoutServers = ImList.<SelfCheckout>of();
        this.custList = ImList.<Customer>of();

        int max = 0;

        for (int i = 0; i < list.size(); i++) {
            max = Math.max(list.get(i).getMaxQLength(), max);
        }

        this.maxQLength = max;
    }

    public Shop(ImList<Server> list) {
        this.list = list;
        this.checkoutServers = ImList.<SelfCheckout>of();
        this.custList = ImList.<Customer>of();

        int max = 0;

        for (int i = 0; i < list.size(); i++) {
            max = Math.max(list.get(i).getMaxQLength(), max);
        }

        this.maxQLength = max;
    }

    public Shop(ImList<Server> list, ImList<SelfCheckout> checkoutServers) {
        this.list = list;
        this.checkoutServers = checkoutServers;
        this.custList = ImList.<Customer>of();

        int max = 0;

        for (int i = 0; i < list.size(); i++) {
            max = Math.max(list.get(i).getMaxQLength(), max);
        }

        for (int i = 0; i < checkoutServers.size(); i++) {
            max = Math.max(checkoutServers.get(i).getMaxQLength(), max);
        }

        this.maxQLength = max;
    }

    public Shop(ImList<Server> list, ImList<SelfCheckout> checkoutServers,
            ImList<Customer> custList) {
        this.list = list;
        this.checkoutServers = checkoutServers;
        this.custList = custList;

        int max = 0;

        for (int i = 0; i < list.size(); i++) {
            max = Math.max(list.get(i).getMaxQLength(), max);
        }

        for (int i = 0; i < checkoutServers.size(); i++) {
            max = Math.max(checkoutServers.get(i).getMaxQLength(), max);
        }

        this.maxQLength = max;
    }

    public ImList<Server> getServers() {
        return list;
    }

    public int getMaxQLength() {
        return this.maxQLength;
    }

    public ImList<SelfCheckout> getCheckoutServers() {
        return checkoutServers;
    }

    public ImList<Customer> getCustomerList() {
        return custList;
    }

    public Shop update(ImList<Server> list) {
        return new Shop(list, getCheckoutServers(), getCustomerList());
    }

    public Shop updateCheckoutServers(ImList<SelfCheckout> checkoutServers) {
        return new Shop(getServers(), checkoutServers, getCustomerList());
    }

    public Shop addToCustomerList(Customer customer) {
        return new Shop(getServers(), getCheckoutServers(),
                getCustomerList().add(customer));
    }

    public Shop removeCustomerFromList() {
        return new Shop(getServers(), getCheckoutServers(),
                getCustomerList().remove(0).second());
    }

    int getNextAvailableServerIdx() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isAvailable() && list.get(i).isResting() == false) {
                return i;
            }
        }
        return -1;
    }

    int getServerWithoutWaitingCustomerIdx() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).canQueue()) {
                return i;
            }
        }
        return -1;
    }

    int getNextAvailableCheckoutIdx() {
        for (int i = 0; i < checkoutServers.size(); i++) {
            if (checkoutServers.get(i).isAvailable()) {
                return i;
            }
        }
        return -1;
    }

    boolean canQueueAtCheckout() {
        if (this.getCustomerList().size() < this.maxQLength) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
