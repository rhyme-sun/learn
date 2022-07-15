package learn.java.concurrency.lock;

/**
 * 两个线程交替打印。
 * 每个线程的执行逻辑为 获取到锁 -> 唤醒其它线程 -> 打印字符 -> 等待（释放锁）
 */
public class AlternatePrint {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        char[] numbers = "123456".toCharArray();
        char[] chs = "ABCDEF".toCharArray();

        PrintTask task1 = new PrintTask(lock, numbers);
        PrintTask task2 = new PrintTask(lock, chs);

        Thread t1 = new Thread(task1);
        Thread t2 = new Thread(task2);

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }

    static class PrintTask implements Runnable {

        private Object lock;
        private char[] data;
        int index;

        public PrintTask(Object lock, char[] data) {
            this.lock = lock;
            this.data = data;
        }

        @Override
        public void run() {
            while (index < data.length) {
                synchronized (lock) {
                    lock.notify();
                    System.out.print(data[index++]);
                    try {
                        // 释放锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            // notify at last
            synchronized (lock) {
                lock.notify();
            }
         }
    }
}
