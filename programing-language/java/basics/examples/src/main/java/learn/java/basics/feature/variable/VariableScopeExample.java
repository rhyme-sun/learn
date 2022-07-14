package learn.java.basics.feature.variable;

/**
 * VariableScopeExample.
 */
public class VariableScopeExample {

    void example() {
        int a = 0;
        {
            int b = a;
            {
                b = 1;
            }
        }
        if (true) {
            // 可以访问 a，不能访问 b
            int c = 0;
        }
    }
}
