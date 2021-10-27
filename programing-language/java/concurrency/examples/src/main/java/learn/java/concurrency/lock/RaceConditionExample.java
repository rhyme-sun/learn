package learn.java.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * RaceConditionExample.
 */
@Slf4j
public class RaceConditionExample {

    private long count = 0;

    synchronized long get() {
        return count;
    }

    synchronized void set(long v) {
        count = v;
    }

    void plus() {
        int idx = 0;
        while (idx++ < 10000) {
            set(get() + 1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RaceConditionExample problem = new RaceConditionExample();
        Runnable task = problem::plus;

        Thread t11 = new Thread(task);
        Thread t12 = new Thread(task);

        t11.start();
        t12.start();

        t11.join();
        t12.join();

        // expect 20000, but < 20000
        log.info("count: {}", problem.count);
    }
}