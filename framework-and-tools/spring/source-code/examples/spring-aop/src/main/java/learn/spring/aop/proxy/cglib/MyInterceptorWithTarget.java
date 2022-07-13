package learn.spring.aop.proxy.cglib;

import java.lang.reflect.Method;
import java.util.Objects;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * MyInterceptorWithTarget.
 */
public class MyInterceptorWithTarget implements MethodInterceptor {

    private Calculator target;

    public MyInterceptorWithTarget(Calculator target) {
        this.target = Objects.requireNonNull(target);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return method.invoke(target, objects);
    }
}