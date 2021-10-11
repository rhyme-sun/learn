package learn.java.concurrency.lock;

/**
 * synchronized 解决并发问题
 */
public class ConcurrentProblem {

    private int count1;
    // volatile 不能保证复合操作的原子性。
    // private volatile int count1;
    private int count2;

    void plus() {
        for (int i = 0; i < 10000; i++) {
            count1++;
        }
    }

    synchronized void syncPlus() {
        for (int i = 0; i < 10000; i++) {
            count2++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentProblem problem = new ConcurrentProblem();
        Runnable task1 = problem::plus;
        Runnable task2 = problem::syncPlus;

        Thread t11 = new Thread(task1);
        Thread t12 = new Thread(task1);

        Thread t21 = new Thread(task2);
        Thread t22 = new Thread(task2);

        t11.start();
        t12.start();
        t21.start();
        t22.start();

        t11.join();
        t12.join();
        t21.join();
        t22.join();

        System.out.println("count1: " + problem.count1);
        System.out.println("count2: " + problem.count2);
    }
}
