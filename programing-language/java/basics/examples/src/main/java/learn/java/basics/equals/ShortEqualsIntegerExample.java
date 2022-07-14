package learn.java.basics.equals;

import java.util.Objects;

/**
 * ShortEqualsIntegerExample.
 */
public class ShortEqualsIntegerExample {

    public static void main(String[] args) {
        int intNumber = 1;
        short shortNumber = (short) 1;
        System.out.println(Objects.equals(intNumber, shortNumber)); // false
    }
}
