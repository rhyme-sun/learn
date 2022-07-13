package learn.java.basics.feature;

/**
 * FinallyExample.
 */
public class FinallyExample {

    public static void main(String[] args) {
        System.out.println(run1()); // 0
        System.out.println(run2()); // 1
    }

    private static int run1() {
         int x = 0;
        try {
            return x;
        } finally {
            x++;
        }
    }

    private static int run2() {
        int x = 0;
        try {
            return x;
        } finally {
            x++;
            return x;
        }
    }
}
