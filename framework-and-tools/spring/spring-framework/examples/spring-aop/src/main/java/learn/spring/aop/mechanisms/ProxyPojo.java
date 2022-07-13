package learn.spring.aop.mechanisms;

import org.springframework.aop.framework.AopContext;

/**
 * ProxyPojo.
 */
public class ProxyPojo implements Pojo {

    @Override
    public void foo() {
        System.out.println("target call...");
        // 使用当前类的代理类
        ((Pojo) AopContext.currentProxy()).bar();
    }

    @Override
    public void bar() {
        System.out.println("bar...");
    }
}
