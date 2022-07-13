package learn.java.concurrency.jmm;

/**
 * StopWorkWhenDone.
 * 使用 volatile 变量来通知工作已完成
 */
public class StopWorkWhenDone {

    private volatile boolean done;

    public void work() {
        while (!this.done) {
            // running
        }
    }

    public static void main(String[] args) throws InterruptedException {
        StopWorkWhenDone sleep = new StopWorkWhenDone();
        Thread thread = new Thread(() -> sleep.work());
        thread.start();
        Thread.sleep(100);
        sleep.done = true;
        thread.join();
    }
}