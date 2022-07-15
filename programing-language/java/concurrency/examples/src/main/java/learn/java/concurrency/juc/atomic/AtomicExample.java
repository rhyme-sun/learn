package learn.java.concurrency.juc.atomic;

import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicExample.
 */
public class AtomicExample {

    static AtomicLong count = new AtomicLong(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            count.getAndIncrement();
        }
        System.out.println(count);
    }
}