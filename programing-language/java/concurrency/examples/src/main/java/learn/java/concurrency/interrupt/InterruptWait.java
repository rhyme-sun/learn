package learn.java.concurrency.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程被阻塞，通过中断（另一个线程）通知线程退出，方式三：在 synchronized 代码块中，使用{@link Object#wait()}方法阻塞线程，被中断后
 * 抛出异常，但需注意抛出异常后线程的阻塞状态被重置（重置为 false），若其他地方依赖了线程的中断状态，则需要重新设置线程的中断状态。
 *
 *
 * @see <a href="https://www.raychase.net/698">四火的唠叨.Java 多线程发展历史</a>
 */
@Slf4j
public class InterruptWait extends Thread {

    public static Object lock = new Object();

    @Override
    public void run() {

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                // 线程被中断后，抛出异常，并且中断标识会被重置
                log.info("线程中断标识：[{}]", Thread.currentThread().isInterrupted()); // false
                // set interrupt flag again
                Thread.currentThread().interrupt();
                log.info("线程中断标识：[{}]", Thread.currentThread().isInterrupted()); // true
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        InterruptWait task = new InterruptWait();
        task.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.interrupt();
    }
}
