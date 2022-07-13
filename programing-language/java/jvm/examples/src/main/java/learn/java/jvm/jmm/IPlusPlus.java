package learn.java.jvm.jmm;

/**
 * i = i++;
 * i = ++i;
 */
public class IPlusPlus {

    public static void main(String[] args) {
        // 8
        testIPlusPlus();
        // 9
        testPlusPlusI();
    }

    static void testIPlusPlus() {
        int i = 8;
        i = i++;
        System.out.println(i);
    }

    static void testPlusPlusI() {
        int i = 8;
        i = ++i;
        System.out.println(i);
    }
}
