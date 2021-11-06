package learn.java.jvm.bytecode.enhancer.bytebuddy;

import static net.bytebuddy.matcher.ElementMatchers.named;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import learn.java.jvm.bytecode.enhancer.Base;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base 字节码增强代理类，基于 ByteBuddy
 */
public class ByteBuddyEnhancer {

    public static Base createEnhancedBase() throws Exception {
        final String methodName = "process";
        return new ByteBuddy()
                .subclass(Base.class)
                .method(named(methodName))
                .intercept(MethodDelegation.to(new TimingInterceptor()))
                .make()
                .load(Base.class.getClassLoader())
                .getLoaded()
                .getConstructor()
                .newInstance();
    }

    /**
     * 需要用 public 修饰符
     */
    public static class TimingInterceptor {

        private static final Logger log = LoggerFactory.getLogger(TimingInterceptor.class);

        public Object enhance(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
            long start = System.currentTimeMillis();
            Object call = callable.call();
            long end = System.currentTimeMillis();
            log.info("Duration: {}", end - start);
            return call;
        }
    }
}
