package learn.java.basics.equals;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * OverrideEqualsAndHasCodeExample.
 */
public class OverrideEqualsAndHasCodeExample {

    public static void main(String[] args) {
        PhoneNumber number1 = new PhoneNumber(1);
        PhoneNumber number2 = new PhoneNumber(1);

        System.out.println(number1.equals(number2)); // true

        System.out.println(number1.hashCode());
        System.out.println(number2.hashCode());

        Set<PhoneNumber> set = new HashSet<>();
        set.add(number1);
        set.add(number2);
        System.out.println(set); // size 2
    }

    static class PhoneNumber {

        private int number;

        public PhoneNumber(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PhoneNumber that = (PhoneNumber) o;
            return number == that.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
    }
}
