package learn.spring.ioc.cycle;

/**
 * A.
 */
public class A {

    private B b;

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
