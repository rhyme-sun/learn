package learn.java.concurrency.lock;

import lombok.extern.slf4j.Slf4j;

/**
 * synchronized 的可重入性，即同一个线程可以重入同一把锁。
 */
@Slf4j
public class Reentrancy {

    static synchronized void reentrant() {
        log.info("static method");
        synchronized (Reentrancy.class) {
            log.info("static block");
        }
    }

    public static void main(String[] args) {
        // 都使用 Reentrancy Class 对象作为锁，一次执行流程具有可重入性
        synchronized (Reentrancy.class) {
            reentrant();
        }
    }
}
