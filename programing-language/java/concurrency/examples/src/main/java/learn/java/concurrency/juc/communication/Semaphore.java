package learn.java.concurrency.juc.communication;

import java.util.Queue;

/**
 * Semaphore.
 */
public class Semaphore {

    /**
     * 计数器
     */
    int count;
    /**
     *  等待队列
     */
    Queue queue;

    /**
     * 初始化操作
     */
    Semaphore(int c) {
        this.count = c;
    }

    /**
     * P 操作
     */
    void acquire() {
        this.count--;
        if (this.count < 0) {
            // 将当前线程插入等待队列
            // 阻塞当前线程
        }
    }

    /**
     * V 操作
     */
    void release() {
        this.count++;
        if (this.count <= 0) {
            // 移除等待队列中的某个线程T
            // 唤醒线程T
        }
    }
}