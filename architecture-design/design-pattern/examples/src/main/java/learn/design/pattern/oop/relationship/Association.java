package learn.design.pattern.oop.relationship;

/**
 * 关联（Association）是一种非常弱的关系，包含聚合、组合两种关系。
 * 具体到代码层面，如果 B 类对象是 A 类的成员变量，那 B 类和 A 类就是关联关系。
 */
public class Association {

    class A {

        private B b;
    }

    class B {}
}
