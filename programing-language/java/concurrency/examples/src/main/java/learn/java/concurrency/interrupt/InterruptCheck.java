package learn.java.concurrency.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程被阻塞，通过中断（另一个线程）通知线程退出，方式二：通过循环判断线程中断标识位，未被中断继续循环执行当前任务。
 */
@Slf4j
public class InterruptCheck extends Thread {

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            log.info("未被中断，继续执行");
        }
        log.info("当前线程[{}]被中断", Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        InterruptLoop task = new InterruptLoop();
        task.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.interrupt();
    }
}
