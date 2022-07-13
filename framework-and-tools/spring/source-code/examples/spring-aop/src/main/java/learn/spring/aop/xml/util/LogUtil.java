package learn.spring.aop.xml.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;

/**
 * LogUtil.
 */
public class LogUtil {

    public int start(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        System.out.println("Before: " + signature.getName());
        return 100;
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        Object[] args = pjp.getArgs();
        Object result = null;
        try {
            System.out.println("Around start: " + signature.getName());
            // 通过反射的方式调用目标的方法，相当于执行 method.invoke()，可以自己修改结果值
            result = pjp.proceed(args);
            System.out.println("Around end: " + signature.getName());
        } catch (Throwable throwable) {
            System.out.println("AfterThrowing in around: " + signature.getName());
            throw throwable;
        } finally {
            System.out.println("AfterFinally in around: " + signature.getName());
        }
        return result;
    }

    public void logFinally(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        System.out.println("AfterFinally: " + signature.getName());
    }

    public void stop(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        System.out.println("AfterReturning: " + signature.getName());
    }

    public void logException(JoinPoint joinPoint, Exception e) {
        Signature signature = joinPoint.getSignature();
        System.out.println("AfterThrowing: " + signature.getName());
    }
}
