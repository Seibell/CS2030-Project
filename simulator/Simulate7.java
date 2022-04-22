package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;

public class Simulate7 {

    private final int numServers;
    private final List<Pair<Double, Supplier<Double>>> inputTimes;
    private final Shop shop;
    private final PQ<Event> pq;
    private final int maxQLength;
    private final Supplier<Double> restTimes;

    private final Statistic stat;

    public Simulate7(int numServers,
            List<Pair<Double, Supplier<Double>>> inputTimes,
            int maxQLength, Supplier<Double> restTimes) {
        this.numServers = numServers;
        this.inputTimes = inputTimes;
        PQ<Event> temp = new PQ<Event>(new EventComparator());
        this.maxQLength = maxQLength;
        this.restTimes = restTimes;

        this.stat = new Statistic();

        ImList<Server> servers = ImList.<Server>of();

        for (int i = 0; i < numServers; i++) {
            servers = servers.add(new Server(i + 1, maxQLength, restTimes));
        }

        this.shop = new Shop(servers);

        for (int i = 0; i < inputTimes.size(); i++) {

            Lazy<Double> lazy = Lazy.<Double>of(this.inputTimes.get(i).second());

            Customer cust = new Customer(i + 1, this.inputTimes.get(i).first(), lazy);

            ArriveEvent newArriveEvent = new ArriveEvent(cust);
            temp = temp.add(newArriveEvent);
        }

        this.pq = temp;
    }

    public String run() {

        Comparator<Event> cmp = new EventComparator();
        PQ<Event> temp = new PQ<Event>(cmp);

        Shop tempShop = shop;
        temp = pq;

        // Statistics
        Statistic stats = this.stat;
        ImList<Pair<Integer, Double>> customerStartWaitTimes = ImList.<Pair<Integer, Double>>of();
        ImList<Pair<Integer, Double>> customerServeTimes = ImList.<Pair<Integer, Double>>of();

        int waitingCustomerID = 0;
        double startWait = 0.0;

        int servedCustomerID = 0;
        double serveTime = 0.0;

        while (!temp.isEmpty()) {

            if (temp.poll().first().toString().contains("serves")) {
                Event current = temp.poll().first();
                Event next = temp.poll().first().execute(tempShop).first().orElseThrow();

                if ((current.getCustomer() == next.getCustomer()) &&
                        (current.getEvent().contains("serve") &&
                                next.getEvent().contains("serve"))) {
                    int whycheckstyle1 = 1;
                } else {
                    // Prints all the correct serve events
                    System.out.println(temp.poll().first().toString());
                }

            } else if (temp.poll().first().toString().contains("pending")) {
                int whycheckstyle = 1;
            } else {

                // Prints out rest of correct events
                System.out.println(temp.poll().first().toString());
            }

            Pair<Optional<Event>, Shop> e = temp.poll().first().execute(tempShop);
            tempShop = e.second();

            if (!e.first().equals(Optional.empty())) {

                // creating statistics
                if (e.first().orElseThrow().getEvent().contains("wait")) {
                    startWait = e.first().orElseThrow().getEventTime();
                    waitingCustomerID = e.first().orElseThrow().getCustomer().getID();
                    customerStartWaitTimes = customerStartWaitTimes
                            .add(Pair.<Integer, Double>of(waitingCustomerID, startWait));
                }
                if (e.first().orElseThrow().getEvent().contains("serve")) {
                    serveTime = e.first().orElseThrow().getEventTime();
                    servedCustomerID = e.first().orElseThrow().getCustomer().getID();

                    int idx = -1;

                    for (int i = 0; i < customerServeTimes.size(); i++) {
                        if (servedCustomerID == customerServeTimes.get(i).first()) {
                            idx = i;
                        }
                    }

                    if (idx != -1) {
                        customerServeTimes = customerServeTimes.set(idx,
                                Pair.<Integer, Double>of(servedCustomerID, serveTime));
                    } else {
                        customerServeTimes = customerServeTimes
                                .add(Pair.<Integer, Double>of(servedCustomerID, serveTime));
                    }
                }
                if (e.first().orElseThrow().getEvent().contains("leave")) {
                    stats = stats.addCustomersLeft();
                }
                temp = temp.add(e.first().orElseThrow());
            }

            temp = temp.poll().second();
        }

        // get Customers Served
        for (int i = 0; i < customerServeTimes.size(); i++) {
            stats = stats.addCustomersServed();
        }

        // get Waiting Time
        for (int i = 0; i < customerStartWaitTimes.size(); i++) {
            for (int j = 0; j < customerServeTimes.size(); j++) {
                if (customerStartWaitTimes
                        .get(i).first() == customerServeTimes
                                .get(j).first()) {
                    stats = stats.addWaitingTime(
                            customerServeTimes.get(j).second() -
                                    customerStartWaitTimes.get(i).second());
                }
            }
        }

        return stats.toString();
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", this.pq, this.shop);
    }
}
