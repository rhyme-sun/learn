package learn.spring.ioc.cycle;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * CycleReferenceExample.
 */
public class CycleReferenceExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:cycle.xml");
        System.out.println(ac.getBean("a", A.class).getB());
        System.out.println(ac.getBean("b", B.class).getA());
    }
}
