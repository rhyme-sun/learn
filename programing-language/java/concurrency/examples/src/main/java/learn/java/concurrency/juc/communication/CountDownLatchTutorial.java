package learn.java.concurrency.juc.communication;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link CountDownLatch} 基本用法
 *
 * @author ykthree
 * 2021/6/12
 */
public class CountDownLatchTutorial {

    public static void main(String[] args) throws InterruptedException {
        // waitByJoin();
        // waitByCountDownLatch();
    }

    private static void waitByJoin() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("t1 is running.");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 quit.");
        });

        Thread t2 = new Thread(() -> {
            System.out.println("t2 is running.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 quit.");
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Main thread continue run.");
    }

    private static void waitByCountDownLatch() {
        final ExecutorService service = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        try {
            service.execute(() -> {
                System.out.println("t1 is running.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("t1 quit.");
            });

            service.execute(() -> {
                System.out.println("t2 is running.");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
                System.out.println("t2 quit.");
            });

            latch.await();
            System.out.println("Main thread continue run.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
        }
    }
}
