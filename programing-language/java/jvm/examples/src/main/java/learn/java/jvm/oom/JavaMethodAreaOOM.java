package learn.java.jvm.oom;

import java.lang.reflect.Method;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * JavaMethodAreaOOM.
 * JDK 7: -XX:PermSize=10M -XX:MaxPermSize=10M
 * JDK 8: -XX:MaxMetaspaceSize=10M
 */
public class JavaMethodAreaOOM {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\LearnProjects\\learn\\programing-language\\java\\jvm\\examples");

        try {
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMObject.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MyMethodInterceptor());
                enhancer.create();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    static class MyMethodInterceptor implements MethodInterceptor {
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            return methodProxy.invokeSuper(o, objects);
        }
    }

    static class OOMObject {
    }
}
