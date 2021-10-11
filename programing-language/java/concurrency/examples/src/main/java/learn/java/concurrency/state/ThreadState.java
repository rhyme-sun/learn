package learn.java.concurrency.state;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程状态
 */
@Slf4j
public class ThreadState {

    public static void main(String[] args) throws InterruptedException {
//        // 线程的 NEW RUNNABLE TERMINATED 状态
//        state1();
//        // 线程的 WAITING TIMED_WAITING 状态
//        state2();
//        // 线程的 BLOCKED 状态
//        state3();
//        // JUC 下的锁，线程处于 WAITING 而不是 BLOCKED 状态
//        state4();
//        // await / signal
//        state5();
//        // System.in.read()
//        state6();
//        join();
        park();
    }

    private static void state1() throws InterruptedException {
        Thread t = new Thread(() -> {
            // RUNNABLE
            log.info("[{}] [{}]", 2, Thread.currentThread().getState());
        });
        t.setName("test");

        // NEW
        log.info("[{}] [{}]", 1, t.getState());
        t.start();
        t.join();
        // TERMINATED
        log.info("[{}] [{}]", 3, t.getState());
    }

    private static void state2() {
        Thread t = new Thread(() -> {
            LockSupport.park();
            log.info("{} continues to run.", Thread.currentThread().getName());
            sleepSeconds(2);
        });
        t.setName("test");
        t.start();

        sleepSeconds(1);
        // WAITING
        log.info("[{}] [{}]", 4, t.getState());

        LockSupport.unpark(t);
        sleepSeconds(1);
        // TIMED_WAITING
        log.info("[{}] [{}]", 5, t.getState());
    }

    private static void state3() {
        final Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                sleepSeconds(4);
            }
        }).start();


        Thread t = new Thread(() -> {
            synchronized (lock) {
                log.info("{} get lock.", Thread.currentThread().getName());
            }
        });
        t.setName("test");
        sleepSeconds(1);
        t.start();
        sleepSeconds(1);
        log.info("[{}] [{}]", 6, t.getState());
    }

    private static void state4() {
        final Lock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            sleepSeconds(4);
            lock.unlock();
        }).start();

        Thread t = new Thread(() -> {
            lock.lock();
            log.info("{} get lock.", Thread.currentThread().getName());
            lock.unlock();
        });

        t.setName("test");
        sleepSeconds(1);
        t.start();
        sleepSeconds(1);
        // WAITING
        log.info("[{}] [{}]", 7, t.getState());
    }

    private static void state5() {
        final Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread t = new Thread(() -> {
            lock.lock();
            try {
                // 进入等待队列，释放锁
                condition.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        });
        t.setName("test");
        t.start();
        sleepSeconds(1);
        // WAITING
        log.info("[{}] [{}]", 8, t.getState());
        lock.lock();
        condition.signalAll();
        lock.unlock();
        // RUNNABLE
        log.info("[{}] [{}]", 9, Thread.currentThread().getState());
        sleepSeconds(1);
        // TERMINATED
        log.info("[{}] [{}]", 10, t.getState());
    }

    private static void state6() {
        Thread t = new Thread(() -> {
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        t.setName("test");
        t.start();
        sleepSeconds(1);
        // RUNNABLE
        log.info("[{}] [{}]", 11, t.getState());
    }

    private static void join() throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            System.out.println("t1 is running.");
            sleepSeconds(5);
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 is running.");
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 quit.");
            }
        });
        t1.start();
        sleepSeconds(1);
        t2.start();
        sleepSeconds(1);
        System.out.println("try get lock.");
        synchronized (lock) {
            System.out.println("get lock.");
        }
    }

    private static void park() {
        Object lock = new Object();
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("t1 is running.");
                LockSupport.parkUntil(System.currentTimeMillis() + 5000);
                System.out.println("t1 quit.");
            }
        });
        t1.start();
        sleepSeconds(1);
        System.out.println("try get lock.");
        synchronized (lock) {
            System.out.println("get lock.");
        }
    }

    static void sleepSeconds(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
