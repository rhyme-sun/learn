package learn.java.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * synchronized 关键字的使用，T04_NotifyFreeLock。
 * <p>
 * 非静态方法使用 synchronized 修饰，相当于 synchronized(this)。
 * 静态方法使用 synchronized 修饰，相当于 synchronized(SynchronizedLock.class)。
 *
 * @see <a href="https://www.raychase.net/698">四火的唠叨.Java 多线程发展历史</a>
 */
@Slf4j
public class SynchronizedLock {

    private static final Object lock = new Object();

    /**
     * lock on an object
     */
    public void objectBlock() {
        synchronized (lock) {
            log.info("object block");
            sleep(2000);
        }
    }

    /**
     * lock on dynamic method，use this as lock.
     */
    public synchronized void dynamicMethod() {
        log.info("dynamic method");
        // 可重入
        thisBlock();
        sleep(2000);
    }

    /**
     * lock on this
     */
    public void thisBlock() {
        synchronized (this) {
            log.info("this block");
            sleep(2000);
        }
    }

    /**
     * lock on static method，use SynchronizedLock.class as lock.
     */
    public static synchronized void staticMethod() {
        log.info("static method");
        sleep(2000);
    }

    /**
     * lock on the class
     */
    public void classBlock() {
        synchronized (SynchronizedLock.class) {
            log.info("static block");
            sleep(2000);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SynchronizedLock lock = new SynchronizedLock();

        // object lock test
        new Thread(lock::dynamicMethod).start();
        new Thread(lock::thisBlock).start();

        new Thread(lock::objectBlock).start();

        sleep(3000);
        System.out.println();

        // class lock test
        new Thread(SynchronizedLock::staticMethod).start();
        new Thread(lock::classBlock).start();
    }
}