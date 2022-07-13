package learn.spring.aop.proxy.cglib;

import java.util.Objects;

import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;

/**
 * CglibProxyExample.
 */
public class CglibProxyExample {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\LearnProjects\\learn\\framework-and-tools\\spring\\source-code\\examples");

        proxyClass();
        //System.out.println();
        //proxyInterface();
    }

    private static void proxyClass() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyCalculator.class);
        enhancer.setCallback(new MyInterceptor());

        MyCalculator proxy = (MyCalculator) enhancer.create();
        System.out.println("Proxy object: " + proxy);
        System.out.println("Proxy class: " + proxy.getClass());

        MyCalculator target = proxy.self();
        System.out.println("Target object: " + target);
        System.out.println("Target class: " + target.getClass());

        System.out.println("target == proxy ? " + Objects.equals(target, proxy));
        proxy.add(1, 1);

        proxy.staticMethod();
    }

    private static void proxyInterface() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Calculator.class);

        enhancer.setCallback(new MyInterceptorWithTarget(new MyCalculator()));
        Calculator proxy = (Calculator) enhancer.create();
        System.out.println("Proxy object: " + proxy);
        System.out.println("Proxy class: " + proxy.getClass());

        Calculator target = proxy.self();
        System.out.println("Target object: " + target);
        System.out.println("Target class: " + target.getClass());

        System.out.println("target == proxy ? " + Objects.equals(target, proxy));
        proxy.add(1, 1);
    }
}