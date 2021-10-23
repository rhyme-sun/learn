package learn.java.concurrency.juc.communication;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link CyclicBarrier} 基本用法
 */
@Slf4j
public class CyclicBarrierExample {

    public static void main(String[] args) {
        int parties = 20;
        // 执行回调的线程池，使用线程池执行回调函数，避免下一轮任务被阻塞（执行回调函数的线程是将 CyclicBarrier 内部计数器减到 0 的那个线程）
        Executor executor = Executors.newFixedThreadPool(1);
        Runnable barrierAction = () -> executor.execute(() -> log.info("GO"));
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
