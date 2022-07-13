package learn.spring.ioc.cycle;

/**
 * B.
 */
public class B {

    private A a;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }
}
