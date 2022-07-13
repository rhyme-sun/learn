package learn.spring.ioc.instantiation.callback;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * MyMethodInterceptor.
 */
public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println(method);
        System.out.println(methodProxy);
        return methodProxy.invokeSuper(o, objects);
    }
}
