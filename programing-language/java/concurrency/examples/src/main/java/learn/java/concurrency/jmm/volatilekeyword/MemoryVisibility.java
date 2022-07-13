package learn.java.concurrency.jmm.volatilekeyword;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

/**
 * volatile 保证内存可见性
 */
@Slf4j
public class MemoryVisibility {

    private boolean running1 = true;
    private volatile boolean running2 = true;

    private void run1() {
        log.info("Thread [{}] start running.", Thread.currentThread().getName());
        while (running1) {
            // do something
            // TODO ... 执行了一些方法后，也可以读到变化的值，比如：
            // Thread.currentThread().getName();
            // System.out.println();
        }
        log.info("Thread [{}] stop running.", Thread.currentThread().getName());
    }

    private void run2() {
        log.info("Thread [{}] start running.", Thread.currentThread().getName());
        while (running2) {
            // do something
        }
        log.info("Thread [{}] stop running.", Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        MemoryVisibility visibility = new MemoryVisibility();

        new Thread(visibility::run1, "Thread-1").start();
        new Thread(visibility::run2, "Thread-2").start();

        TimeUnit.SECONDS.sleep(2);
        visibility.running1 = false;
        visibility.running2 = false;
    }
}
