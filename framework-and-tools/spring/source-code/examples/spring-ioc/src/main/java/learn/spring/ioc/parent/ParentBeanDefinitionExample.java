package learn.spring.ioc.parent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ParentBeanDefinitionExample.
 */
public class ParentBeanDefinitionExample {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:parent.xml");
        System.out.println(ac.getBean("user"));
        System.out.println(ac.getBean("superUser"));

        System.out.println(new SuperUser());
    }
}
