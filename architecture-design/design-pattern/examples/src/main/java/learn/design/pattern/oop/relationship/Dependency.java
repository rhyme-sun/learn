package learn.design.pattern.oop.relationship;

/**
 * 依赖（Dependency）是一种比关联关系更加弱的关系，包含关联关系。代码层面，只要 A 类对象使用了 B 类对象，不管是 B 类对象是 A 类对象的成员变量，
 * 还是 A 类的方法使用 B 类对象作为参数或者返回值、局部变量，我们都称 A 依赖了 B。
 */
public class Dependency {

    class A {

        void func(B b) {

        }
    }

    class B {}
}
