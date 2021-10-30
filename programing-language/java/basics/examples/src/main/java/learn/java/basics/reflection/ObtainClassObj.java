package learn.java.basics.reflection;

import lombok.extern.slf4j.Slf4j;

/**
 * ObtainClassObj.
 */
@Slf4j
public class ObtainClassObj {

    public static void main(String[] args) throws ClassNotFoundException {
        // class java.lang.String
        final Class<String> stringClass = String.class;
        // class java.lang.String
        final Class<? extends String> aClass1 = new String().getClass();
        // class java.lang.String
        final Class<?> aClass2 = Class.forName("java.lang.String");

        log.info("{}", stringClass);
        log.info("{}", aClass1);
        log.info("{}", aClass2);
    }
}