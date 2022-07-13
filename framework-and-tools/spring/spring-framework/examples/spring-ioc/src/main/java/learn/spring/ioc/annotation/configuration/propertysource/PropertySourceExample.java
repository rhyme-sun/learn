package learn.spring.ioc.annotation.configuration.propertysource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * PropertySourceExample.
 */
public class PropertySourceExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println(ac.getBean(MyBean.class).getName());
    }
}
