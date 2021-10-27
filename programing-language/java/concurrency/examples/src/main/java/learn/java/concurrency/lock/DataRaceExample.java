package learn.java.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * DataRaceExample.
 */
@Slf4j
public class DataRaceExample {

    private int count;

    void plus() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DataRaceExample problem = new DataRaceExample();
        Runnable task = problem::plus;

        Thread t11 = new Thread(task);
        Thread t12 = new Thread(task);

        t11.start();
        t12.start();

        t11.join();
        t12.join();

        log.info("count: {}", problem.count);
    }
}