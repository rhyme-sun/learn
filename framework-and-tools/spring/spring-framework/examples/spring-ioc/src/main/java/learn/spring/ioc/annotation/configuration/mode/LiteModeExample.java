package learn.spring.ioc.annotation.configuration.mode;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * LiteModeExample.
 */
public class LiteModeExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        // Full Mode
        FooService fooService = ac.getBean(FooService.class);
        System.out.println(fooService.getFooRepository());
        System.out.println(ac.getBean(FooRepository.class));
    }
}
