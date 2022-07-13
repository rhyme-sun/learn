package learn.spring.ioc.annotation.configuration.importresource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ImportResourceExample
 */
public class ImportResourceExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        System.out.println(ac.getBean(DbConfig.class));
    }
}
