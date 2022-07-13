package learn.spring.ioc.populate;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * PopulateExample.
 */
public class PopulateExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:populate-bean.xml");
        System.out.println(ac.getBean("person", Person.class));
    }
}
