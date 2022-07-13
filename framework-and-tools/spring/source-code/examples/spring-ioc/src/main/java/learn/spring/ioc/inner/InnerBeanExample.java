package learn.spring.ioc.inner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * InnerBeanExample.
 */
public class InnerBeanExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:inner-bean.xml");

        System.out.println(ac.getBean("outer"));
    }
}
