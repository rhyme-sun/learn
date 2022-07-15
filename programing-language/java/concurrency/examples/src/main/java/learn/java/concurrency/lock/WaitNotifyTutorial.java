package learn.java.concurrency.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 曾经的面试题：
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程 1 添加 10 个元素到容器中，线程 2 实现监控元素的个数，当个数到 5 个时，线程 2 给出提示并结束。
 * <p>
 * 给 lists 添加 volatile 之后，t2 能够接到通知，但是，t2 线程的死循环很浪费 cpu，如果不用死循环，该怎么做呢？
 * <p>
 * 这里使用 wait 和 notify 做到，wait会释放锁，而 notify 不会释放锁
 * 需要注意的是，运用这种方法，必须要保证 t2 先执行，也就是首先让 t2 监听才可以。
 * <p>
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是 size=5 时 t2 退出，而是 t1 结束时 t2 才接收到通知而退出。
 * <p>
 * notify 之后，t1 必须释放锁，t2 退出后，也必须 notify，通知 t1 继续执行
 * 整个通信过程比较繁琐
 *
 * By 马士兵老师
 */
public class WaitNotifyTutorial {

    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        WaitNotifyTutorial c = new WaitNotifyTutorial();
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                //通知 t1 继续执行
                lock.notify();
            }

        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);

                    if (c.size() == 5) {
                        lock.notify();
                        //释放锁，让 t2 得以执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1").start();
    }
}

