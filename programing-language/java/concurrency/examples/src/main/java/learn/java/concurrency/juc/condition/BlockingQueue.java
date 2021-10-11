package learn.java.concurrency.juc.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * BlockingQueue.
 */
public class BlockingQueue {

    final ReentrantLock lock = new ReentrantLock();

    final Condition notEmpty = lock.newCondition();
    final Condition notFull = lock.newCondition();

    final Object[] items;
    int count;

    public BlockingQueue(int capacity) {
        this.items = new Object[capacity];
    }

    public void put(Object e) throws InterruptedException {
        // 管程入口
        lock.lock();
        try {
            // notFull 条件不满足，这里表示存放数据的队列已经满了
            while (count == items.length) {
                // 进入 notFull 条件的等待队列
                notFull.await();
            }
            // 入队操作
            // notEmpty 条件已满足，通知在这个条件等待队列的线程可以选择一个出队
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        // 进入管程
        lock.lock();
        try {
            // notEmpty 条件不满足，这里表示存放数据的队列为空
            while (count == 0) {
                // 进入 notEmpty 的等待队列
                notEmpty.await();
            }
            // 出队操作
            Object o = new Object();
            // notFull 条件已满足，通知在这个条件等待队列的线程可以选择一个出队
            notFull.signal();
            return o;
        } finally {
            lock.unlock();
        }
    }
}