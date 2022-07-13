package learn.spring.ioc.annotation.configuration.lookup;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * LookupExample.
 */
public class LookupExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);
        ac.refresh();

        CommandManager manager = ac.getBean(CommandManager.class);
        System.out.println(manager.createCommand());
        System.out.println(manager.createCommand());
        System.out.println(manager.createCommand());
        System.out.println(manager.createCommand());

        ac.close();
    }
}
