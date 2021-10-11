package learn.java.concurrency.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLockTutorial.
 */
public class ReadWriteLockTutorial {

    private int value = 1;

    public int read(Lock lock) {
        try {
            lock.lock();
            System.out.println("Read.");
            TimeUnit.SECONDS.sleep(1);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return value;
        } finally {
            lock.unlock();
        }
    }

    public void write(Lock lock, int value) {
        try {
            lock.lock();
            this.value = value;
            System.out.println("Write.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 互斥锁，读和写操作互斥进行
        // ReentrantLock lock = new ReentrantLock();
        // run(lock, lock);

        // 读写锁，读操作并发重入，写操作互斥进行
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        // run(readLock, writeLock);

        // 写时，禁止其他线程读
        // forbidReadWhileWriting(readLock, writeLock);
        // 读时，禁止其他线程写
        // forbidWriteWhileReading(readLock, writeLock);

        // 锁降级
        // lockDegrade(readLock, writeLock);

        // 锁升级
        Thread t = new Thread(() -> {
            lockUpgrade(readLock, writeLock);
        });
        t.start();
        Thread.sleep(1000);
        System.out.println(t.getState());
        t.join();
    }

    private static void run(Lock readLock, Lock writeLock) {
        ReadWriteLockTutorial tutorial = new ReadWriteLockTutorial();

        Runnable readTask = () -> tutorial.read(readLock);
        Runnable writeTask = () -> tutorial.write(writeLock, 2);

        int readCount = 15;
        int writeCount = 5;
        for (int i = 0; i < readCount; i++) {
            new Thread(readTask).start();
        }
        for (int i = 0; i < writeCount; i++) {
            new Thread(writeTask).start();
        }
    }

    private static void forbidReadWhileWriting(Lock readLock, Lock writeLock) {
        writeLock.lock();
        System.out.println("Write Lock.");

        Thread other = new Thread(() -> {
            readLock.lock();
            System.out.println("Read Lock.");
        });
        other.start();
    }

    private static void forbidWriteWhileReading(Lock readLock, Lock writeLock) {
        readLock.lock();
        System.out.println("Read Lock.");

        Thread other = new Thread(() -> {
            writeLock.lock();
            System.out.println("Write Lock.");
        });
        other.start();
    }

    private static void lockUpgrade(Lock readLock, Lock writeLock) {
        readLock.lock();
        System.out.println("Read Lock.");

        // 不允许锁升级
        writeLock.lock();
        System.out.println("Write Lock.");
    }

    private static void lockDegrade(Lock readLock, Lock writeLock) {
        writeLock.lock();
        System.out.println("Write Lock.");

        // 释放写锁前，降级为读锁
        readLock.lock();
        System.out.println("Read Lock.");
        writeLock.unlock();
        // 写锁释放后，线程依然持有读锁
    }
}
