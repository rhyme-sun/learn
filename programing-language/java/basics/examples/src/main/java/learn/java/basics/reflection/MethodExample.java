package learn.java.basics.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;

/**
 * MethodExample.
 */
@Slf4j
public class MethodExample {

    public static void main(String[] args) {
//        example1();
//        example2();
//        example3();
//        example4();
        example5();
    }

    private static void example1() {
        try {
            Class studentClass = Student.class;
            // public int learn.java.basics.reflection.Student.getScore(java.lang.String)
            log.info("{}", studentClass.getMethod("getScore", String.class));
            // public java.lang.String learn.java.basics.reflection.Person.getName()
            log.info("{}", studentClass.getMethod("getName"));
            // private int learn.java.basics.reflection.Student.getGrade(int)
            log.info("{}", studentClass.getDeclaredMethod("getGrade", int.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射调用 {@link String#substring(int)} 方法，如：
     * <pre>
     * String s = "Hello world";
     * String r = s.substring(6); // "world"
     * </pre>
     */
    private static void example2() {
        try {
            String s = "Hello world";
            Method m = String.class.getMethod("substring", int.class);
            String r = (String) m.invoke(s, 6);
            log.info(r);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用反射调用静态方法 {@link Integer#parseInt(String)}
     */
    private static void example3() {
        try {
            Method m = Integer.class.getMethod("parseInt", String.class);
            Integer result = (Integer) m.invoke(null, "12345");
            log.info("{}", result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void example4() {
        try {
            Person p = new Person();
            Method m = p.getClass().getDeclaredMethod("setName", String.class);
            m.setAccessible(true);
            m.invoke(p, "Simon");
            log.info(p.name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void example5() {
        try {
            final Method hello = Person.class.getMethod("hello");
            final Object invoke = hello.invoke(new Student());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class Student extends Person {

    public int getScore(String type) {
        return 99;
    }

    private int getGrade(int year) {
        return 1;
    }

    public void hello() {
        System.out.println("Student");
    }
}

class Person {

    String name;

    public String getName() {
        return "Person";
    }

    private void setName(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.println("Person");
    }
}