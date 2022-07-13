package learn.spring.aop.proxy.cglib;

import java.lang.reflect.Method;
import java.util.Objects;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * MyInterceptor.
 */
public class MyInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 死递归
        // return method.invoke(o, objects);
        if (Objects.equals("add", method.getName())) {
            System.out.println("Aspect add ....");
        }
        return methodProxy.invokeSuper(o, objects);
    }
}