package learn.java.concurrency.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * InterruptedExceptionExample.
 */
@Slf4j
public class InterruptedExceptionExample extends Thread {

    private volatile boolean isTerminated;

    @Override
    public void run() {
        while (!isTerminated) {
            log.info("未被中断，继续执行");
        }
        log.info("当前线程[{}]被中断", Thread.currentThread().getName());

        while (!Thread.currentThread().isInterrupted()) {
            log.info("未被中断，继续执行");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // 重新设置被异常清除了的中断标志位
                Thread.currentThread().interrupt();
            }
        }
        log.info("当前线程[{}]被中断", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        InterruptedExceptionExample task = new InterruptedExceptionExample();
        task.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.interrupt();
        task.isTerminated = true;
    }
}