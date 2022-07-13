package learn.spring.aop.proxy.jdk;

/**
 * MyCalculator.
 */
public class MyCalculator implements Calculator {

    private long version = System.currentTimeMillis();

    @Override
    public int add(int i, int j) {
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
    public Calculator self() {
        return this;
    }
}