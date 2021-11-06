package learn.java.jvm.bytecode.enhancer.aspect;

import learn.java.jvm.bytecode.enhancer.Base;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link Base#process()} 拦截配置
 */
@Aspect
public class BaseProcessMethodAspect {

    private static final Logger log = LoggerFactory.getLogger(BaseProcessMethodAspect.class);

    @Pointcut("execution(* learn.java.jvm.bytecode.enhancer.Base.process(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info("Duration: {}", end - start);
        return proceed;
    }
}
