package learn.java.concurrency.future;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * FutureExample.
 */
@Slf4j
public class FutureExample {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Map<String, Object> shareMap = new HashMap<>();
        executor.submit(new Task(shareMap), shareMap);
        log.info("ShareMap: {}", shareMap);
        executor.shutdown();
    }
}

class Task implements Runnable {

    private Map<String, Object> shareMap;

    public Task(Map<String, Object> shareMap) {
        this.shareMap = shareMap;
    }

    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        shareMap.put(thread.getName(), thread);
    }
}