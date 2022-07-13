package learn.spring.aop.proxy.cglib;

import java.util.Objects;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.core.KeyFactory;

/**
 * KeyFactoryExample.
 */
public class KeyFactoryExample {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"E:\\LearnProjects\\learn\\framework-and-tools\\spring\\source-code\\examples");

        IntStringKey factory = (IntStringKey) KeyFactory.create(IntStringKey.class);
        Object key1 = factory.newInstance(4, "Hello");
        Object key2 = factory.newInstance(4, "Hello");
        Object key3 = factory.newInstance(4, "World");

        System.out.println(key1);
        System.out.println(key2);
        System.out.println(Objects.equals(key1, key2));

        System.out.println(key3);
        System.out.println(Objects.equals(key1, key3));
    }

    private interface IntStringKey {
        Object newInstance(int i, String s);
    }
}
