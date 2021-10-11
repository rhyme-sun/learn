package learn.java.concurrency.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * 响应中断
 */
public class InterruptResponse {

    public static void main(String[] args) throws InterruptedException {
//        interrupt();
//        interrupt2();
        interrupt3();
    }

    private static void interrupt() throws InterruptedException {
        Thread t = new Thread(() -> {
            while(true) {
                final Thread thread = Thread.currentThread();
                final boolean interrupted = thread.isInterrupted();
                System.out.println(thread.getName() + " is running, interrupted: " + interrupted);
                if (interrupted) {
                    // 主动决定是否退出
                    // break;
                }
            }
        });
        t.start();
        t.interrupt();
        t.join();
        LockSupport.park();
    }

    private static void interrupt2() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                // 线程中断时抛出 InterruptedException
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.interrupt();
        t.join();
    }

    private static void interrupt3() throws InterruptedException {
        final Object lock = new Object();
        Thread t = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 1 start.");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    System.out.println("Thread 1 interrupted.");
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("Thread 2 start.");
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 end.");
            }
        });
        t.start();
        Thread.sleep(2000);
        t2.start();
        Thread.sleep(2000);
        // Thread 2 结束，Thread 1 重新获取到锁时再抛出中断异常退出
        System.out.println("Interrupt Thead 1.");
        t.interrupt();
        t.join();
        t2.join();
    }
}