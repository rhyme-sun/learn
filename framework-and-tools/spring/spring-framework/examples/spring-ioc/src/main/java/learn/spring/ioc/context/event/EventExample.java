package learn.spring.ioc.context.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * EventExample.
 */
public class EventExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        EmailService service = ac.getBean(EmailService.class);
        service.sendEmail("a", "A");
        service.sendEmail("d", "D");

        ac.close();
    }
}
