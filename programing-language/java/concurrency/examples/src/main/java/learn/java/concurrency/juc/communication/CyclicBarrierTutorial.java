package learn.java.concurrency.juc.communication;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link CountDownLatch} 基本用法
 *
 * @author ykthree
 * 2021/6/12
 */
public class CyclicBarrierTutorial {

    public static void main(String[] args) {
        int parties = 20;
        Runnable barrierAction = () -> System.out.println("Go");
        CyclicBarrier barrier = new CyclicBarrier(parties, barrierAction);

        int total = 100;
        Runnable task = () -> {
            try {
                // 等待，直到等待数量达到指定的 parties 才能运行
                // 每调用一次该方法，等待数就加一
                barrier.await();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < total; i++) {
            new Thread(task).start();
        }
    }
}
