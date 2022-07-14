package learn.java.basics.equals;

/**
 * ClassSameExample.
 */
public class ClassSameExample {

    public static void main(String[] args) {
        Object a = 1;
        System.out.println(a.getClass().isAssignableFrom(Number.class));  // false
        System.out.println(Number.class.isAssignableFrom(a.getClass()));  // true


        System.out.println(a instanceof Number);
        System.out.println(a instanceof Integer);
        System.out.println(Number.class.isInstance(a));
        System.out.println(Integer.class.isInstance(a));
        System.out.println(Long.class.isInstance(a));
    }
}
