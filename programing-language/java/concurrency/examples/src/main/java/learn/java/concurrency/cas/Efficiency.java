package learn.java.concurrency.cas;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子操作效率比较
 */
public class Efficiency {

    /**
     * 每次累计此次数
     */
    private int perCount;

    long count1 = 0;
    AtomicLong count2 = new AtomicLong(0);
    /**
     * 分段锁
     */
    LongAdder count3 = new LongAdder();

    public Efficiency(int perCount) {
        this.perCount = perCount;
    }

    void syncAdd() {
        for (int i = 0; i < perCount; i++) {
            synchronized (this) {
                count1++;
            }
        }
    }

    void atomicAdd() {
        for (int i = 0; i < perCount; i++) {
            count2.incrementAndGet();
        }
    }

    void longAdder() {
        for (int i = 0; i < perCount; i++) {
            count3.increment();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int perCount = 10000;
        int currentCount = 10000;
        Efficiency efficiency = new Efficiency(perCount);
        Runnable syncAddTask = efficiency::syncAdd;
        Runnable atomicAddTask = efficiency::atomicAdd;
        Runnable longAdderTask = efficiency::longAdder;

        // 3111
        run(syncAddTask, currentCount);
        // 1739
        run(atomicAddTask, currentCount);
        // 730
        run(longAdderTask, currentCount);
    }

    private static void run(Runnable task, int currentCount) throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread[] threads = new Thread[currentCount];
        for (int i = 0; i < currentCount; i++) {
            threads[i] = new Thread(task);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println("cost: " + (System.currentTimeMillis() - start));
    }
}
