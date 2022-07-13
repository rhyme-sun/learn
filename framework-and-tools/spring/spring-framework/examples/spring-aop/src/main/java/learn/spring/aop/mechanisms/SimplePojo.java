package learn.spring.aop.mechanisms;

/**
 * SimplePojo
 */
public class SimplePojo implements Pojo {

    @Override
    public void foo() {
        System.out.println("target call...");
        this.bar();
    }

    @Override
    public void bar() {
        System.out.println("bar...");
    }
}
