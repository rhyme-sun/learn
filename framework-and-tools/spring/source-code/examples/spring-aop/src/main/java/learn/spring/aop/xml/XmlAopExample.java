package learn.spring.aop.xml;

import learn.spring.aop.xml.service.MyCalculator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * XmlAopExample.
 */
public class XmlAopExample {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:aop.xml");
        MyCalculator myCalculator = ac.getBean(MyCalculator.class);
        //System.out.println(myCalculator);
        System.out.println(myCalculator.add(1, 1));
    }
}
