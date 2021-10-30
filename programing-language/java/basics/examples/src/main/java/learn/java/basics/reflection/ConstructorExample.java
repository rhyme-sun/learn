package learn.java.basics.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

/**
 * ConstructorExample.
 */
@Slf4j
public class ConstructorExample {

    public static void main(String[] args) {
        example1();
    }

    private static void example1() {
        try {
            Constructor cons1 = Integer.class.getConstructor(int.class);
            Integer n1 = (Integer) cons1.newInstance(123);
            log.info("{}", n1);

            Constructor cons2 = Integer.class.getConstructor(String.class);
            Integer n2 = (Integer) cons2.newInstance("456");
            log.info("{}", n2);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}