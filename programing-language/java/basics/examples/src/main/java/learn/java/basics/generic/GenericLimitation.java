package learn.java.basics.generic;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * GenericLimitation.
 * Java 泛型的局限性。
 */
@Slf4j
public class GenericLimitation {

    public static void main(String[] args) {
        example1();
    }

    static void example1() {
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        Class<? extends List> aClass1 = list1.getClass();
        Class<? extends List> aClass2 = list2.getClass();
        // true
        log.info("{}", aClass1 == aClass2);
        // true
        log.info("{}", aClass1 == ArrayList.class);
    }
}