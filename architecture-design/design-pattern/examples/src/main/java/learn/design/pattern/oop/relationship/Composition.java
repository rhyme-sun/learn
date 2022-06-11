package learn.design.pattern.oop.relationship;

/**
 * 组合，A 类对象包含 B 类对象，B 类对象的生命周期依赖 A 类对象的生命周期，B 类对象不可单独存在，比如鸟与翅膀之间的关系。
 */
public class Composition {

    class A {

        private B b;

        A () {
            this.b = new B();
        }
    }

    class B {}
}
