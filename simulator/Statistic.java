package cs2030.simulator;

class Statistic {
    private final double waitingTime;
    private final int numOfCustomersServed;
    private final int numOfCustomersLeft;

    Statistic() {
        this.waitingTime = 0.0;
        this.numOfCustomersServed = 0;
        this.numOfCustomersLeft = 0;
    }

    Statistic(double waitingTime,
            int numOfCustomersServed,
            int numOfCustomersLeft) {
        this.waitingTime = waitingTime;
        this.numOfCustomersServed = numOfCustomersServed;
        this.numOfCustomersLeft = numOfCustomersLeft;
    }

    Statistic addCustomersServed() {

        int customersServed = this.numOfCustomersServed;
        customersServed = customersServed + 1;

        return new Statistic(this.waitingTime,
                customersServed,
                this.numOfCustomersLeft);
    }

    Statistic addCustomersLeft() {

        int customersLeft = this.numOfCustomersLeft;
        customersLeft = customersLeft + 1;

        return new Statistic(this.waitingTime,
                this.numOfCustomersServed,
                customersLeft);
    }

    Statistic addWaitingTime(double time) {

        double t = this.waitingTime;
        t = t + time;

        return new Statistic(t,
                this.numOfCustomersServed,
                this.numOfCustomersLeft);
    }

    double getAverageWaitingTime() {
        double averageWaitingTime = this.waitingTime / this.numOfCustomersServed;
        return averageWaitingTime;
    }

    int getCustomersServed() {
        return this.numOfCustomersServed;
    }

    int getCustomersLeft() {
        return this.numOfCustomersLeft;
    }

    @Override
    public String toString() {
        return String.format("[%.3f %d %d]",
                this.getAverageWaitingTime(),
                this.numOfCustomersServed,
                this.numOfCustomersLeft);
    }
}
