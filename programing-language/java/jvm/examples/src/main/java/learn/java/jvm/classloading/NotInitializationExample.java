package learn.java.jvm.classloading;

/**
 * NotInitializationExample.
 */
public class NotInitializationExample {

    public static void main(String[] args) throws ClassNotFoundException {
        notInitialization5();
    }

    // 通过子类调用父类静态变量，不会触发子类的初始化，会初始化父类
    private static void notInitialization1() {
        System.out.println(Subclass.value);
    }

    // 通过数组来定义引用类，不会触发此类的初始化
    private static void notInitialization2() {
        SuperClass[] superClasses = new SuperClass[1];
        System.out.println(superClasses);
    }

    // 常量在编译阶段就放入常量池，使用时不会触发引用类的初始化
    private static void notInitialization3() {
        System.out.println(ConstClass.HELLO);
    }

    // 使用类名.class 的方式获取类对象不会出发类的初始化
    private static void notInitialization4() throws ClassNotFoundException {
        System.out.println(ConstClass.class);
        // 使用 forName 的方法获取 Class 对象会出发类初始化
        // System.out.println(Class.forName("learn.java.jvm.classloading.ConstClass"));
    }

    // 外部类加载时不会加载内部类
    private static void notInitialization5() {
        System.out.println(new Outclass());
    }

}


class SuperClass {

    public static int value = 1;

    static {
        System.out.println("super class init...");
    }
}

class Subclass extends SuperClass {

    static {
        System.out.println("sub class init...");
    }
}

class ConstClass {

    public static final String HELLO = "hello";

    static {
        System.out.println("const class init...");
    }
}


class Outclass {

    static class InnerClass {

        static {
            System.out.println("inner class init...");
        }
    }
}

