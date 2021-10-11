package learn.java.concurrency.juc.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link java.util.concurrent.Semaphore}
 *
 * @author ykthree
 * 2021/6/12
 */
public class SemaphoreTutorial {


    public static void main(String[] args) throws InterruptedException {
//        mutex();
//        TimeUnit.SECONDS.sleep(1);
//        condition();
        concurrentControl();
    }

    private static void mutex() {
        Runnable mutexTask = () -> {
            try {
                MutexAccess.mutex();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        // t1 和 t2 互斥进行，同时只能一个线程在对 count 进行加一操作
        Thread t1 = new Thread(mutexTask);
        Thread t2 = new Thread(mutexTask);

        t1.start();
        t2.start();
    }

    private static void condition() throws InterruptedException {
        Runnable conditionTask = () -> {
            try {
                ConditionAccess.condition();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread t1 = new Thread(conditionTask);
        t1.start();

        TimeUnit.SECONDS.sleep(1);
        ConditionAccess.condition.release();
    }

    private static void concurrentControl() throws InterruptedException {
        ConcurrentController control = new ConcurrentController(5);

        // 创建任务
        List<Task> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(new Task(control));
        }
        // 执行任务
        for (Task task : taskList) {
            new Thread(task).start();
        }
    }
}

class MutexAccess {

    static int count;
    static Semaphore mutex = new Semaphore(1);

    static void mutex() throws InterruptedException {
        mutex.acquire();

        try {
            System.out.println(Thread.currentThread().getName() + " is running.");
            for (int i = 0; i < 1000000; i++) {
                count++;
            }
            System.out.println(count);
        } finally {
            mutex.release();
        }
    }
}

class ConditionAccess {

    static Semaphore condition = new Semaphore(0);

    static void condition() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is running.");
        // 条件满足时，进入等待队列
        condition.acquire();
        System.out.println(Thread.currentThread().getName() + " is doing something.");

        // 释放，唤醒等待队列的其他线程
        condition.release();
    }
}

class ConcurrentController {

    final Semaphore sem;

    public ConcurrentController(int permits) {
        this.sem = new Semaphore(permits);
    }

    public void acquire() throws InterruptedException {
        sem.acquire();
    }

    public void release() {
        sem.release();
    }
}

class Task implements Runnable {

    private final ConcurrentController controller;

    public Task(ConcurrentController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        try {
            controller.acquire();
            // do something
            System.out.println(Thread.currentThread().getName() + " start.");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            controller.release();
        }
    }
}

class BlockingQueue1 {

    private int capacity = 100;

    private Semaphore mutex = new Semaphore(1);

    private Semaphore notFull = new Semaphore(capacity);
    private Semaphore notEmpty = new Semaphore(0);

    public void product() throws InterruptedException {
        // 缓冲区满时，生产者线程必须等待
        notFull.acquire();
        mutex.acquire();
        // 生成元素，放入队列
        // ...
        mutex.release();
        // 唤醒等待的消费者线程
        notEmpty.release();
    }

    public void consume() throws InterruptedException {
        // 缓冲区空时，消费都线程等待
        notEmpty.acquire();
        mutex.acquire();
        // 消费元素，移出队列
        // ...
        mutex.release();
        // 唤醒等待的生产者线程
        notFull.release();
    }
}

class BlockingQueue2 {

    private int capacity = 100;
    private int size;

    private final Lock lock = new ReentrantLock();

    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public void product() throws InterruptedException {
        lock.lock();
        try {
            // 队列满时，进入等待队列不满的条件队列
            while (size == capacity) {
                notFull.await();
            }
            // 入队
            // 唤醒一个等待的消费者线程
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            // 队列为空时，进入等代队列不为空的条件队列
            while (size == 0) {
                notEmpty.await();
            }
            // 出队
            // 唤醒一个等待的生产者线程
            notFull.signal();
        } finally {
            lock.unlock();
        }
    }
}

class BlockingQueue3 {

    private int capacity = 100;
    private int size;

    private final Object lock = new Object();

    public synchronized void product() throws InterruptedException {
        // 队列满时，进入等待队列不满的条件队列
        while (size == capacity) {
            lock.wait();
        }
        // 入队
        // 唤醒一个等待的消费者线程
        lock.notifyAll();
    }

    public synchronized void consume() throws InterruptedException {
        // 队列为空时，进入等代队列不为空的条件队列
        while (size == 0) {
            lock.wait();
        }
        // 出队
        // 唤醒一个等待的生产者线程
        lock.notifyAll();
    }
}