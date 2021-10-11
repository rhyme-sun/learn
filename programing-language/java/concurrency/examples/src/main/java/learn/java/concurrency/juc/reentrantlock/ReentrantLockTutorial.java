package learn.java.concurrency.juc.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * {@ReentrantLock} 解决并发问题
 */
public class ReentrantLockTutorial {

    ReentrantLock lock = new ReentrantLock();

    private int count1;
    private int count2;

    void plus() {
        for (int i = 0; i < 5000; i++) {
            count1++;
        }
    }

    void lockPlus() {
        try {
            lock.lock();
            for (int i = 0; i < 5000; i++) {
                count2++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTutorial problem = new ReentrantLockTutorial();
        Runnable task1 = problem::plus;
        Runnable task2 = problem::lockPlus;

        Thread t11 = new Thread(task1);
        Thread t12 = new Thread(task1);

        Thread t21 = new Thread(task2);
        Thread t22 = new Thread(task2);

        t11.start();
        t12.start();
        t21.start();
        t22.start();

        t11.join();
        t12.join();
        t21.join();
        t22.join();

        System.out.println("count1: " + problem.count1);
        System.out.println("count2: " + problem.count2);
    }
}
