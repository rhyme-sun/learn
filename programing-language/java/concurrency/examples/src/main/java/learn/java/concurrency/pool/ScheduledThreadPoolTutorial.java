package learn.java.concurrency.pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPoolTutorial.
 */
public class ScheduledThreadPoolTutorial {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 每隔一秒执行一次
        scheduledExecutorService.scheduleAtFixedRate(ScheduledThreadPoolTutorial::doSomething, 0, 1, TimeUnit.SECONDS);
    }

    private static void doSomething() {
        System.out.println("Do something.");
    }
}