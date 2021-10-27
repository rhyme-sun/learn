package learn.java.concurrency.division;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * ThreadPoolDeadLock.
 * jstack pid
 */
@Slf4j
public class ThreadPoolDeadLock {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        // 提交主任务
        pool.submit(() -> {
            try {
                // 提交子任务并等待其完成，因为线程池中没有其他线程来处理子任务，会导致该线程一直处于等待状态
                String hello = pool.submit(() -> "hello").get();
                log.info("{}", hello);
            } catch (Exception e) {
            }
        });
    }
}