package learn.spring.ioc.instantiation.factorymethod;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * FactoryMethodInstantiationExample.
 */
public class FactoryMethodInstantiationExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:factory-method.xml");
        System.out.println(ac.getBean(BeanByFactoryMethod.class));
    }
}
