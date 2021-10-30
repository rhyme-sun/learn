package learn.java.basics.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import lombok.extern.slf4j.Slf4j;

/**
 * FieldExample.
 */
@Slf4j
public class FieldExample {

    public static void main(String[] args) {
//        example1();
//        example2();
        example3();
    }

    private static void example1() {
        try {
            Class appleClass = Apple.class;
            //  public java.lang.String learn.java.basics.reflection.Apple.color
            log.info("{}", appleClass.getField("color"));
            // java.lang.NoSuchFieldException: price
            // log.info("{}", appleClass.getField("price"));
            // public java.lang.String learn.java.basics.reflection.Fruit.name
            log.info("{}", appleClass.getField("name"));

            // public java.lang.String learn.java.basics.reflection.Apple.color
            log.info("{}", appleClass.getDeclaredField("color"));
            // private int learn.java.basics.reflection.Apple.price
            log.info("{}", appleClass.getDeclaredField("price"));
            // java.lang.NoSuchFieldException: name
            // log.info("{}", appleClass.getDeclaredField("name"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * private final byte[] value;
     */
    private static void example2() {
        try {
            Field f = String.class.getDeclaredField("value");
            // value
            log.info("{}", f.getName());
            // class [B
            log.info("{}", f.getType());
            int m = f.getModifiers();

            // false
            log.info("is public: {}", Modifier.isPublic(m));
            // false
            log.info("is protected: {}",  Modifier.isProtected(m));
            // true
            log.info("is private: {}", Modifier.isPrivate(m));
            // true
            log.info("is final: {}", Modifier.isFinal(m));
            // false
            log.info("is static: {}", Modifier.isStatic(m));
            // ...
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void example3() {
        try {
            Fruit fruit = new Apple("Apple");
            final Field name = fruit.getClass().getField("name");
            final Object o = name.get(fruit);
            //  Apple
            log.info("{}", o);
            // 如果属性不是 public，需设置 accessible 为 true.
            // name.setAccessible(true);
            name.set(fruit, "Green Apple");
            final Object o2 = name.get(fruit);
            // Green Apple
            log.info("{}", o2);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

class Apple extends Fruit {

    public String color;
    private int price;

    public Apple(String name) {
        super(name);
    }
}

class Fruit {

    public Fruit() {
    }

    public Fruit(String name) {
        this.name = name;
    }

    public String name;
}
