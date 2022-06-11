package learn.design.pattern.structural.adapter;

/**
 * 适应者，需要通过适配器适配目标接口
 */
public class Adaptee {

    public void fa() {
        System.out.println("I can be used as f1");
    }

    public void fb() {
        System.out.println("I need be overwrote");
    }

    public void f() {
        System.out.println("I am same as f");
    }
}
