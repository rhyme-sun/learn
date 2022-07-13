package learn.spring.aop.mechanisms;

import org.springframework.aop.framework.ProxyFactory;

/**
 * ProxyExample.
 */
public class ProxyExample {

    public static void main(String[] args) {
        callBySelf();
        System.out.println();
        callByProxy();
        System.out.println();
        callByProxy2();
    }

    // 自己调用
    private static void callBySelf() {
        Pojo pojo = new SimplePojo();
        pojo.foo();
    }

    // 通过代理调用，目标对象间方法的平级调用不会走代理类
    private static void callByProxy() {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.setInterfaces(Pojo.class);
        factory.addAdvice(new RetryAdvice());
        Pojo pojo = (Pojo) factory.getProxy();
        pojo.foo();
    }

    // 通过代理调用
    private static void callByProxy2() {
        ProxyFactory factory = new ProxyFactory(new ProxyPojo());
        factory.setInterfaces(Pojo.class);
        factory.addAdvice(new RetryAdvice());
        // expose proxy object
        factory.setExposeProxy(true);
        Pojo pojo = (Pojo) factory.getProxy();
        pojo.foo();
    }
}
