package learn.spring.aop.proxy.cglib;

/**
 * MyCalculator.
 */
public class MyCalculator implements Calculator {

    @Override
    public int add(int i, int j) {
        self();
        int result = i + j;
        return result;
    }

    @Override
    public int sub(int i, int j) {
        int result = i - j;
        return result;
    }

    @Override
    public int mul(int i, int j) {
        int result = i * j;
        return result;
    }

    @Override
    public int div(int i, int j) {
        int result = i / j;
        return result;
    }

    @Override
    public MyCalculator self() {
        return this;
    }

    public static void staticMethod() {
        System.out.println("static method...");
    }
}
