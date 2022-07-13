package learn.java.concurrency.jmm;

/**
 * MemoryModel.
 * 指令重排测试，如果多线程情况下，(a,b) 的值出现了 (1,1) 的情况，则说明了发生了指令重排。
 */
public class MemoryModel {

    private int a = 0;
    private int b = 0;

    public void method1() {
        int r2 = a;
        b = 1;
    }

    public void method2() {
        int r1 = b;
        a = 1;
    }
}