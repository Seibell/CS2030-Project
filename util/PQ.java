package cs2030.util;

import java.util.PriorityQueue;
import java.util.Comparator;

public class PQ<T> {

    private final PriorityQueue<T> priorityQueue;

    public PQ(Comparator<? super T> cmp) {
        this.priorityQueue = new PriorityQueue<T>(cmp);
    }

    public PQ(PriorityQueue<T> priorityQueue) {
        this.priorityQueue = new PriorityQueue<T>(priorityQueue);
    }

    public PQ<T> add(T elem) {
        PQ<T> temp = new PQ<T>(this.priorityQueue);
        temp.priorityQueue.add(elem);
        return temp;
    }

    public boolean isEmpty() {
        return this.priorityQueue.isEmpty();
    }

    public Pair<T, PQ<T>> poll() {
        PriorityQueue<T> temp = new PriorityQueue<T>(this.priorityQueue);
        return Pair.<T, PQ<T>>of(temp.poll(), new PQ<T>(temp));
    }

    @Override
    public String toString() {
        return String.format(this.priorityQueue.toString());
    }
}
