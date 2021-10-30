package learn.java.basics.reflection;

import java.lang.reflect.InvocationTargetException;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassInfo.
 */
@Slf4j
public class ClassInfo {

    public static void main(String[] args) {
        printClassInfo("".getClass());
        printClassInfo(Runnable.class);
        printClassInfo(java.time.Month.class);
        printClassInfo(String[].class);
        printClassInfo(int.class);

        newInstance();
    }

    static void printClassInfo(Class cls) {
        log.info("Class name: {}", cls.getName());
        log.info("Simple name: {}", cls.getSimpleName());
        if (cls.getPackage() != null) {
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        log.info("is interface: {}", cls.isInterface());
        log.info("is enum: {}", cls.isEnum());
        log.info("is array: {}", cls.isArray());
        log.info("is primitive: {}", cls.isPrimitive());
    }

    static void newInstance() {
        try {
            Class<String> stringClass = String.class;
            // String stringObj = stringClass.newInstance();
            String stringObj = stringClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
