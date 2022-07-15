package learn.java.concurrency.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ExecutorService.
 */
public class ExecutorServiceExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        final Future<?> submit = executorService.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
