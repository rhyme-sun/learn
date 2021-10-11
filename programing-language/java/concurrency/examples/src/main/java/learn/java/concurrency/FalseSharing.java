package learn.java.concurrency;

/**
 * 缓存行（cache line）伪共享（FalseSharing）引起的效率问题
 *
 * @author ykthree
 * 2021/4/12
 */
public class FalseSharing {

    static volatile long[] arr1 = new long[8];
    static volatile long[] arr2 = new long[16];

    static long loopTimes = 100000000L;

    public static void main(String[] args) throws InterruptedException {
        // 伪共享：460
        falseSharing();
        // 缓存行对齐：293
        cachePadding();
    }

    private static void falseSharing() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < loopTimes; i++) {
                arr1[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (long i = 0; i < loopTimes; i++) {
                arr1[1] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("FalseSharing: " + (System.nanoTime() - start) / 1000000);
    }

    private static void cachePadding() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < loopTimes; i++) {
                arr2[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (long i = 0; i < loopTimes; i++) {
                arr2[8] = i;
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("CachePadding: " + (System.nanoTime() - start) / 1000000);
    }
}
