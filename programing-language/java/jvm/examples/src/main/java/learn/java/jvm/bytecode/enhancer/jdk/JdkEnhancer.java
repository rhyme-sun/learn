package learn.java.jvm.bytecode.enhancer.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import learn.java.jvm.bytecode.enhancer.Base;
import learn.java.jvm.bytecode.enhancer.IBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IBase 增强代理类，基于 JDK 动态代理
 */
public class JdkEnhancer {

    public static IBase createEnhancedBase() {
        return (IBase) Proxy.newProxyInstance(IBase.class.getClassLoader(), new Class[]{IBase.class},
                new TimingInterceptor());
    }

    static class TimingInterceptor implements InvocationHandler {

        private static final Logger log = LoggerFactory.getLogger(TimingInterceptor.class);

        private final Base delegate = new Base();

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            long start = System.currentTimeMillis();
            delegate.process();
            long end = System.currentTimeMillis();
            log.info("Duration: {}", end - start);
            return null;
        }
    }
}
