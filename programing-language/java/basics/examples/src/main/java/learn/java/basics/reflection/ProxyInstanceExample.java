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
        //System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        System.setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        // 代理逻辑处理类
        InvocationHandler handler = (proxy, method, args1) -> {
            log.info("proxy: {}", proxy);
            log.info("method: {}", method);
            if (method.getName().equals("morning")) {
                log.info("Good morning, {}",  args1[0]);
            }
            return null;
        };

        // 生成动态代理实例
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),
                new Class[]{Hello.class},
                handler);

        hello.morning("Simon");
        // toString 方法也会被增强，此外还有 equals 和 hasCode 方法
        hello.toString();
    }
}

interface Hello {
    void morning(String name);
}

class HelloInstance implements Hello {

    @Override
    public void morning(String name) {
        System.out.println("morning: " + name);
    }
}

class HelloProxy implements Hello {

    private Hello hello;

    HelloProxy(Hello hello) {
        this.hello = hello;
    }

    @Override
    public void morning(String name) {
        //...
        hello.morning(name);
    }
}