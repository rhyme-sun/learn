package learn.spring.ioc.instantiation.callback;

import org.springframework.context.annotation.Bean;

/**
 * BeforeInstantiation.
 * 初始化前被代理创建.
 */
public class BeforeInstantiation {

    public void doSomeThing() {
        System.out.println("BeforeInstantiation#doSomeThing...");
    }
}
