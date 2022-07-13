package learn.spring.ioc.instantiation.callback;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * InstantiationCallbackExample.
 * 通过 BPP（）提前实例化并初始化代理 bean
 */
public class InstantiationCallbackExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:instantiation-callback.xml");
        ac.getBean(BeforeInstantiation.class).doSomeThing();
    }
}
