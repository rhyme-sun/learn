package learn.java.concurrency.juc.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * AsyncToSync.
 */
public class AsyncToSync {

    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();

    private Object response;

    /**
     * 调用方通过该方法等待结果
     */
    Object get(long timeout) throws InterruptedException {
        long start = System.nanoTime();
        lock.lock();
        try {
            while (!isDone()) {
                done.await(timeout, TimeUnit.SECONDS);
                long cur = System.nanoTime();
                if (isDone() || cur - start > timeout) {
                    break;
                }
            }
        } finally {
            lock.unlock();
        }
        if (!isDone()) {
            throw new RuntimeException("请求超时");
        }
        // 返回结果
        return response;
    }

    /**
     * RPC 结果是否已经返回
     */
    boolean isDone() {
        return response != null;
    }

    /**
     * RPC 结果返回时调用该方法
     */
    private void doReceived(Object res) {
        lock.lock();
        try {
            response = res;
            if (done != null) {
                done.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}