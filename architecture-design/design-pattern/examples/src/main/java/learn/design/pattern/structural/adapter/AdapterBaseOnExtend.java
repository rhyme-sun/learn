package learn.design.pattern.structural.adapter;

/**
 * 基于继承的适配器
 */
public class AdapterBaseOnExtend extends Adaptee implements Target {

    @Override
    public void f1() {
        super.fa();
    }

    @Override
    public void f2() {
        System.out.println("Overwrite fb");
    }

    // f 方法可以不用实现，直接使用继承的服父类，这是和基于实现适配最大的区别
}
