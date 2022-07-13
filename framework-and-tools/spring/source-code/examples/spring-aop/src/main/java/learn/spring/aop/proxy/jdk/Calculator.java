package learn.spring.aop.proxy.jdk;

/**
 * Calculator.
 */
public interface Calculator {

     int add(int i, int j);

     int sub(int i, int j);

     int mul(int i, int j);

     int div(int i, int j);

     Calculator self();

     static void staticMethod() {
          System.out.println("Static method int Calculator...");
     }
}