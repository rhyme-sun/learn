package learn.spring.ioc.annotation.configuration.importconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ImportConfigExample.
 */
public class ImportConfigExample {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigB.class);

        A a = ctx.getBean(A.class);
        B b = ctx.getBean(B.class);
        System.out.println(a);
        System.out.println(b);
    }
}
