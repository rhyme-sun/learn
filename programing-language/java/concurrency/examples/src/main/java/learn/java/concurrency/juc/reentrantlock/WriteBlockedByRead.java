package learn.java.concurrency.juc.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读锁多个线程共享的，即多个线程获取读锁时不会被阻塞。
 * <p>
 * 当有线程持有读锁时，其他线程或者当前线程在获取写锁会被阻塞。
 *
 * @author ykthree
 * 2021/6/12
 */
public class WriteBlockedByRead {

    public static void main(String[] args) throws InterruptedException {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        new Thread(() -> {
            readLock.lock();
            System.out.println("read.");
            // readLock.unlock();
        }).start();

        TimeUnit.MILLISECONDS.sleep(100);

        readLock.lock();
        System.out.println("read.");
        // readLock.unlock();

        writeLock.lock();
        System.out.println("write.");
    }
}
