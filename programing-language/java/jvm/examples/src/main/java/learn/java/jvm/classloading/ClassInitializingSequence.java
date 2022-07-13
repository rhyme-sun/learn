package learn.java.jvm.classloading;

/**
 * ClassInitializingSequence.
 */
public class ClassInitializingSequence {

    public static void main(String[] args) {
        /**
         * 调用了子类构造方法，输出结果：
         *
         * 1.1.Parent's static attribute1.
         * 1.2.Parent's static attribute2.
         * 2.Parent's static code block.
         * 3.1.Child's static attribute1.
         * 3.2.Child's static attribute2.
         * 4.Child's static code block.
         * 5.Parent's attribute.
         * 6.Parent's code block.
         * 7.Parent's constructor.
         * 8.Child's attribute.
         * 9.Child's code block.
         * 10.Child's constructor.
         */
        Child child = new Child();

        /**
         * 调用了子类的静态属性，输出结果：
         *
         * 1.1.Parent's static attribute1.
         * 1.2.Parent's static attribute2.
         * 2.Parent's static code block.
         * 3.1.Child's static attribute1.
         * 3.2.Child's static attribute2.
         * 4.Child's static code block.
         */
        // Consumer childConsumer = Child.staticConsumer2;

        /**
         * 调用了父类的静态属性，输出结果：
         *
         * 1.1.Parent's static attribute1.
         * 1.2.Parent's static attribute2.
         * 2.Parent's static code block.
         */
        // Consumer parentConsumer = Parent.staticConsumer1;

        /**
         * 调用了外部类的静态属性，输出结果：
         *
         * Outer class's static attribute.
         */
        // Consumer outerConsumer = Outer.outerConsumer;

        /**
         * 调用了外部了的静态属性，输出结果：
         *
         * Inner class's static attribute.
         */
        // Consumer innerConsumer = Outer.Inner.innerConsumer;
    }
}

class Parent {

    static Consumer staticConsumer1 = new Consumer("1.1.Parent's static attribute1.");
    static Consumer staticConsumer2 = new Consumer("1.2.Parent's static attribute2.");
    Consumer consumer = new Consumer("5.Parent's attribute.");

    static {
        System.out.println("2.Parent's static code block.");
    }

    {
        System.out.println("6.Parent's code block.");
    }

    Parent() {
        System.out.println("7.Parent's constructor.");
    }
}

class Child extends Parent {

    static Consumer staticConsumer1 = new Consumer("3.1.Child's static attribute1.");
    static Consumer staticConsumer2 = new Consumer("3.2.Child's static attribute2.");
    Consumer consumer = new Consumer("8.Child's attribute.");

    static {
        System.out.println("4.Child's static code block.");
    }

    {
        System.out.println("9.Child's code block.");
    }

    Child() {
        System.out.println("10.Child's constructor.");
    }
}

class Outer {

    static Consumer outerConsumer = new Consumer("Outer class's static attribute.");

    static class Inner {

        static Consumer innerConsumer = new Consumer("Inner class's static attribute.");
    }
}

class Consumer {

    Consumer(String s) {
        System.out.println(s);
    }
}
