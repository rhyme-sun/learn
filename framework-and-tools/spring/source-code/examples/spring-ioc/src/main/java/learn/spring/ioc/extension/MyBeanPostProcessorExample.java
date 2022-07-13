package learn.spring.ioc.extension;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * MyBeanPostProcessorExample.
 */
public class MyBeanPostProcessorExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:my-bean-post-processor.xml");
        System.out.println(ac.getBean("foo"));
    }
}
