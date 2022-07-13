package learn.java.basics.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * AnnotationExample.
 */
public class AnnotationExample {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Range {
        int min() default 0;

        int max() default 255;
    }

    static class Person {
        @Range(min = 1, max = 20)
        public String name;

        @Range(max = 10)
        public String city;

        public Person(String name, String city) {
            this.name = name;
            this.city = city;
        }
    }

    public static void main(String[] args) {
        Person person = new Person("Simon", "Xi'an,.........");
        try {
            check(person);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    static void check(Person person) throws IllegalArgumentException, ReflectiveOperationException {
        for (Field field : person.getClass().getFields()) {
            Range range = field.getAnnotation(Range.class);
            if (range != null) {
                Object value = field.get(person);
                if (value instanceof String) {
                    String s = (String) value;
                    if (s.length() < range.min() || s.length() > range.max()) {
                        throw new IllegalArgumentException("Invalid field: " + field.getName());
                    }
                }
            }
        }
    }

}
