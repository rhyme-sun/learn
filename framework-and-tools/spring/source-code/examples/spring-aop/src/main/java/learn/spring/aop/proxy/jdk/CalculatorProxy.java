package learn.spring.aop.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * CalculatorProxy.
 */
public class CalculatorProxy {

    public static Calculator getProxy(final Calculator calculator) {
        ClassLoader loader = calculator.getClass().getClassLoader();
        Class<?>[] interfaces = calculator.getClass().getInterfaces();

        InvocationHandler h = (proxy, method, args) -> {
            Object result = null;
            try {
                result = method.invoke(calculator, args);
            } catch (Exception e) {
            }
            return result;
        };

        Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
        return (Calculator) proxy;
    }
}