package learn.design.pattern.oop.relationship;

/**
 * 聚合，A 类对象包含 B 类对象，B 类对象的生命周期可以不依赖 A 类对象的生命周期，也就是说可以单独销毁 A 类对象而不影响 B 对象，
 * 比如学生与课程之间的关系。
 */
public class Aggregation {

    class A {

        private B b;

        A (B b) {
            this.b = b;
        }
    }

    class B {}
}
