package learn.spring.dependency.injection.method;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MethodInjectExample.
 * 单例 bean 中获取原型 bean.
 */
public class MethodInjectionExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:META-INF/method-injection.xml");
        System.out.println("lookup-method");
        lookupMethod(ac);
        lookupMethod(ac);

        System.out.println("aware");
        getPrototypeBeanInSingletonByAware(ac);
        getPrototypeBeanInSingletonByAware(ac);

        System.out.println("replace-method");
        replaceMethod(ac);
    }

    private static void lookupMethod(BeanFactory factory) {
        SingletonFoo foo = factory.getBean(SingletonFoo.class);
        System.out.println(foo);
        System.out.println(foo.getPrototypeFoo());
        System.out.println();
    }

    private static void getPrototypeBeanInSingletonByAware(BeanFactory factory) {
        SingletonFoo foo = factory.getBean(SingletonFoo.class);
        System.out.println(foo);
        System.out.println(foo.getPrototypeFooByAware());
        System.out.println();
    }

    private static void replaceMethod(BeanFactory factory) {
        SingletonFoo foo = factory.getBean(SingletonFoo.class);
        foo.method();
    }
}
