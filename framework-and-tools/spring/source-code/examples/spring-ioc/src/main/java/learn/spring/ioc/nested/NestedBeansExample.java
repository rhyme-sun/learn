package learn.spring.ioc.nested;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * NestedBeansExample.
 */
public class NestedBeansExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:nested-beans.xml");

        System.out.println(ac.getBean("foo"));
    }
}
