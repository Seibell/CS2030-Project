package cs2030.simulator;

import cs2030.util.ImList;
import cs2030.util.Pair;
import cs2030.util.PQ;
import cs2030.util.Lazy;
import java.util.function.Supplier;

class SelfCheckout extends Server {

    SelfCheckout(int id, double nextAvailableTime,
            boolean isAvailable, int maxQLength) {
        super(id, nextAvailableTime, isAvailable,
                ImList.<Customer>of(), maxQLength, () -> 0.0, false);
    }

    SelfCheckout(int id, int maxQLength) {
        super(id, 0, true,
                ImList.<Customer>of(),
                maxQLength, () -> 0.0, false);
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public boolean isResting() {
        return false;
    }

    @Override
    public boolean isAvailable() {
        return super.isAvailable();
    }

    @Override
    public boolean isSelfCheckout() {
        return true;
    }

    @Override
    public SelfCheckout setNextAvailableTime(double time) {
        return new SelfCheckout(super.getID(), time,
                super.isAvailable(), super.getMaxQLength());
    }

    @Override
    public SelfCheckout makeBusy() {
        return new SelfCheckout(super.getID(),
                super.getNextAvailableTime(),
                false,
                super.getMaxQLength());
    }

    @Override
    public SelfCheckout makeAvailable() {
        return new SelfCheckout(super.getID(),
                super.getNextAvailableTime(),
                true,
                super.getMaxQLength());
    }

    public String toString() {
        return String.format("self-check %d", super.getID());
    }
}
