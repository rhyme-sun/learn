package learn.java.basics.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * ProxyInstanceExample.
 */
@Slf4j
public class ProxyInstanceExample {

    public static void main(String[] args) {
        InvocationHandler handler = (proxy, method, args1) -> {
            log.info("proxy: {}", proxy);
            log.info("method: {}", method);
            if (method.getName().equals("morning")) {
                log.info("Good morning, {}",  args1[0]);
            }
            return null;
        };

        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                handler);
        hello.morning("Simon");
    }
}

interface Hello {
    void morning(String name);
}