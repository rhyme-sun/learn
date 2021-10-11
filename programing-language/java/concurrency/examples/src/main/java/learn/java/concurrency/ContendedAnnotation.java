package learn.java.concurrency;

//import sun.misc.Contended;

/**
 * 避免竞争，使用{@link sun.misc.Contended} 避免成员变量位于同一缓存行，并添加 -XX:-RestrictContended 参数使得注解生效。
 *
 * @author ykthree
 * 2021/4/12
 */
public class ContendedAnnotation {

    static int loopTimes = 100000000;
    //    @Contended
    volatile int x;
    //    @Contended
    volatile int y;

    public static void main(String[] args) throws InterruptedException {
        // 使用注解：525；不使用注解：1821
        ContendedAnnotation t = new ContendedAnnotation();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                t.x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < loopTimes; i++) {
                t.y = i;
            }
        });
        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("CachePadding: " + (System.nanoTime() - start) / 100_0000);
    }
}
