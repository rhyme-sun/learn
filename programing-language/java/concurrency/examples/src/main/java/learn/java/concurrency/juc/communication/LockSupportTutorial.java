package learn.java.concurrency.juc.communication;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * {@link LockSupport}
 */
public class LockSupportTutorial {

    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 先执行 LockSupport#unpark 方法，线程 t 中的 park 方法不会生效。
        // LockSupport.unpark(t);
        t.start();
        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("After 8 seconds.");
        LockSupport.unpark(t);
    }
}
