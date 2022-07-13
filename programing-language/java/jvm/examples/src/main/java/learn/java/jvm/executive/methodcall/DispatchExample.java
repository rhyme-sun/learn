package learn.java.jvm.executive.methodcall;

/**
 * DispatchExample.
 * Java 语言是一门静态多分派、动态单分派的语言。
 *
 */
public class DispatchExample {

    public static void main(String[] args) {
        Father son = new Son();
        son.showNumber(1);
    }

    static class Father {

        public void showNumber(Number number) {
            System.out.println("father show number " + number);
        }

        public void showNumber(Integer integer) {
            System.out.println("father show integer " + integer);
        }
    }

    static class Son extends Father {

        public void showNumber(Number number) {
            System.out.println("son show number " + number);
        }

        public void showNumber(Integer integer) {
            System.out.println("son show integer " + integer);
        }
    }
}
