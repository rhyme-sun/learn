package learn.java.concurrency.daemon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DaemonThread {

    public static void main(String[] args) {
        Runnable task = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread t = Thread.currentThread();
            log.info("当前线程：{}", t.getName()); // thread.setDaemon(true) 时并不会打印
        };
        Thread thread = new Thread(task);
        thread.setName("test-thread-1");
        // thread.setDaemon(false);
        thread.setDaemon(true);
        thread.start();
    }
}
