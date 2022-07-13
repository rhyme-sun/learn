package learn.spring.ioc.selftag;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * SelfTagExample.
 */
public class SelfTagExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:self-tag.xml");
        System.out.println(ac.getBean("simon"));
    }
}
