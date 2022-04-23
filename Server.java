package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Optional;
import java.util.function.Supplier;

class Server {
    private final int id;
    private final double nextAvailableTime;
    private final boolean isAvailable;
    private final ImList<Customer> custList;
    private final int maxQLength;
    private final Supplier<Double> restTimes;
    private final boolean isResting;

    Server(int id, double nextAvailableTime, boolean isAvailable,
            ImList<Customer> custList,
            int maxQLength, Supplier<Double> restTimes, boolean isResting) {
        this.id = id;
        this.nextAvailableTime = nextAvailableTime;
        this.isAvailable = isAvailable;
        this.custList = custList;
        this.maxQLength = maxQLength;
        this.restTimes = restTimes;
        this.isResting = isResting;
    }

    public Server(int id) {
        this(id, 0, true,
                ImList.<Customer>of(), 1, () -> 0.0, false);
    }

    public Server(int id, int maxQLength) {
        this(id, 0, true,
                ImList.<Customer>of(), maxQLength, () -> 0.0, false);
    }

    public Server(int id, int maxQLength, Supplier<Double> restTimes) {
        this(id, 0, true,
                ImList.<Customer>of(), maxQLength, restTimes, false);
    }

    public double getNextAvailableTime() {
        return this.nextAvailableTime;
    }

    public Supplier<Double> getRestTime() {
        return this.restTimes;
    }

    public Server makeResting() {
        return new Server(this.id, this.nextAvailableTime,
                this.isAvailable, this.custList,
                this.maxQLength, this.restTimes, true);
    }

    public Server makeNotResting() {
        return new Server(this.id, this.nextAvailableTime,
                this.isAvailable, this.custList,
                this.maxQLength, this.restTimes, false);
    }

    public Server makeAvailable() {
        return new Server(this.id, this.nextAvailableTime,
                true, this.custList,
                this.maxQLength, this.restTimes, this.isResting);
    }

    public Server addToQueue(Customer customer) {
        return new Server(this.id, this.nextAvailableTime,
                this.isAvailable, this.custList.add(customer),
                this.maxQLength, this.restTimes, this.isResting);
    }

    public Server removeFromQueue() {
        return new Server(this.id, this.nextAvailableTime,
                this.isAvailable, this.custList.remove(0).second(),
                this.maxQLength, this.restTimes, this.isResting);
    }

    public Server makeBusy() {
        return new Server(this.id, this.nextAvailableTime,
                false, this.custList,
                this.maxQLength, this.restTimes, this.isResting);
    }

    public Server setNextAvailableTime(double time) {
        return new Server(this.id, time, this.isAvailable,
                this.custList, this.maxQLength,
                this.restTimes, this.isResting);
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }

    public boolean isSelfCheckout() {
        return false;
    }

    public boolean canQueue() {
        return this.custList.size() < this.maxQLength;
    }

    public boolean isResting() {
        return this.isResting;
    }

    public ImList<Customer> getCustomerList() {
        return this.custList;
    }

    public int getMaxQLength() {
        return this.maxQLength;
    }

    public int getID() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("%s", this.id);
    }
}
