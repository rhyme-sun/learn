package learn.java.concurrency.jmm;

public class VolatileFeaturesExample {

    private volatile long a = 0L;
    private long b = 0L;

    public void set(long a) {
        this.a = a;
    }
    public synchronized void synSet(long b) {
        this.b = b;
    }

    public void getAndIncrement() {
        a++;
    }
    public synchronized void synGetAndIncrement() {
        b++;
    }

    public long get() {
        return a;
    }
    public synchronized long synGet() {
        return b;
    }
}