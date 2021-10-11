package learn.java.concurrency.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程被阻塞，通过中断（另一个线程）通知线程退出，方式一：使用死循环阻塞一个线程，需循环体内部判断线程中断标识，被中断后退出循环。
 *
 * @see <a href="https://www.raychase.net/698">四火的唠叨.Java 多线程发展历史</a>
 */
@Slf4j
public class InterruptLoop extends Thread {

    @Override
    public void run() {
        while (true) {
            boolean interrupted = Thread.currentThread().isInterrupted();
            if (interrupted) {
                log.info("当前线程[{}]被中断，跳出循环", Thread.currentThread().getName());
                // 中断后退出循环
                break;
            } else {
                log.info("未被中断，继续执行");
            }
        }
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
