package learn.java.concurrency.volatilekeyword;

/**
 * 测试 volatile 关键字效果，volatile 可以保证内存可见性，但无法保证原子性。
 *
 * @see <a href="https://www.raychase.net/698">四火的唠叨.Java 多线程发展简史</a>
 */
public class NoAtomicity {

    //    boolean boolValue;
    volatile boolean boolValue;

    public /*synchronized*/ void check() {
        if (boolValue == !boolValue) {
            System.out.println("boolValue == !boolValue");
        }
    }

    public void swap() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolValue = !boolValue;
    }

    public static void main(String[] args) {
        final NoAtomicity volObj = new NoAtomicity();
        Thread t2 = new Thread(() -> {
            while (true) {
                volObj.check();
            }
        });
        t2.start();
        Thread t1 = new Thread(() -> {
            while (true) {
                volObj.swap();
            }
        });
        t1.start();
    }
}
