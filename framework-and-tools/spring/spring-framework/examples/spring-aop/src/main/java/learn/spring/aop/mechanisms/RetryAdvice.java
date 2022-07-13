package learn.spring.aop.mechanisms;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

/**
 * RetryAdvice.
 */
public class RetryAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("RetryAdvice...");
    }
}
