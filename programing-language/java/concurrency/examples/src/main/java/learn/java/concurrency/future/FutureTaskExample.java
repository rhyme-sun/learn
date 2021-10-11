package learn.java.concurrency.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

/**
 * FutureTaskTutorial.
 */
@Slf4j
public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        executeByThread();
        executeByThreadPool();
    }

    private static void executeByThread() {
        try {
            final FutureTask task = new FutureTask(() -> 1 + 2);
            final Thread thread = new Thread(task);
            thread.start();
            final Object result = task.get();
            log.info("Task result: {}", result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void executeByThreadPool() {
        try {
            final FutureTask task = new FutureTask(() -> 1 + 2);
            final ExecutorService executorService = Executors.newFixedThreadPool(1);
            executorService.execute(task);
            final Object result = task.get();
            log.info("Task result: {}", result);
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}