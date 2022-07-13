package learn.spring.aop.support;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * NotVeryUsefulAspect.
 */
@Aspect
public class NotVeryUsefulAspect {

    @Pointcut("execution(* transfer(..))") // the pointcut expression
    private void anyOldTransfer() {} // the pointcut signature
}
