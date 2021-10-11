package learn.java.concurrency.lock;

import java.util.concurrent.TimeUnit;

/**
 * 对一个对象的写操作加锁，读操作不加锁，则有可能读取到一个对象的中间状态。
 */
public class ReadMediateState {

    private String name;
    private int balance;

    synchronized void set(String name, int balance) {
        this.name = name;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public ReadMediateState get() {
        return this;
    }

    public synchronized ReadMediateState syncGet() {
        return this;
    }

    @Override
    public String toString() {
        return "ReadMediateState{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        ReadMediateState state = new ReadMediateState();

        Thread t1 = new Thread(() -> state.set("test", 1));
        Thread t2 = new Thread(() -> System.out.println(state.get()));
        Thread t3 = new Thread(() -> System.out.println(state.syncGet()));

        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
    }
}
