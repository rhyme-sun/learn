package learn.java.jvm.executive.methodcall;

/**
 * StaticDispatchExample.
 * 静态分派。
 */
public class StaticDispatchExample {

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatchExample sr = new StaticDispatchExample();
        sr.sayHello(man);
        sr.sayHello(woman);
    }

    public void sayHello(Human human) {
        System.out.println("Hello,guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello,lady!");
    }

    static abstract class Human {}

    static class Man extends Human {}

    static class Woman extends Human {}
}
