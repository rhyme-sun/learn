package learn.java.concurrency.jmm.volatilekeyword;

/**
 * VolatileConcurrentProblem.
 *
 * volatile 不保证原子性的另外一个例子
 */
public class VolatileConcurrentProblem {

    private static volatile int race = 0;

    private static void plus() {
        for (int i = 0; i < 1000; i++) {
            race++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int count = 10;
        Thread[] threads = new Thread[10];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(() -> plus());
        }
        for (int i = 0; i < count; i++) {
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            threads[i].join();
        }
        System.out.println(race);
    }
}
