package learn.spring.aop.proxy.jdk;

import java.util.Objects;

/**
 * JdkProxyExample.
 */
public class JdkProxyExample {

    public static void main(String[] args) {
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        while (true) {
            System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
            Calculator proxy = CalculatorProxy.getProxy(new MyCalculator());
            System.out.println("Proxy object: " + proxy);
            System.out.println("Proxy class: " + proxy.getClass());

            Calculator target = proxy.self();
            System.out.println("Target object: " + target);
            System.out.println("Target class: " + target.getClass());

            System.out.println("target == proxy ? " + Objects.equals(target, proxy)); // false
            proxy.add(1, 1);
        }

    }
}