package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.Optional;

public class Customer {
    private final int id;
    private final double arrivalTime;
    private final Lazy<Double> serviceTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = new Lazy<Double>(() -> 1.0);
    }

    public Customer(int id, double arrivalTime, Lazy<Double> serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime; // serviceTime.get()
    }

    public int getID() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public Lazy<Double> getServiceTime() {
        return this.serviceTime;
    }

    @Override
    public String toString() {
        return String.format("%d", this.id);
    }
}
