package learn.java.basics.reflection;

import lombok.extern.slf4j.Slf4j;

/**
 * SuperClassExample.
 */
@Slf4j
public class SuperClassExample {

    public static void main(String[] args) {
//        example1();
        example2();
    }

    private static void example1() {
        final Class<Integer> integerClass = Integer.class;
        final Class<? super Integer> superclass = integerClass.getSuperclass();
        // class java.lang.Number
        log.info("{}", superclass);

        final Class<? super Integer> superclass1 = superclass.getSuperclass();
        // class java.lang.Object
        log.info("{}", superclass1);

        final Class<? super Integer> superclass2 = superclass1.getSuperclass();
        // class java.lang.Object
        log.info("{}", superclass1);
    }

    private static void example2() {
        Class s = Integer.class;
        Class[] is = s.getInterfaces();
        // interface java.lang.Comparable
        for (Class i : is) {
            log.info("{}", i);
        }
    }
}