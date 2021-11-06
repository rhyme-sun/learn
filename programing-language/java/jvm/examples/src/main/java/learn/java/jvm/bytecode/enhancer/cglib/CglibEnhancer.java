package learn.java.jvm.bytecode.enhancer.cglib;

import java.lang.reflect.Method;

import learn.java.jvm.bytecode.enhancer.Base;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base 字节码增强代理类，基于 Cglib
 */
public class CglibEnhancer {

    static Enhancer enhancer;

    static {
        enhancer = new Enhancer();
        enhancer.setSuperclass(Base.class);
        enhancer.setCallback(new TimingInterceptor());
    }

    public static Base createEnhancedBase() {
        return (Base) enhancer.create();
    }

    static class TimingInterceptor implements MethodInterceptor {

        private static final Logger log = LoggerFactory.getLogger(TimingInterceptor.class);

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            long start = System.currentTimeMillis();
            Object proceed = proxy.invokeSuper(obj, args);
            long end = System.currentTimeMillis();
            log.info("Duration: {}", end - start);
            return proceed;
        }
    }
}
