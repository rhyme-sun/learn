package learn.java.jvm.bytecode;

/**
 * BytecodeExample3.
 */
public class BytecodeExample3 {

    public void foo() {
        int a = 1;
        int b = 2;

        int c = a;
        a = b;
        b = c;
    }
}