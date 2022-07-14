package learn.java.basics.feature.variable;

/**
 * LocalVariableExample.
 */
public class LocalVariableExample {

    public static void main(String[] args) {
        final LocalVariableExample localVariableExample = new LocalVariableExample();
        localVariableExample.func(1);
    }

    void func(int a) {
        int c = 6;
        int b = 5;
        System.out.println(a + b + c);
    }
    
    void ifFunc(int a) {
        if (a > 0) {
            int b = a;
            System.out.println(b);
        } else {
            int c = a + 1;
            System.out.println(c);
        }
    }
    
    void loopFunc() {
        for (int i = 0; i < 10; i++) {
            int c = i + 1;
            System.out.println(c);
        }
    }
}
