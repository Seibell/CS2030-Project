package cs2030.simulator;

import java.util.List;
import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Comparator;

public class Simulate2 {

    private final int numServers;
    private final List<Double> custArrivalTime;
    private final Shop shop;
    private final PQ<Event> pq;

    public Simulate2(int numServers, List<Double> custArrivalTime) {
        this.numServers = numServers;
        this.custArrivalTime = custArrivalTime;

        ImList<Server> list = ImList.<Server>of();

        for (int i = 1; i <= numServers; i++) {
            list = list.add(new Server(i));
        }

        this.shop = new Shop(list);

        Comparator<Event> cmp = new EventComparator();
        PQ<Event> temp = new PQ<Event>(cmp);

        for (int i = 0; i < custArrivalTime.size(); i++) {
            temp = temp.add(new EventStub(
                    new Customer(i + 1, custArrivalTime.get(i)),
                    custArrivalTime.get(i)));
        }

        this.pq = temp;
    }

    public String run() {

        Comparator<Event> cmp = new EventComparator();
        PQ<Event> temp = new PQ<Event>(cmp);

        temp = pq;
        String print = "";

        while (!temp.isEmpty()) {
            print += temp.poll().first().toString();

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
