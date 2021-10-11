package learn.java.concurrency.lock;

/**
 * DeadLock.
 *
 * @author ykthree
 */
public class DeadLockTutorial {

    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    public static void run1() {
        synchronized (lock1) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // dead lock at here
            synchronized (lock2) {
                System.out.println("do something.");
            }
        }
    }

    public static void run2() {
        synchronized (lock2) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // dead lock at here
            synchronized (lock1) {
                System.out.println("do something.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(DeadLockTutorial::run1);
        Thread t2 = new Thread(DeadLockTutorial::run2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}