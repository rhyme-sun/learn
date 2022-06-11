package learn.design.pattern.structural.adapter;

import java.util.Objects;

/**
 * 基于实现的适配器
 */
public class AdapterBaseImplement implements Target {

    private Adaptee adaptee;

    public AdapterBaseImplement(Adaptee adaptee) {
        this.adaptee = Objects.requireNonNull(adaptee, "adaptee can not be null");
    }

    @Override
    public void f1() {
        adaptee.fa();
    }

    @Override
    public void f2() {
        System.out.println("Implement f2");
    }

    @Override
    public void f() {
        adaptee.f();
    }
}
