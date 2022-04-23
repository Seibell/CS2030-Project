package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Comparator;
import java.util.Optional;

public class Simulate3 {

    private final int numServers;
    private final List<Double> custArrivalTimes;
    private final Shop shop;
    private final PQ<Event> pq;
    private final int maxQLength;

    public Simulate3(int numServers, List<Double> custArrivalTimes) {
        this(numServers, custArrivalTimes, 1); // default max queue length to 1
    }

    public Simulate3(int numServers, List<Double> custArrivalTimes, int maxQLength) {
        this.numServers = numServers;
        this.custArrivalTimes = custArrivalTimes;
        PQ<Event> temp = new PQ<Event>(new EventComparator());
        this.maxQLength = maxQLength;

        ImList<Server> servers = ImList.<Server>of();

        for (int i = 0; i < numServers; i++) {
            servers = servers.add(new Server(i + 1));
        }

        this.shop = new Shop(servers);

        for (int i = 0; i < custArrivalTimes.size(); i++) {
            ArriveEvent newArriveEvent = new ArriveEvent(
                    new Customer(i + 1, this.custArrivalTimes.get(i)));
            temp = temp.add(newArriveEvent);
        }

        this.pq = temp;
    }

    public String run() {

        Comparator<Event> cmp = new EventComparator();
        PQ<Event> temp = new PQ<Event>(cmp);

        Shop tempShop = shop;
        temp = pq;

        // PQ<Event> temp = new PQ<Event>(cmp);

        String print = "";

        while (!temp.isEmpty()) {

            print += temp.poll().first().toString();

            Pair<Optional<Event>, Shop> e = temp.poll().first().execute(tempShop);
            tempShop = e.second();

            if (!e.first().equals(Optional.empty())) {
                temp = temp.add(e.first().orElseThrow());
                // tempShop = e.second();
            }

            temp = temp.poll().second();

            if (temp.isEmpty()) {
                print += "\n" + "-- End of Simulation --";
            } else {
                print += "\n";
            }
        }

        return print;
    }

    @Override
    public String toString() {
        return String.format("Queue: %s; Shop: %s", this.pq, this.shop);
    }
}
