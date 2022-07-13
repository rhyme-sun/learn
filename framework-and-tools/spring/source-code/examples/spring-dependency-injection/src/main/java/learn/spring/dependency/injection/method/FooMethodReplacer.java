package learn.spring.dependency.injection.method;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

/**
 * FooMethodReplacer.
 */
public class FooMethodReplacer implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        System.out.println("Replace method: ");
        System.out.println(method);
        System.out.println(obj);
        return null;
    }
}
