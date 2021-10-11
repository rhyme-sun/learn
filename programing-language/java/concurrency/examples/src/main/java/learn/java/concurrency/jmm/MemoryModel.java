package learn.java.concurrency.jmm;

import java.util.Map;

/**
 * MemoryModel
 *
 * @author ykthree
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