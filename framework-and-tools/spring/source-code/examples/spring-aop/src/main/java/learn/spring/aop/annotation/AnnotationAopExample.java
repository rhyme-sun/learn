package learn.spring.aop.annotation;

import learn.spring.aop.annotation.config.AppConfig;
import learn.spring.aop.annotation.service.MyCalculator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationAopExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();
        MyCalculator myCalculator = ac.getBean("myCalculator", MyCalculator.class);
        System.out.println(myCalculator.add(1, 1));

        ac.close();
    }
}
